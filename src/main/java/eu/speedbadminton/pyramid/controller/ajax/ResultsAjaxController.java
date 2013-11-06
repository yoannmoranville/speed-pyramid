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

/**
 * User: Yoann Moranville
 * Date: 05/11/2013
 *
 * @author Yoann Moranville
 */
@Controller
public class ResultsAjaxController extends AjaxAbstractController {
    private static final Logger LOG = Logger.getLogger(ResultsAjaxController.class);

    @Autowired
    private PlayerService playerService;
    @Autowired
    private MatchService matchService;

    @RequestMapping(value={"/resultColorbox"}, method = RequestMethod.POST)
    public void getMatchData(HttpServletRequest request, HttpServletResponse response) {
        try {
            String matchId = request.getParameter("id");
            Writer writer = getResponseWriter(response);
            LOG.info("Id of match: " + matchId);
            Match match = matchService.getMatchById(matchId);
            Player asker = match.getPlayer1();
            Player asked = match.getPlayer2();
            writeResultData(writer, asker.getName(), asked.getName());
            closeWriter(writer);
        } catch (IOException e) {
            LOG.error("Error...", e);
        }
    }

    @RequestMapping(value={"/saveResults"}, method = RequestMethod.POST)
    public void saveMatchResult(HttpServletRequest request, HttpServletResponse response) {
        try {
            String matchId = request.getParameter("id");
            Writer writer = getResponseWriter(response);
            Match match = matchService.getMatchById(matchId);
            Player asker = match.getPlayer1();
            Player asked = match.getPlayer2();
//            writeUserData(writer, player.getName(), player.getEmail());
            closeWriter(writer);
        } catch (IOException e) {
            LOG.error("Error...", e);
        }
    }
}
