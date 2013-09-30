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

    public static void logout() {
        SecurityContext securityContext = SecurityContext.get();
        if (securityContext != null) {
            securityContext.logout();
        }
    }

    public static class LoginResult {
        public enum LoginResultType {
            LOGGED_IN, INVALID_USERNAME_PASSWORD
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

