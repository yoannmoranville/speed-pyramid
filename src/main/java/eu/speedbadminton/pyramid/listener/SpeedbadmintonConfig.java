package eu.speedbadminton.pyramid.listener;

/**
 * User: Yoann Moranville
 * Date: 16/10/2013
 *
 * @author Yoann Moranville
 */
public class SpeedbadmintonConfig {
    private boolean defaultInactivityProcessing = true;

    public boolean isDefaultInactivityProcessing() {
        return defaultInactivityProcessing;
    }

    public void setDefaultInactivityProcessing(boolean defaultInactivityProcessing) {
        this.defaultInactivityProcessing = defaultInactivityProcessing;
    }
}
