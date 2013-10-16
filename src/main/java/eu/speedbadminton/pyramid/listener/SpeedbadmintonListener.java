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

    public void contextInitialized(ServletContextEvent servletContextEvent) {
        SpeedbadmintonConfig config = new SpeedbadmintonConfig();
        if (config.isDefaultInactivityProcessing()){
            InactivityDaemon.start();
        }
    }

    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        InactivityDaemon.stop();
    }
}
