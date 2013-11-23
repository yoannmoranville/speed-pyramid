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
        EmailComposer emailComposer = new EmailComposer("emails/basicMail.txt", "Account created!", true, false);
        emailComposer.setProperty("body", body);
        emailComposer.setProperty("nameOfReceiver", nameOfReceiver);
        emailComposer.setProperty("to", to);
        Emailer emailer = new Emailer();
        emailer.sendMessage(to, null, null, null, emailComposer);
    }

    public static void sendEmailResultsLooserValidation(String body, String email, String name) {
        EmailComposer emailComposer = new EmailComposer("emails/basicMail.txt", "Please validate the match results!", true, false);
        emailComposer.setProperty("body", body);
        emailComposer.setProperty("nameOfReceiver", name);
        emailComposer.setProperty("to", email);
        Emailer emailer = new Emailer();
        emailer.sendMessage(email, null, null, null, emailComposer);
    }

    public static void sendEmailResultsWaitingForLooserValidation(String body, String email, String name) {
        EmailComposer emailComposer = new EmailComposer("emails/basicMail.txt", "Information to wait for the validation of your opponent", true, false);
        emailComposer.setProperty("body", body);
        emailComposer.setProperty("nameOfReceiver", name);
        emailComposer.setProperty("to", email);
        Emailer emailer = new Emailer();
        emailer.sendMessage(email, null, null, null, emailComposer);
    }

    public static void sendEmailDisablePlayer(String body, String email, String name) {
        EmailComposer emailComposer = new EmailComposer("emails/basicMail.txt", "Player account disabled", true, false);
        emailComposer.setProperty("body", body);
        emailComposer.setProperty("nameOfReceiver", name);
        emailComposer.setProperty("to", email);
        Emailer emailer = new Emailer();
        emailer.sendMessage(email, null, null, null, emailComposer);
    }

    public static void sendEmailEnablePlayer(String body, String email, String name) {
        EmailComposer emailComposer = new EmailComposer("emails/basicMail.txt", "Player account enabled", true, false);
        emailComposer.setProperty("body", body);
        emailComposer.setProperty("nameOfReceiver", name);
        emailComposer.setProperty("to", email);
        Emailer emailer = new Emailer();
        emailer.sendMessage(email, null, null, null, emailComposer);
    }

    public static void sendEmailChangePassword(String body, String email, String name) {
        EmailComposer emailComposer = new EmailComposer("emails/basicMail.txt", "Change password", true, false);
        emailComposer.setProperty("body", body);
        emailComposer.setProperty("nameOfReceiver", name);
        emailComposer.setProperty("to", email);
        Emailer emailer = new Emailer();
        emailer.sendMessage(email, null, null, null, emailComposer);
    }
}
