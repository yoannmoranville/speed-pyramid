package eu.speedbadminton.pyramid.security;

import eu.speedbadminton.pyramid.context.ApplicationContextSpeedminton;
import eu.speedbadminton.pyramid.model.Player;
import eu.speedbadminton.pyramid.service.PlayerService;
import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import javax.servlet.http.HttpServletRequest;

/**
 * User: Yoann Moranville
 * Date: 30/09/2013
 *
 * @author Yoann Moranville
 */

public final class SecurityService {
    private static final Logger LOGGER = Logger.getLogger(SecurityService.class);

    public static LoginResult login(String username, String password) throws Exception {
        try {
            PlayerService playerService = (PlayerService) ApplicationContextSpeedminton.getApplicationContext().getBean("playerService");
            Player loginPlayer = playerService.login(username, PasswordEncryption.generateDigest(password));
            SecurityContext context = null;
            LoginResult.LoginResultType type = null;
            if (loginPlayer != null) {
                context = new SecurityContext(loginPlayer);
                context.login();
                type = LoginResult.LoginResultType.LOGGED_IN;
            } else {
                type = LoginResult.LoginResultType.INVALID_USERNAME_PASSWORD;
            }
            return new LoginResult(context, type);
        } catch (Exception e) {
            throw new Exception("Unable to login with username" + username, e);
        }
    }

    public static void logout(boolean logoutToParent) {
        SecurityContext securityContext = SecurityContext.get();
        if (securityContext != null) {
            securityContext.logout(logoutToParent);
        }
    }

    public static LoginResult loginAsPlayer(String playerId) {
        SecurityContext currentSecurityContext = SecurityContext.get();
        SecurityContext context = null;
        LoginResult.LoginResultType type = null;
        if (currentSecurityContext != null && currentSecurityContext.isAdmin()) {
            PlayerService playerService = (PlayerService) ApplicationContextSpeedminton.getApplicationContext().getBean("playerService");
            Player player = playerService.getPlayerById(playerId);

            if (player != null) {
                context = new SecurityContext(player, currentSecurityContext);
                if (SecurityContextContainer.checkAvailability(context)) {
                    context.login();
                    type = LoginResult.LoginResultType.LOGGED_IN;
                } else {
                    context = null;
                    type = LoginResult.LoginResultType.ALREADY_IN_USE;
                }

            } else {
                type = LoginResult.LoginResultType.NO_PLAYER;
            }
        } else {
            type = LoginResult.LoginResultType.ACCESS_DENIED;
        }
        return new LoginResult(context, type);

    }

    public static class LoginResult {
        public enum LoginResultType {
            LOGGED_IN, INVALID_USERNAME_PASSWORD, ALREADY_IN_USE, NO_PLAYER, ACCESS_DENIED
        }

        private SecurityContext context;
        private LoginResultType type;

        protected LoginResult(SecurityContext context, LoginResultType type) {
            this.context = context;
            this.type = type;
        }

        public SecurityContext getContext() {
            return context;
        }

        public LoginResultType getType() {
            return type;
        }
    }
}

