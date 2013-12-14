package eu.speedbadminton.pyramid.service;

import eu.speedbadminton.pyramid.model.Player;
import junit.framework.Assert;
import org.apache.log4j.Logger;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * User: Yoann Moranville
 * Date: 17/09/2013
 *
 * @author Yoann Moranville
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:META-INF/context-base.xml"})
public class PlayerServiceTest {
    private static final Logger LOG = Logger.getLogger(PlayerServiceTest.class);

    @Autowired
    private PlayerService playerService;

    @Test
    public void countPyramidRowsFromNumberOfPlayersTest() {
        Assert.assertEquals(playerService.untilWhichPositionCanPlayerChallenge(15), 10);
        Assert.assertEquals(playerService.untilWhichPositionCanPlayerChallenge(14), 9);
        Assert.assertEquals(playerService.untilWhichPositionCanPlayerChallenge(12), 7);
        Assert.assertEquals(playerService.untilWhichPositionCanPlayerChallenge(11), 7);
        Assert.assertEquals(playerService.untilWhichPositionCanPlayerChallenge(9), 5);
        Assert.assertEquals(playerService.untilWhichPositionCanPlayerChallenge(7), 4);
        Assert.assertEquals(playerService.untilWhichPositionCanPlayerChallenge(6), 3);
        Assert.assertEquals(playerService.untilWhichPositionCanPlayerChallenge(5), 2);
        Assert.assertEquals(playerService.untilWhichPositionCanPlayerChallenge(1), 1);
    }




}

/**
 *         1
 *       2  3
 *     4  5  6
 *   7  8  9  10
 * 11 12 13 14 15
 */
