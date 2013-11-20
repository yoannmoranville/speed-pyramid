package eu.speedbadminton.pyramid.listener;

/**
 * User: Yoann Moranville
 * Date: 16/10/2013
 *
 * @author Yoann Moranville
 */
public class SpeedbadmintonConfig {
    private static String pathForAvatarFile;
    private static boolean dev;
    private static String linkServer;

    public static String getPathForAvatarFile() {
        return pathForAvatarFile;
    }

    public static void setPathForAvatarFile(String pathForAvatarFile) {
        SpeedbadmintonConfig.pathForAvatarFile = pathForAvatarFile;
    }

    public static boolean isDev() {
        return dev;
    }

    public static void setDev(boolean dev) {
        SpeedbadmintonConfig.dev = dev;
    }

    public static String getLinkServer() {
        return linkServer;
    }

    public static void setLinkServer(String linkServer) {
        SpeedbadmintonConfig.linkServer = linkServer;
    }
}
