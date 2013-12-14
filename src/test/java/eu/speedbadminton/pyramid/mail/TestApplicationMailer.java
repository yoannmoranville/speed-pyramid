package eu.speedbadminton.pyramid.mail;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * User: lukasgotter
 * Date: 12.12.13
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:META-INF/context-base.xml"})
public class TestApplicationMailer {

    @Ignore
    @Test
    public void SendMailOnDeployTest()
    {
        //Create the application context
        ApplicationContext context = new FileSystemXmlApplicationContext("src/main/resources/META-INF/context-base.xml");

        //Get the mailer instance
        ApplicationMailer mailer = (ApplicationMailer) context.getBean("applicationMailer");

        //Send a pre-configured mail
        mailer.sendTestMessage("This is just a test message from speed badminton spring application. You should recieve it after a deploy or any time this test is executed.");
    }
}
