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
}
