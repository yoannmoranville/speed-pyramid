package eu.speedbadminton.pyramid.utils;

import eu.speedbadminton.pyramid.model.Match;
import eu.speedbadminton.pyramid.model.Player;
import eu.speedbadminton.pyramid.model.Result;

/**
 * User: Yoann Moranville
 * Date: 05/11/2013
 *
 * @author Yoann Moranville
 */
public abstract class ResultsUtil {
    private static final String SEPARATION_OF_POINT = ":";
    private static final String SEPARATION_OF_SET = " - ";

    public static String createResultString(Result result) {
        StringBuilder resultString = new StringBuilder();
        resultString.append(result.getSet(1).getPointsPlayer1());
        resultString.append(SEPARATION_OF_POINT);
        resultString.append(result.getSet(1).getPointsPlayer2());

        resultString.append(SEPARATION_OF_SET);

        resultString.append(result.getSet(2).getPointsPlayer1());
        resultString.append(SEPARATION_OF_POINT);
        resultString.append(result.getSet(2).getPointsPlayer2());

        if(result.getSet(3) != null) {
            resultString.append(SEPARATION_OF_SET);

            resultString.append(result.getSet(3).getPointsPlayer1());
            resultString.append(SEPARATION_OF_POINT);
            resultString.append(result.getSet(3).getPointsPlayer2());
        }

        return resultString.toString();
    }

    public static boolean isChallengerWinner(Result result) {
        int winningSet = 0;
        if(result.getSet(1).getPointsPlayer1() > result.getSet(1).getPointsPlayer2()) {
            winningSet ++;
        }
        if(result.getSet(2).getPointsPlayer1() > result.getSet(2).getPointsPlayer2()) {
            winningSet ++;
        }
        if(result.getSet(3) != null) {
            if(result.getSet(3).getPointsPlayer1() > result.getSet(3).getPointsPlayer2()) {
                winningSet ++;
            }
        }
        return winningSet == 2;
    }

    public static Player getWinner(Match match, Result result) {
        int winningSet = 0;
        if(result.getSet(1).getPointsPlayer1() > result.getSet(1).getPointsPlayer2()) {
            winningSet ++;
        }
        if(result.getSet(2).getPointsPlayer1() > result.getSet(2).getPointsPlayer2()) {
            winningSet ++;
        }
        if(result.getSet(3) != null) {
            if(result.getSet(3).getPointsPlayer1() > result.getSet(3).getPointsPlayer2()) {
                winningSet ++;
            }
        }

        if (winningSet == 2) { // challenger has 2 winning sets
            return match.getChallenger();
        } else {
            return match.getChallengee();
        }


    }

    // TODO: refactor!
    public static Player getLooser(Match match, Result result) {
        int winningSet = 0;
        if(result.getSet(1).getPointsPlayer1() > result.getSet(1).getPointsPlayer2()) {
            winningSet ++;
        }
        if(result.getSet(2).getPointsPlayer1() > result.getSet(2).getPointsPlayer2()) {
            winningSet ++;
        }
        if(result.getSet(3) != null) {
            if(result.getSet(3).getPointsPlayer1() > result.getSet(3).getPointsPlayer2()) {
                winningSet ++;
            }
        }

        if (winningSet == 2) { // challenger has 2 winning sets
            return match.getChallengee();
        } else {
            return match.getChallenger();
        }


    }





}
