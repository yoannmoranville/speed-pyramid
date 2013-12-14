package eu.speedbadminton.pyramid.utils;

import eu.speedbadminton.pyramid.model.Match;
import eu.speedbadminton.pyramid.model.Player;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;

/**
 * User: Yoann Moranville
 * Date: 06/11/2013
 *
 * @author Yoann Moranville
 */
public class ResultsUtilTest {

    @Test
    public void isResultCorrectTest() {
        Player player1 = new Player();
        player1.setId("A");
        Player player2 = new Player();
        player2.setId("B");

        Result result = new Result(player1,player2);
        result.addSet(new Set(player1,player2,16,7)).addSet(new Set(player1,player2,16,8));
        Assert.assertTrue("Player A wins in 2 sets",result.isResultCorrect());

        result.setSets(new ArrayList<Set>());
        result.addSet(new Set(player1,player2,0,16)).addSet(new Set(player1,player2,0,16));
        Assert.assertTrue(result.isResultCorrect());


        result.setSets(new ArrayList<Set>());
        result.addSet(new Set(player1,player2,16,10)).addSet(new Set(player1, player2, 11, 16)).addSet(new Set(player1,player2,16,5));
        Assert.assertTrue(result.isResultCorrect());

        result.setSets(new ArrayList<Set>());
        result.addSet(new Set(player1,player2,10,16)).addSet(new Set(player1,player2,20,18)).addSet(new Set(player1,player2,16,5));
        Assert.assertTrue(result.isResultCorrect());

        // unfinished matches
        result.setSets(new ArrayList<Set>());
        result.addSet(new Set(player1,player2,10,10));
        Assert.assertFalse(result.isResultCorrect());

        result.setSets(new ArrayList<Set>());
        result.addSet(new Set(player1, player2, 16, 10));
        Assert.assertFalse(result.isResultCorrect());

        result.setSets(new ArrayList<Set>());
        result.addSet(new Set(player1,player2,10,10)).addSet(new Set(player1,player2,5,4));
        Assert.assertFalse(result.isResultCorrect());

        result.setSets(new ArrayList<Set>());
        result.addSet(new Set(player1,player2,16,10)).addSet(new Set(player1,player2,5,16));
        Assert.assertFalse(result.isResultCorrect());

        // illegal 3rd set
        result.setSets(new ArrayList<Set>());
        result.addSet(new Set(player1,player2,1,16)).addSet(new Set(player1,player2,5,16)).addSet(new Set(player1,player2,16,0));
        Assert.assertFalse(result.isResultCorrect());

        // negative stuff
        result.setSets(new ArrayList<Set>());
        result.addSet(new Set(player1,player2,-16,10)).addSet(new Set(player1,player2,-5,16));
        Assert.assertFalse(result.isResultCorrect());
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
        Result result = new Result(one,two);
        result.addSet(new Set(one,two,16,1)).addSet(new Set(one,two,16,5));
        Assert.assertTrue(one.equals(result.getMatchWinner()));

        // challengee is winner;
        result.setSets(new ArrayList<Set>());
        result.addSet(new Set(one,two,1,16)).addSet(new Set(one,two,6,16));
        Assert.assertTrue(two.equals(result.getMatchWinner()));

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
        Result result = new Result(one,two);
        result.addSet(new Set(one,two,16,1)).addSet(new Set(one,two,16,5));
        Assert.assertTrue(two.equals(result.getMatchLooser()));

    }
}
