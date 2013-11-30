package eu.speedbadminton.pyramid.model;

import eu.speedbadminton.pyramid.service.PlayerService;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashSet;
import java.util.List;

/**
 * User: Yoann Moranville
 * Date: 25/06/2013
 *
 * @author Yoann Moranville
 */


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:META-INF/context-base.xml"})
public class PlayerTest {
    private static final Logger LOG = Logger.getLogger(PlayerTest.class);

    /*
    @Autowired
    private PlayerService playerService;

    @Test
    public void testReadPlayers() {
        List<Player> players = playerService.getPlayers();
        LOG.info("All players (" + players.size() + ")");
        for(Player player : players) {
            LOG.info("Player name: " + player.getName());
        }
        players = playerService.getEnabledPlayers();
        LOG.info("Only enabled players (" + players.size() + ")");
        for(Player player : players) {
            LOG.info("Player name: " + player.getName());
        }
    }
    */
    @Test
    public void testPlayerEquals(){
        Player p1 = new Player();
        p1.setId("eb10eb24-7e06-47e7-921f-a7ef82168e76");
        Player p2 = new Player();
        p2.setId("eb10eb24-7e06-47e7-921f-a7ef82168e76");

        Assert.assertEquals(p1,p2);

    }

    @Test
    public void testPlayerNotEquals(){
        Player p1 = new Player();
        p1.setId("eb10eb24-7e06-47e7-921f-a7ef82168e76");
        Player p2 = new Player();
        p2.setId("asdfasd-7e06-47e7-921f-a7ef82168e76");

        Assert.assertNotSame(p1, p2);

    }

    @Test
    public void testInChallangeablePlayers(){
        Player p1 = new Player();
        p1.setId("Ab10eb24-7e06-47e7-921f-a7ef82168e76");
        Player p2 = new Player();
        p2.setId("eb10eb24-7e06-47e7-921f-a7ef82168e76");
        Player p3 = new Player();
        p3.setId("eb10eb24-7e06-47e7-921f-a7ef82168e76");

        HashSet<Player> list = new HashSet<Player>();
        list.add(p2);
        Assert.assertTrue(list.contains(p2));

        Assert.assertFalse(list.contains(p1));
        Assert.assertFalse(list.contains(p3));

    }
}
