package eu.speedbadminton.pyramid.mail;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * User: Yoann Moranville
 * Date: 06/10/2013
 *
 * @author Yoann Moranville
 */
@Service
public class MailService {

    private static final Logger LOG = org.apache.log4j.Logger.getLogger(MailService.class);

    @Autowired
    private ApplicationMailer applicationMailer;

    public void sendEmailEncounter(String from, String body, String to, String nameOfReceiver, String nameOfSender) {
        EmailComposer emailComposer = new EmailComposer("emails/encounter.txt", "Encounter!!!!", true, false);
        emailComposer.setProperty("email", from);
        emailComposer.setProperty("body", body);
        emailComposer.setProperty("nameOfReceiver", nameOfReceiver);
        emailComposer.setProperty("nameOfSender", nameOfSender);
        emailComposer.setProperty("to", to);
        emailComposer.setProperty("from", from);

        sendMail(to, null, emailComposer);

    }



    public void sendEmailResults(String from, String body, String to, String nameOfReceiver, String nameOfSender) {
        EmailComposer emailComposer = new EmailComposer("emails/results.txt", "The results of your last game!", true, false);
        emailComposer.setProperty("email", from);
        emailComposer.setProperty("body", body);
        emailComposer.setProperty("nameOfReceiver", nameOfReceiver);
        emailComposer.setProperty("nameOfSender", nameOfSender);
        emailComposer.setProperty("to", to);
        emailComposer.setProperty("from", from);

        sendMail(to, from, emailComposer);
    }

    public void sendEmailPassword(String body, String to, String nameOfReceiver) {
        EmailComposer emailComposer = new EmailComposer("emails/basicMail.txt", "Account created!", true, false);
        emailComposer.setProperty("body", body);
        emailComposer.setProperty("nameOfReceiver", nameOfReceiver);
        emailComposer.setProperty("to", to);

        sendMail(to, null, emailComposer);
    }

    public void sendEmailResultsLooserValidation(String body, String to, String name) {
        EmailComposer emailComposer = new EmailComposer("emails/basicMail.txt", "Please validate the match results!", true, false);
        emailComposer.setProperty("body", body);
        emailComposer.setProperty("nameOfReceiver", name);
        emailComposer.setProperty("to", to);

        sendMail(to, null, emailComposer);
    }

    public void sendEmailResultsWaitingForLooserValidation(String body, String to, String name) {
        EmailComposer emailComposer = new EmailComposer("emails/basicMail.txt", "Information to wait for the validation of your opponent", true, false);
        emailComposer.setProperty("body", body);
        emailComposer.setProperty("nameOfReceiver", name);
        emailComposer.setProperty("to", to);

        sendMail(to, null, emailComposer);
    }

    public void sendEmailDisablePlayer(String body, String to, String name) {
        EmailComposer emailComposer = new EmailComposer("emails/basicMail.txt", "Player account disabled", true, false);
        emailComposer.setProperty("body", body);
        emailComposer.setProperty("nameOfReceiver", name);
        emailComposer.setProperty("to", to);

        sendMail(to, null, emailComposer);
    }

    public void sendEmailEnablePlayer(String body, String to, String name) {
        EmailComposer emailComposer = new EmailComposer("emails/basicMail.txt", "Player account enabled", true, false);
        emailComposer.setProperty("body", body);
        emailComposer.setProperty("nameOfReceiver", name);
        emailComposer.setProperty("to", to);

        sendMail(to, null, emailComposer);
    }

    public void sendEmailChangePassword(String body, String to, String name) {
        EmailComposer emailComposer = new EmailComposer("emails/basicMail.txt", "Change password", true, false);
        emailComposer.setProperty("body", body);
        emailComposer.setProperty("nameOfReceiver", name);
        emailComposer.setProperty("to", to);

        sendMail(to, null, emailComposer);
    }

    private void sendMail(String to, String cc, EmailComposer emailComposer) {
        try {
            applicationMailer.sendMail(to, cc, emailComposer.getSubject(), emailComposer.getContent());
        } catch (Exception ex) {
            LOG.error("Error sending mail. Most probably Template not found. "+ex.getMessage());
        }
    }
}
