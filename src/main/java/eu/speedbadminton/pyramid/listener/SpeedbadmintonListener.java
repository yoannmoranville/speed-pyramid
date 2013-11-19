package eu.speedbadminton.pyramid.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * User: Yoann Moranville
 * Date: 16/10/2013
 *
 * @author Yoann Moranville
 */
public class SpeedbadmintonListener implements ServletContextListener {

    private static final String PATH_FOR_AVATAR = "PATH_FOR_AVATAR";

    public void contextInitialized(ServletContextEvent servletContextEvent) {
        SpeedbadmintonConfig config = new SpeedbadmintonConfig();
        String pathForAvatar = servletContextEvent.getServletContext().getInitParameter(PATH_FOR_AVATAR);
        if (pathForAvatar == null)
            throw new RuntimeException(PATH_FOR_AVATAR + " is not configured in TOMCAT");
        config.setPathForAvatarFile(pathForAvatar);
    }

    public void contextDestroyed(ServletContextEvent servletContextEvent) {
    }
}
