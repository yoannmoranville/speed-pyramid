package eu.speedbadminton.pyramid.mail;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

/**
 * User: lukasgotter
 * Date: 12.12.13
 */
@Service
public class ApplicationMailer {
    private static final Logger LOGGER = Logger.getLogger(EmailComposer.class);


    @Autowired
    private MailSender mailSender;

    @Autowired
    private SimpleMailMessage testMessage;

    /**
     * This method will send compose and send the message
     * */
    public void sendMail(String to, String cc, String subject, String body)
    {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setCc(cc);
        message.setSubject(subject);
        message.setText(body);
        mailSender.send(message);
        LOGGER.debug("Sent Email to "+to+" subject: "+subject+" ---- message: "+message);
    }

    /**
     * This method will send a pre-configured message
     * */
    public void sendTestMessage(String message)
    {
        SimpleMailMessage mailMessage = new SimpleMailMessage(testMessage);
        mailMessage.setText(message);
        mailSender.send(mailMessage);
    }

}
