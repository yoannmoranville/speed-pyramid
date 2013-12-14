package eu.speedbadminton.pyramid.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;

import eu.speedbadminton.pyramid.model.Player;
import org.apache.log4j.Logger;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * User: Yoann Moranville
 * Date: 30/09/2013
 *
 * @author Bastiaan Verhoef
 */
public final class SecurityContext implements HttpSessionBindingListener {
    private static final Logger LOGGER = Logger.getLogger(SecurityContext.class);
    protected static final String VAR_SECURITY_CONTEXT = "var-security-context";

    private String emailAddress;
    private String name;
    private String playerId;
    private Player.Role role;
    private boolean admin;
    private String sessionId;
    private SecurityContext parent;

    protected SecurityContext(Player player, SecurityContext parent) {
        this(player);
        this.parent = parent;
    }

    protected SecurityContext(Player player) {
        role = player.getRole();
        admin = Player.Role.ADMIN.equals(role);
        playerId = player.getId().toString();
        emailAddress = player.getEmail();
        name = player.getName();
    }

    public String getPlayerId() {
        return playerId;
    }

    public Player.Role getRole() {
        return role;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public String getName() {
        return name;
    }

    public boolean isAdmin() {
        return admin;
    }

    public String getParentName() {
        if (parent != null) {
            return parent.getName();
        }
        return null;
    }
    protected SecurityContext getParent(){
        return parent;
    }
    public boolean isChild() {
        return parent != null;
    }

    @Override
    public boolean equals(Object obj) {
        SecurityContext otherSecurityContext = (SecurityContext) obj;
        return this.getPlayerId().equals(otherSecurityContext.getPlayerId());
    }

    public static SecurityContext get() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpSession newSession = attributes.getRequest().getSession();
        return get(newSession);
    }

    private static SecurityContext get(HttpSession session) {
        return ((SecurityContext) session.getAttribute(VAR_SECURITY_CONTEXT));
    }

    protected void logout(boolean logoutToParent) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        if (logoutToParent) {
            parent.login();
        } else {
            request.getSession().removeAttribute(VAR_SECURITY_CONTEXT);
            request.getSession().invalidate();
        }
    }

    protected void login() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        login(request);
    }

    protected void login(HttpServletRequest request) {
        request.getSession().invalidate();
        HttpSession newSession = request.getSession(true);
        newSession.setAttribute(VAR_SECURITY_CONTEXT, this);
        sessionId = newSession.getId();
    }

    protected String getSessionId() {
        return sessionId;
    }

    public void valueBound(HttpSessionBindingEvent event) {
        if (event.getValue() instanceof SecurityContext) {
            SecurityContextContainer.putSecurityContext((SecurityContext) event.getValue());
        }
    }

    public void valueUnbound(HttpSessionBindingEvent event) {
        SecurityContextContainer.deleteSecurityContext(this);
    }
}

