package eu.speedbadminton.pyramid.security;

import java.util.Random;

/**
 * User: Yoann Moranville
 * Date: 18/11/2013
 *
 * @author ?
 */
public class PasswordGenerator {

    private static final int DEFAULT_PASSWORD_LENGTH = 15;

    private static final String SPECIAL_CHARSET = "!%$";
    private static final String LOWERCASE_CHARSET = "abcdefghijklmnopqrstuvwxyz";
    private static final String UPPER_CHARSET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String DIGIT_CHARSET = "0123456789";
    private static final String CHARSET = DIGIT_CHARSET + LOWERCASE_CHARSET + UPPER_CHARSET + SPECIAL_CHARSET;

    public static String getRandomString() {
        return getRandomString(CHARSET, DEFAULT_PASSWORD_LENGTH);
    }

    public static String getRandomString(String charset, int length) {
        Random rand = new Random(System.currentTimeMillis());
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int pos = rand.nextInt(charset.length());
            sb.append(charset.charAt(pos));
        }
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
        }
        return sb.toString();
    }

}

