package eu.speedbadminton.pyramid.utils;

import eu.speedbadminton.pyramid.model.Match;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * User: Yoann Moranville
 * Date: 26/11/2013
 *
 * @author Yoann Moranville
 */
public abstract class MatchUtil {
    public static String createMatchResultString(Match match) {
        StringBuilder builder = new StringBuilder();
        builder.append(match.getChallenger().getName());
        builder.append(" vs ");
        builder.append(match.getChallengee().getName());
        builder.append(": ");
        builder.append(match.getResult());
        builder.append(" played on ");
        builder.append(printNiceDate(match.getMatchDate()));
        return builder.toString();
    }

    public static String createMatchChallengeString(Match match) {
        StringBuilder builder = new StringBuilder();
        builder.append(match.getChallenger().getName());
        builder.append(" vs ");
        builder.append(match.getChallengee().getName());
        builder.append(" - created on ");
        builder.append(printNiceDate(match.getCreation()));
        return builder.toString();
    }

    private static String printNiceDate(Date date) {
        DateFormat outputFormat = new SimpleDateFormat("dd-MM-yyyy");
        return outputFormat.format(date);
    }
}
