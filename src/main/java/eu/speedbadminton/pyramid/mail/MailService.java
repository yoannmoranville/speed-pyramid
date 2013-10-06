package eu.speedbadminton.pyramid.mail;

/**
 * User: Yoann Moranville
 * Date: 06/10/2013
 *
 * @author Yoann Moranville
 */
public abstract class MailService {
    public static void sendEmail(String email, String body, String to, String from) {
        EmailComposer emailComposer = new EmailComposer("emails/encounter.txt", "Encounter!!!!", true, false);
        emailComposer.setProperty("email", email);
        emailComposer.setProperty("body", body);
        emailComposer.setProperty("to", to);
        emailComposer.setProperty("reply-to", from);
        Emailer emailer = new Emailer();
        emailer.sendMessage(to, null, null, email, emailComposer);
    }
}
