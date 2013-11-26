package eu.speedbadminton.pyramid.service;

import eu.speedbadminton.pyramid.model.Match;
import eu.speedbadminton.pyramid.model.Player;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

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
        Player player = playerService.getPlayers().get(0);
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
}
