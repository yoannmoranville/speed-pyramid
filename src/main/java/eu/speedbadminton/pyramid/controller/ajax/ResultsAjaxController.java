package eu.speedbadminton.pyramid.controller.ajax;

import eu.speedbadminton.pyramid.model.Match;
import eu.speedbadminton.pyramid.model.Player;
import eu.speedbadminton.pyramid.service.MatchService;
import eu.speedbadminton.pyramid.service.PlayerService;
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
import java.util.Calendar;
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
    @Autowired
    private PlayerService playerService;

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

    @RequestMapping(value={"/saveResults"}, method = RequestMethod.POST)
    public void saveMatchResult(HttpServletRequest request, HttpServletResponse response) throws ParseException {
        try {
            Writer writer = getResponseWriter(response);

            String matchId = request.getParameter("matchId");
            Match match = matchService.getMatchById(matchId);

            String challengerId = request.getParameter("askerId");
            String challengeeId = request.getParameter("askedId");

            String resultSet1Player1 = request.getParameter("results_set1_player1");
            String resultSet1Player2 = request.getParameter("results_set1_player2");
            String resultSet2Player1 = request.getParameter("results_set2_player1");
            String resultSet2Player2 = request.getParameter("results_set2_player2");
            String resultSet3Player1 = request.getParameter("results_set3_player1");
            String resultSet3Player2 = request.getParameter("results_set3_player2");
            Result result = new Result(resultSet1Player1, resultSet1Player2, resultSet2Player1, resultSet2Player2, resultSet3Player1, resultSet3Player2);

            boolean continueTask = true;
            Date date = null;
            try {
                String datePlayed = request.getParameter("datePlayed");
                date = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH).parse(datePlayed);
            } catch(Exception e) {
                writeSimpleData(writer, "errors", "The date is not in the correct dd-MM-yyyy format (example: 15-10-2013 for 15th October 2013)");
                continueTask = false;
            }
            if(continueTask && date == null) {
                writeSimpleData(writer, "errors", "The date is empty");
                continueTask = false;
            }

            if(continueTask && !isDateCorrect(match.getCreation(), date)) {
                writeSimpleData(writer, "errors", "The date is prior to the creation date, it should be after (or same day as) " + match.getCreation());
                continueTask = false;
            }
            if(continueTask && !ResultsUtil.isResultCorrect(result)) {
                writeSimpleData(writer, "errors", "The results are not correct, one of the players needs to win 2 sets, not more, not less");
                continueTask = false;
            }

            if(continueTask) {
                String resultString = ResultsUtil.createResultString(result);

                match.setResult(resultString);
                match.setMatchDate(date);

                matchService.update(match);
                Player challenger = playerService.getPlayerById(challengerId);
                Player challengee = playerService.getPlayerById(challengeeId);
                boolean isChallengerWinner = ResultsUtil.isChallengerWinner(result);

                if(isChallengerWinner) {
                    playerService.swap(challenger, challengee);
                }

                playerService.sendEmailResults(challenger, challengee, isChallengerWinner, result);
                writeSimpleData(writer, "success", "true");
            }
            closeWriter(writer);
        } catch (IOException e) {
            LOG.error("Error...", e);
        }
    }

    private static boolean isDateCorrect(Date creationDate, Date matchDate) {
        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        cal1.setTime(creationDate);
        cal2.setTime(matchDate);
        if(cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) && cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR)) {
            return true;
        } else if(!creationDate.after(matchDate) && matchDate.before(new Date())) {
            return true;
        }
        return false;
    }
}
