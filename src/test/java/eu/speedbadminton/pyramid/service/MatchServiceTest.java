package eu.speedbadminton.pyramid.service;

import eu.speedbadminton.pyramid.model.Match;
import eu.speedbadminton.pyramid.model.Player;
import eu.speedbadminton.pyramid.utils.Result;
import eu.speedbadminton.pyramid.utils.Set;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;

/**
 * User: Yoann Moranville
 * Date: 25/11/2013
 *
 * @author Yoann Moranville
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:META-INF/context-base.xml"})
public class MatchServiceTest {
    private static final Logger LOG = Logger.getLogger(PlayerServiceTest.class);

    @Autowired
    private MatchService matchService;

    @Autowired
    private PlayerService playerService;

    @Test
    public void getLastMatchesWithResultsTest() {
        LOG.info("-- Last 5 Matches with results --");
        for(Match match : matchService.getLastMatchesWithResults()) {
            LOG.info(match);
        }
    }

    @Test
    public void getOpenChallengesTest() {
        LOG.info("-- Last 5 Matches still in open challenge --");
        for(Match match : matchService.getOpenChallenges()) {
            LOG.info(match);
        }
    }

    @Test
    public void getLastMatchesWithResultsTestWithPlayer() {
        Player player = playerService.getPlayers().get(5);
        LOG.info("-- Last 5 Matches with results of " + player.getName() + " --");
        for(Match match : matchService.getLastMatchesWithResults(player)) {
            LOG.info(match);
        }
    }

    @Test
    public void getOpenChallengesTestWithPlayer() {
        Player player = playerService.getPlayers().get(0);
        LOG.info("-- Open challenge for " + player.getName() + " --");
        for(Match match : matchService.getOpenChallenges(player)) {
            LOG.info(match);
        }
    }

    @Test
    public void hasCorrectWinner(){
        Player player1 = new Player();
        player1.setId("one");
        Player player2 = new Player();
        player2.setId("two");

        Match match = new Match();
        match.setChallenger(player1);
        match.setChallengee(player2);

        Result result = new Result(player1,player2);
        result.addSet(new Set(player1,player2,16,0)).addSet(new Set(player1,player2,9,16)).addSet(new Set(player1,player2,16,7));

        Assert.assertTrue(result.getMatchWinner().equals(player1));
        Assert.assertTrue(result.getMatchLooser().equals(player2));

        result.setSets(new ArrayList<Set>());
        result.addSet(new Set(player1,player2,0,16)).addSet(new Set(player1,player2,9,16));
        Assert.assertTrue(result.getMatchWinner().equals(player2));
        Assert.assertTrue(result.getMatchLooser().equals(player1));

    }
}
