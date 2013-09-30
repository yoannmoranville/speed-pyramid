package eu.speedbadminton.pyramid.security;

import org.apache.log4j.Logger;

import java.security.MessageDigest;
import org.apache.commons.codec.binary.Base64;

/**
 * User: Yoann Moranville
 * Date: 30/09/2013
 *
 * @author Yoann Moranville
 */

public class PasswordEncryption {
    private static final Logger LOG = Logger.getLogger(PasswordEncryption.class);
    private static final String SHA1 = "SHA1";
    private static final String UTF8 = "UTF-8";

    public static String generateDigest(String plainText) {
        try {
            byte[] pwdBin = plainText.getBytes(UTF8);
            MessageDigest md = MessageDigest.getInstance(SHA1);
            byte[] digest = md.digest(pwdBin);
            return new String(Base64.encodeBase64(digest));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

