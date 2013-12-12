package eu.speedbadminton.pyramid.utils;

import eu.speedbadminton.pyramid.model.Match;
import eu.speedbadminton.pyramid.model.Player;
import org.junit.Assert;
import org.junit.Test;

/**
 * User: Yoann Moranville
 * Date: 06/11/2013
 *
 * @author Yoann Moranville
 */
public class ResultsUtilTest {

    @Test
    public void isResultCorrectTest() {
        Result result = new Result("2", "16", "20", "18", "7", "16");
        Assert.assertTrue(ResultsUtil.isResultCorrect(result));
        result = new Result("2", "16", "18", "20", null, null);
        Assert.assertTrue(ResultsUtil.isResultCorrect(result));
        result = new Result("2", "16", "21", "18", "7", "16");
        Assert.assertFalse(ResultsUtil.isResultCorrect(result));
        result = new Result("16", "0", "16", "5", "3", "16");
        Assert.assertFalse(ResultsUtil.isResultCorrect(result));
        result = new Result("16", "0", "16", "-2", null, null);
        Assert.assertFalse(ResultsUtil.isResultCorrect(result));
    }

    @Test
    public void isWinnerCorrect(){
        Player one = new Player();
        Player two = new Player();
        one.setId("one");
        two.setId("two");
        Match m = new Match();
        m.setChallenger(one);
        m.setChallengee(two);

        // challenger is winner
        Result result = new Result("16", "1", "16", "5","","");
        Player winner = ResultsUtil.getWinner(m,result);
        Assert.assertTrue(one.equals(winner));

        // challengee is winner;
        result = new Result("1", "16", "1", "16","","");
        winner = ResultsUtil.getWinner(m,result);
        Assert.assertTrue(two.equals(winner));

    }

    @Test
    public void isLooserCorrect(){
        Player one = new Player();
        Player two = new Player();
        one.setId("one");
        two.setId("two");
        Match m = new Match();
        m.setChallenger(one);
        m.setChallengee(two);

        // challenger is winner
        Result result = new Result("16", "1", "16", "5","","");
        Player looser = ResultsUtil.getLooser(m,result);
        Assert.assertTrue(two.equals(looser));

        // challengee is winner;
        result = new Result("1", "16", "1", "16","","");
        looser = ResultsUtil.getLooser(m,result);
        Assert.assertTrue(one.equals(looser));
    }
}
