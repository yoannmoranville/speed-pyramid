package eu.speedbadminton.pyramid.service;

import eu.speedbadminton.pyramid.model.Match;
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

    @Test
    public void getLastMatchesWithResultsTest() {
        LOG.info("-- Last 5 Matches with resultd --");
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
}
