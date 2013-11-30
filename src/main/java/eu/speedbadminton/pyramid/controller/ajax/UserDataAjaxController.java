package eu.speedbadminton.pyramid.controller.ajax;

import eu.speedbadminton.pyramid.model.Match;
import eu.speedbadminton.pyramid.model.Player;
import eu.speedbadminton.pyramid.service.MatchService;
import eu.speedbadminton.pyramid.service.PlayerService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;
import java.util.List;

/**
 * User: Yoann Moranville
 * Date: 03/10/2013
 *
 * @author Yoann Moranville
 */
@Controller
public class UserDataAjaxController extends AjaxAbstractController {
    private static final Logger LOG = Logger.getLogger(UserDataAjaxController.class);
    private static final String USER_ALREADY_IN_CHALLENGE = "1";
    private static final String YOU_ALREADY_IN_CHALLENGE = "2";
    private static final String USER_NOT_REACHABLE = "3";

    @Autowired
    private PlayerService playerService;
    @Autowired
    private MatchService matchService;

    @RequestMapping(value = {"/usersEncounterQuestion"}, method = RequestMethod.POST)
    public void sendEncounter(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String askerId = request.getParameter("asker");
        Player askerPlayer = playerService.getPlayerById(askerId);
        String askedId = request.getParameter("asked");
        Player askedPlayer = playerService.getPlayerById(askedId);
        Writer writer = null;
        try {
            writer = getResponseWriter(response);

            if (matchService.createMatch(askerPlayer, askedPlayer)){
                if(playerService.sendEmail(askerPlayer, askedPlayer)) {
                    writeSimpleData(writer, "success", "true");
                } else {
                    writeSimpleData(writer, "success", "false");
                }
            }
        } catch (IOException e) {
            LOG.error("Error...", e);
        } finally {
            closeWriter(writer);
        }
    }

    @RequestMapping(value = {"/getUserMatchData"}, method = RequestMethod.POST)
    public void getUserMatchData(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String playerId = request.getParameter("playerid");
        Player player = playerService.getPlayerById(playerId);
        Writer writer = null;
        try {
            writer = getResponseWriter(response);
            List<Match> lastResults = matchService.getLastMatchesWithResults(player);
            List<Match> openChallenges = matchService.getOpenChallenges(player);
            Match openChallenge = null;
            if(openChallenges.size() > 0) {
                openChallenge = openChallenges.get(0);
            }
            writeUserMatchData(writer, lastResults, openChallenge);
        } catch (IOException e) {
            LOG.error("Error...", e);
        } finally {
            closeWriter(writer);
        }
    }
}
