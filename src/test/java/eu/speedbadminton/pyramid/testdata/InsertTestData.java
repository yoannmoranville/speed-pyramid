package eu.speedbadminton.pyramid.testdata;

import eu.speedbadminton.pyramid.context.ApplicationContextSpeedminton;
import eu.speedbadminton.pyramid.model.Match;
import eu.speedbadminton.pyramid.model.Player;
import eu.speedbadminton.pyramid.model.PositionHistory;
import eu.speedbadminton.pyramid.service.MatchService;
import eu.speedbadminton.pyramid.service.PlayerService;
import junit.framework.Assert;
import org.apache.log4j.Logger;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * User: lukasgotter
 * Date: 14.12.13
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:META-INF/context-base.xml"})
public class InsertTestData {

    private static final Logger LOG = Logger.getLogger(InsertTestData.class);


    @Autowired
    private PlayerService playerService;

    @Autowired
    private MatchService matchService;

    @Ignore
    @Test
    public void create36Players() {

        for(int i = 1; i <= 36; i++ ){
            Player p = new Player("Player"+i,"l.gotter+player"+i+"@gmail.com","asdf", Player.Gender.MALE);
            p.setPyramidPosition(playerService.getLastPlayerPosition() + 1);
            playerService.create(p);
        }
        LOG.warn("Created 36 Test Players!");

    }

    @Ignore
    @Test
    public void delete36Players() {

        List<Player> players = playerService.getPlayers();

        for(Player player :players){
            if(player.getName().startsWith("Player")){
                List<Match> matches = matchService.getMatchesOfPlayer(player);
                for(Match match : matches) {
                    matchService.delete(match);
                }
                playerService.delete(player);
            }
        }

        LOG.warn("Deleted 36 Test Players!");

    }

    //@Ignore
    @Test
    public void insertPlayerWithPositionHistory(){

        Player player = new Player("Tester","tester@mail","pwd", Player.Gender.MALE);

        playerService.create(player);

        Assert.assertNotNull(player.getId());

        int originalPyramidPosition = 3;
        player.setPyramidPosition(originalPyramidPosition);


        player.setPyramidPosition(5); // this should add a history to the player

        playerService.update(player);

        // get back the history
        System.out.println(player.getPositionHistoryList());

        Assert.assertNotNull(player.getPositionHistoryList());
        Assert.assertEquals(player.getPositionHistoryList().get(0).getPosition(), originalPyramidPosition);

        playerService.delete(player);
    }




}
