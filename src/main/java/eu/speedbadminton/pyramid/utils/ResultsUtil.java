package eu.speedbadminton.pyramid.utils;

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
        if(sets[2] != null) {
            pointsSet = sets[2].split(SEPARATION_OF_POINT);
            set3 = new Set(Integer.parseInt(pointsSet[0]), Integer.parseInt(pointsSet[1]));
        }

        return new Result(set1, set2, set3);
    }
}
