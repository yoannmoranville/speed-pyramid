package eu.speedbadminton.pyramid.mail;

/**
 * User: Yoann Moranville
 * Date: 06/10/2013
 *
 * @author Yoann Moranville
 */
public abstract class MailService {
    public static void sendEmailEncounter(String from, String body, String to, String nameOfReceiver, String nameOfSender) {
        EmailComposer emailComposer = new EmailComposer("emails/encounter.txt", "Encounter!!!!", true, false);
        emailComposer.setProperty("email", from);
        emailComposer.setProperty("body", body);
        emailComposer.setProperty("nameOfReceiver", nameOfReceiver);
        emailComposer.setProperty("nameOfSender", nameOfSender);
        emailComposer.setProperty("to", to);
        emailComposer.setProperty("from", from);
        Emailer emailer = new Emailer();
        emailer.sendMessage(to, from, null, from, emailComposer);
    }

    public static void sendEmailResults(String from, String body, String to, String nameOfReceiver, String nameOfSender) {
        EmailComposer emailComposer = new EmailComposer("emails/results.txt", "The results of your last game!", true, false);
        emailComposer.setProperty("email", from);
        emailComposer.setProperty("body", body);
        emailComposer.setProperty("nameOfReceiver", nameOfReceiver);
        emailComposer.setProperty("nameOfSender", nameOfSender);
        emailComposer.setProperty("to", to);
        emailComposer.setProperty("from", from);
        Emailer emailer = new Emailer();
        emailer.sendMessage(to, from, null, from, emailComposer);
    }

    public static void sendEmailPassword(String body, String to, String nameOfReceiver) {
        EmailComposer emailComposer = new EmailComposer("emails/password.txt", "Account created!", true, false);
        emailComposer.setProperty("body", body);
        emailComposer.setProperty("nameOfReceiver", nameOfReceiver);
        emailComposer.setProperty("to", to);
        Emailer emailer = new Emailer();
        emailer.sendMessage(to, null, null, null, emailComposer);
    }
}
