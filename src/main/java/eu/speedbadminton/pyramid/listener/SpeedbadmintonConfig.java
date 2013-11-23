package eu.speedbadminton.pyramid.listener;

/**
 * User: Yoann Moranville
 * Date: 16/10/2013
 *
 * @author Yoann Moranville
 */
public class SpeedbadmintonConfig {
    private static String savePathForAvatarFile;
    private static String pathForAvatarFile;
    private static boolean dev;
    private static String sendMailPath;
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

    public static String getSavePathForAvatarFile() {
        return savePathForAvatarFile;
    }

    public static void setSavePathForAvatarFile(String savePathForAvatarFile) {
        SpeedbadmintonConfig.savePathForAvatarFile = savePathForAvatarFile;
    }

    public static String getSendMailPath() {
        return sendMailPath;
    }

    public static void setSendMailPath(String sendMailPath) {
        SpeedbadmintonConfig.sendMailPath = sendMailPath;
    }
}
