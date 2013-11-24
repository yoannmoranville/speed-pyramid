package eu.speedbadminton.pyramid.controller;

import eu.speedbadminton.pyramid.model.Player;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * User: Yoann Moranville
 * Date: 24/11/2013
 *
 * @author Yoann Moranville
 */
public class PlayerValidator implements Validator {

    @Override
    public boolean supports(Class<?> aClass) {
        return Player.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Player player = (Player)target;
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "Name can not be empty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "Email can not be empty");

        String email = player.getEmail();
        if(!email.isEmpty()) {
            Pattern pattern = Pattern.compile("(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])");
            Matcher matcher = pattern.matcher(email);
            if(!matcher.find()) {
                errors.rejectValue("email", "The email address given is not correct");
            }
        }
    }
}
