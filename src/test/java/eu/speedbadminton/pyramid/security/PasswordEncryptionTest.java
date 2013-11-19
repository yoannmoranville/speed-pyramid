package eu.speedbadminton.pyramid.security;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * User: lukasgotter
 * Date: 06.11.13
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:META-INF/context-base.xml"})
public class PasswordEncryptionTest {

    @Test
    public void testEncryptTestPassword(){
        String pwd = "test";
        String expectedHash = "qUqP5cyxm6YcTAhz05Hph5gvu9M=";
        String testEncryption = PasswordEncryption.generateDigest(pwd);

        assert expectedHash.equals(expectedHash);
        System.out.println(pwd+" =>"+testEncryption);

    }

}
