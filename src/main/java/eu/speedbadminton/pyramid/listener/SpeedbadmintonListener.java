package eu.speedbadminton.pyramid.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.io.File;

/**
 * User: Yoann Moranville
 * Date: 16/10/2013
 *
 * @author Yoann Moranville
 */
public class SpeedbadmintonListener implements ServletContextListener {

    private static final String PATH_FOR_AVATAR = "PATH_FOR_AVATAR";
    private static final String SAVE_PATH_FOR_AVATAR = "SAVE_PATH_FOR_AVATAR";
    private static final String IS_DEV = "IS_DEV";

    public void contextInitialized(ServletContextEvent servletContextEvent) {
        SpeedbadmintonConfig config = new SpeedbadmintonConfig();

        boolean isDev = true;
        String isDevStr = servletContextEvent.getServletContext().getInitParameter(IS_DEV);
        if(isDevStr != null) {
            isDev = Boolean.parseBoolean(isDevStr);
        }
        SpeedbadmintonConfig.setDev(isDev);

        if(isDev) {
            SpeedbadmintonConfig.setLinkServer("https://localhost:8443/pyramid-system/confirmResults.html?id=");
        } else {
            SpeedbadmintonConfig.setLinkServer("https://54.214.239.189:8443/pyramid-system/confirmResults.html?id=");
        }

        String pathForAvatar = servletContextEvent.getServletContext().getInitParameter(PATH_FOR_AVATAR);
        if (pathForAvatar == null && !isDev)
            throw new RuntimeException(PATH_FOR_AVATAR + " is not configured in TOMCAT");
        SpeedbadmintonConfig.setPathForAvatarFile(pathForAvatar);

        String savePathForAvatar = servletContextEvent.getServletContext().getInitParameter(SAVE_PATH_FOR_AVATAR);
        if (savePathForAvatar == null && !isDev)
            throw new RuntimeException(SAVE_PATH_FOR_AVATAR + " is not configured in TOMCAT");
        if(!isDev && !new File(savePathForAvatar).canWrite() && !new File(savePathForAvatar).isDirectory())
            throw new RuntimeException(SAVE_PATH_FOR_AVATAR + " is not a writable directory...");
        SpeedbadmintonConfig.setSavePathForAvatarFile(savePathForAvatar);
    }

    public void contextDestroyed(ServletContextEvent servletContextEvent) {
    }
}
