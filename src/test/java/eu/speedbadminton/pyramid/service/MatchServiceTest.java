package eu.speedbadminton.pyramid.service;

import eu.speedbadminton.pyramid.model.Match;
import eu.speedbadminton.pyramid.model.Player;
import eu.speedbadminton.pyramid.model.Result;
import eu.speedbadminton.pyramid.model.Set;
import org.apache.log4j.Logger;
import org.bson.types.ObjectId;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

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

    @Ignore
    @Test
    public void getLastMatchesWithResultsTestWithPlayer() {
        List<Player> players = playerService.getPlayers();

        Player player = players.get(0);
        LOG.info("-- Last 5 Matches with results of " + player.getName() + " --");
        for(Match match : matchService.getLastMatchesWithResults(player)) {
            LOG.info(match);
        }
    }

    @Ignore
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
        player1.setId(new ObjectId());
        Player player2 = new Player();
        player2.setId(new ObjectId());

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

    @Test
    public void getMatchesWonLostTest() {
        Player challenger = createPlayer();
        Player challengee = createPlayer();

        List<Match> matchesOfPlayer = new ArrayList<Match>();

        Match match = createMatch(challenger, challengee);
        Result result = new Result(challenger, challengee);
        result.addSet(new Set(challenger, challengee, 16, 0))
                .addSet(new Set(challenger, challengee, 16, 7));
        match.setResult(result);
        match.setConfirmed(true);
        matchesOfPlayer.add(match);

        match = createMatch(challenger, challengee);
        result = new Result(challenger, challengee);
        result.addSet(new Set(challenger, challengee, 16, 7))
                .addSet(new Set(challenger, challengee, 16, 12));
        match.setResult(result);
        match.setConfirmed(true);
        matchesOfPlayer.add(match);

        match = createMatch(challenger, challengee);
        result = new Result(challenger, challengee);
        result.addSet(new Set(challenger, challengee, 16, 7))
                .addSet(new Set(challenger, challengee, 16, 18))
                .addSet(new Set(challenger, challengee, 1, 16));
        match.setResult(result);
        match.setConfirmed(true);
        matchesOfPlayer.add(match);

        Assert.assertEquals(matchService.getMatchesWonLost(challenger, matchesOfPlayer, true), 2);
        Assert.assertEquals(matchService.getMatchesWonLost(challenger, matchesOfPlayer, false), 1);
        Assert.assertEquals(matchService.getMatchesWonLost(challengee, matchesOfPlayer, true), 1);
        Assert.assertEquals(matchService.getMatchesWonLost(challengee, matchesOfPlayer, false), 2);
    }

    private Player createPlayer() {
        Player player = new Player();
        player.setId(new ObjectId());
        return player;
    }
    private Match createMatch(Player challenger, Player challengee) {
        Match match = new Match();
        match.setChallenger(challenger);
        match.setChallengee(challengee);
        return match;
    }
}
