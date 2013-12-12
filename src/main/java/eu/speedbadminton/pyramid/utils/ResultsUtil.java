package eu.speedbadminton.pyramid.utils;

import eu.speedbadminton.pyramid.model.Match;
import eu.speedbadminton.pyramid.model.Player;

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
        resultString.append(result.getSet1().getPointOfChallenger());
        resultString.append(SEPARATION_OF_POINT);
        resultString.append(result.getSet1().getPointOfChallengee());

        resultString.append(SEPARATION_OF_SET);

        resultString.append(result.getSet2().getPointOfChallenger());
        resultString.append(SEPARATION_OF_POINT);
        resultString.append(result.getSet2().getPointOfChallengee());

        if(result.getSet3() != null) {
            resultString.append(SEPARATION_OF_SET);

            resultString.append(result.getSet3().getPointOfChallenger());
            resultString.append(SEPARATION_OF_POINT);
            resultString.append(result.getSet3().getPointOfChallengee());
        }

        return resultString.toString();
    }

    public static Result parseResultString(String resultString) {
        String[] sets = resultString.split(SEPARATION_OF_SET);

        String[] pointsSet = sets[0].split(SEPARATION_OF_POINT);
        Set set1 = new Set(Integer.parseInt(pointsSet[0]), Integer.parseInt(pointsSet[1]));

        pointsSet = sets[1].split(SEPARATION_OF_POINT);
        Set set2 = new Set(Integer.parseInt(pointsSet[0]), Integer.parseInt(pointsSet[1]));

        Set set3 = null;
        if(sets.length == 3 && sets[2] != null) {
            pointsSet = sets[2].split(SEPARATION_OF_POINT);
            set3 = new Set(Integer.parseInt(pointsSet[0]), Integer.parseInt(pointsSet[1]));
        }

        return new Result(set1, set2, set3);
    }

    public static boolean isChallengerWinner(String resultStr) {
        return isChallengerWinner(parseResultString(resultStr));
    }

    public static boolean isChallengerWinner(Result result) {
        int winningSet = 0;
        if(result.getSet1().getPointOfChallenger() > result.getSet1().getPointOfChallengee()) {
            winningSet ++;
        }
        if(result.getSet2().getPointOfChallenger() > result.getSet2().getPointOfChallengee()) {
            winningSet ++;
        }
        if(result.getSet3() != null) {
            if(result.getSet3().getPointOfChallenger() > result.getSet3().getPointOfChallengee()) {
                winningSet ++;
            }
        }
        return winningSet == 2;
    }

    public static boolean isResultCorrect(Result result) {
        final int SET_POINT = 16;
        final int MINIMUM_DIFF = 2;
        int winningSetForChallenger = 0;
        int winningSetForChallengee = 0;
        Set set;

        for(int i = 1; i <= 3; i++) {
            if(i == 1) {
                set = result.getSet1();
            } else if(i == 2) {
                set = result.getSet2();
            } else {
                set = result.getSet3();
                if(set == null) {
                    break;
                } else if (winningSetForChallenger == 2 || winningSetForChallengee == 2) {
                    return false;
                }
            }

            if(set.getPointOfChallengee() < 0 || set.getPointOfChallenger() < 0) {
                return false;
            }

            if(set.getPointOfChallengee() == SET_POINT && set.getPointOfChallenger() <= (SET_POINT - MINIMUM_DIFF)) {
                winningSetForChallengee++;
            } else if(set.getPointOfChallenger() == SET_POINT && set.getPointOfChallengee() <= (SET_POINT - MINIMUM_DIFF)) {
                winningSetForChallenger++;
            } else if(set.getPointOfChallengee() > SET_POINT && (set.getPointOfChallengee() - set.getPointOfChallenger() == MINIMUM_DIFF)) {
                winningSetForChallengee++;
            } else if(set.getPointOfChallenger() > SET_POINT && (set.getPointOfChallenger() - set.getPointOfChallengee() == MINIMUM_DIFF)) {
                winningSetForChallenger++;
            } else {
                return false;
            }
        }
        return winningSetForChallenger == 2 || winningSetForChallengee == 2;
    }

    public static Player getWinner(Match match, Result result) {
        int winningSet = 0;
        if(result.getSet1().getPointOfChallenger() > result.getSet1().getPointOfChallengee()) {
            winningSet ++;
        }
        if(result.getSet2().getPointOfChallenger() > result.getSet2().getPointOfChallengee()) {
            winningSet ++;
        }
        if(result.getSet3() != null) {
            if(result.getSet3().getPointOfChallenger() > result.getSet3().getPointOfChallengee()) {
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
        if(result.getSet1().getPointOfChallenger() > result.getSet1().getPointOfChallengee()) {
            winningSet ++;
        }
        if(result.getSet2().getPointOfChallenger() > result.getSet2().getPointOfChallengee()) {
            winningSet ++;
        }
        if(result.getSet3() != null) {
            if(result.getSet3().getPointOfChallenger() > result.getSet3().getPointOfChallengee()) {
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
