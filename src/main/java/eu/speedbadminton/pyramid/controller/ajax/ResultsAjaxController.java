package eu.speedbadminton.pyramid.controller.ajax;

import eu.speedbadminton.pyramid.model.Match;
import eu.speedbadminton.pyramid.model.Player;
import eu.speedbadminton.pyramid.service.MatchService;
import eu.speedbadminton.pyramid.utils.Result;
import eu.speedbadminton.pyramid.utils.ResultsUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

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
    private MatchService matchService;

    @RequestMapping(value={"/resultColorbox"}, method = RequestMethod.POST)
    public void getMatchData(HttpServletRequest request, HttpServletResponse response) {
        try {
            String matchId = request.getParameter("id");
            Writer writer = getResponseWriter(response);
            Match match = matchService.getMatchById(matchId);
            Player asker = match.getChallenger();
            Player asked = match.getChallengee();
            writeResultData(writer, asker.getName(), asker.getId(), asked.getName(), asked.getId());
            closeWriter(writer);
        } catch (IOException e) {
            LOG.error("Error...", e);
        }
    }

    //TODO
    @RequestMapping(value={"/saveResults"}, method = RequestMethod.POST)
    public void saveMatchResult(HttpServletRequest request, HttpServletResponse response) throws ParseException {
        try {
            String matchId = request.getParameter("matchId");

            String askerId = request.getParameter("askerId");
            String askedId = request.getParameter("askedId");

            String resultSet1Player1 = request.getParameter("results_set1_player1");
            String resultSet1Player2 = request.getParameter("results_set1_player2");
            String resultSet2Player1 = request.getParameter("results_set2_player1");
            String resultSet2Player2 = request.getParameter("results_set2_player2");
            String resultSet3Player1 = request.getParameter("results_set3_player1");
            String resultSet3Player2 = request.getParameter("results_set3_player2");
            Result result = new Result(resultSet1Player1, resultSet1Player2, resultSet2Player1, resultSet2Player2, resultSet3Player1, resultSet3Player2);
            String resultString = ResultsUtil.createResultString(result);

            String datePlayed = request.getParameter("datePlayed"); //dd-MM-yyyy
            Date date = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH).parse(datePlayed);

            Writer writer = getResponseWriter(response);
            Match match = matchService.getMatchById(matchId);

            match.setResult(resultString);
            match.setMatchDate(date);

            matchService.update(matchId, resultString, date);

            writeSimpleData(writer, "success", "true");

            closeWriter(writer);
        } catch (IOException e) {
            LOG.error("Error...", e);
        }
    }
}
