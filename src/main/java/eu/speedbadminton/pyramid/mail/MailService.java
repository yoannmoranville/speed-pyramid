package eu.speedbadminton.pyramid.mail;

/**
 * User: Yoann Moranville
 * Date: 06/10/2013
 *
 * @author Yoann Moranville
 */
public abstract class MailService {
    public static void sendEmail(String from, String body, String to, String nameOfReceiver) {
        EmailComposer emailComposer = new EmailComposer("emails/encounter.txt", "Encounter!!!!", true, false);
        emailComposer.setProperty("email", from);
        emailComposer.setProperty("body", body);
        emailComposer.setProperty("name", nameOfReceiver);
        emailComposer.setProperty("to", to);
        emailComposer.setProperty("from", from);
        Emailer emailer = new Emailer();
        emailer.sendMessage(to, null, from, null, emailComposer);
    }
}
