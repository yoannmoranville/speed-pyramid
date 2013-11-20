package eu.speedbadminton.pyramid.listener;

/**
 * User: Yoann Moranville
 * Date: 16/10/2013
 *
 * @author Yoann Moranville
 */
public class SpeedbadmintonConfig {
    private static String pathForAvatarFile;

    public static String getPathForAvatarFile() {
        return pathForAvatarFile;
    }

    public static void setPathForAvatarFile(String pathForAvatarFile) {
        SpeedbadmintonConfig.pathForAvatarFile = pathForAvatarFile;
    }
}
