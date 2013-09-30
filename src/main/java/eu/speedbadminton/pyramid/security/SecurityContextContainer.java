package eu.speedbadminton.pyramid.security;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import eu.speedbadminton.pyramid.model.Player;
import org.apache.log4j.Logger;

/**
 * User: Yoann Moranville
 * Date: 30/09/2013
 *
 * @author Yoann Moranville
 */

public final class SecurityContextContainer implements HttpSessionListener {
    private static final Logger LOGGER = Logger.getLogger(SecurityContextContainer.class);

    private static Map<String, SecurityContext> securityContexts = new HashMap<String, SecurityContext>();
    private static Map<String, HttpSession> activeSessions = new HashMap<String, HttpSession>();

    protected static void putSecurityContext(SecurityContext securityContext) {
        synchronized (securityContexts) {
            securityContexts.put(securityContext.getPlayerId(), securityContext);
        }
    }

    protected static void deleteSecurityContext(SecurityContext securityContext) {
        synchronized (securityContexts) {
            if (securityContexts.containsKey(securityContext.getPlayerId())) {
                securityContexts.remove(securityContext.getPlayerId());
            }

        }
    }

    protected static boolean checkAvailability(SecurityContext otherSecurityContext) {
        boolean available = true;
        if (otherSecurityContext != null) {
            synchronized (securityContexts) {
                available = !securityContexts.containsKey(otherSecurityContext.getPlayerId());
            }
        }
        if (!available) {
            LOGGER.info(otherSecurityContext.getEmailAddress() + " is already in use.");
        }
        return available;
    }

    public static boolean checkAvailability(String playerId) {
        boolean available = true;
        if (playerId != null) {
            available = !securityContexts.containsKey(playerId);
        }
        return available;
    }

    protected static void dropOtherSessions(SecurityContext securityContext) {
        SecurityContext otherSecurityContext = null;
        synchronized (securityContexts) {
            otherSecurityContext = securityContexts.get(securityContext.getPlayerId());
        }
        if (otherSecurityContext != null){
            dropSession(otherSecurityContext.getSessionId());
        }
    }

    protected static void dropSession(String sessionId) {
        HttpSession session = activeSessions.get(sessionId);
        try {
            session.invalidate();
        } catch (Exception e) {
            LOGGER.error("Something went wrong while invalidating the session", e);
        }
    }

    public void sessionCreated(HttpSessionEvent event) {
        synchronized (activeSessions) {
            activeSessions.put(event.getSession().getId(), event.getSession());
        }
    }

    public void sessionDestroyed(HttpSessionEvent event) {
        synchronized (activeSessions) {
            activeSessions.remove(event.getSession().getId());
        }
    }

    public static List<SessionInfo> retrieveSessionInfo() {
        List<SessionInfo> sessionInfos = new ArrayList<SessionInfo>();
        for (HttpSession session : activeSessions.values()) {
            SecurityContext securityContext = (SecurityContext) session.getAttribute(SecurityContext.VAR_SECURITY_CONTEXT);
            if (securityContext != null){
                sessionInfos.add(new SessionInfo(securityContext, session.getId()));
            }
        }
        return sessionInfos;
    }

    public static class SessionInfo {
        private String emailAddress;
        private String name;
        private Player.Role role;
        private String sessionId;

        private SessionInfo(SecurityContext securityContext, String sessionId) {
            if (securityContext != null) {
                this.name = securityContext.getName();
                this.emailAddress = securityContext.getEmailAddress();
                this.role = securityContext.getRole();
            }
            this.sessionId = sessionId;
        }

        public String getEmailAddress() {
            return emailAddress;
        }

        public String getName() {
            return name;
        }

        public Player.Role getRole() {
            return role;
        }

        public String getSessionId() {
            return sessionId;
        }
    }
}
