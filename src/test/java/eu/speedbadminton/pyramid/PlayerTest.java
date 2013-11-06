package eu.speedbadminton.pyramid;

import eu.speedbadminton.pyramid.model.Player;
import eu.speedbadminton.pyramid.service.PlayerService;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * User: Yoann Moranville
 * Date: 25/06/2013
 *
 * @author Yoann Moranville
 */


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:META-INF/context-base.xml"})
@TransactionConfiguration(transactionManager="transactionManager", defaultRollback=true)
@Transactional
public class PlayerTest {
    private static final Logger LOG = Logger.getLogger(PlayerTest.class);

    @Autowired
    private ApplicationContext context;

    @Test
    public void testReadPlayers() {
        PlayerService playerService = (PlayerService)context.getBean("playerService");
        List<Player> players = playerService.getPlayers();
        for(Player player : players) {
            LOG.info("Player name: " + player.getName());
        }
    }
}
