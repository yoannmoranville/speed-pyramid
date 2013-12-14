package eu.speedbadminton.pyramid.controller.ajax;

import eu.speedbadminton.pyramid.listener.SpeedbadmintonConfig;
import eu.speedbadminton.pyramid.model.Match;
import eu.speedbadminton.pyramid.model.Player;
import eu.speedbadminton.pyramid.security.PasswordGenerator;
import eu.speedbadminton.pyramid.security.SecurityContext;
import eu.speedbadminton.pyramid.service.MatchService;
import eu.speedbadminton.pyramid.service.PlayerService;
import eu.speedbadminton.pyramid.model.Result;
import eu.speedbadminton.pyramid.model.Set;
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

    @RequestMapping(value={"/saveResults"}, method = RequestMethod.POST)
    public void saveMatchResult(HttpServletRequest request, HttpServletResponse response) throws IOException {
        if(SecurityContext.get() == null) {
            throw new IllegalAccessError("Please authenticate");
        }

        Player loggedPlayer = playerService.getPlayerById(SecurityContext.get().getPlayerId());

        Writer writer = getResponseWriter(response);

        String matchId = request.getParameter("matchid");
        Match match = matchService.getMatchById(matchId);

        String challengeeId = request.getParameter("challengeeid");
        String challengerId = request.getParameter("challengerid");

        Player challengeePlayer = playerService.getPlayerById(challengeeId);
        Player challengerPlayer = playerService.getPlayerById(challengerId);

        Result result = new Result(challengerPlayer,challengeePlayer);

        String resultSet1Player1 = request.getParameter("results_set1_player1");
        String resultSet1Player2 = request.getParameter("results_set1_player2");
        if (!resultSet1Player1.isEmpty()&& !resultSet1Player2.isEmpty()){
            result.addSet(new Set(challengerPlayer,challengeePlayer,getPointsInteger(resultSet1Player1),getPointsInteger(resultSet1Player2)));
        }
        String resultSet2Player1 = request.getParameter("results_set2_player1");
        String resultSet2Player2 = request.getParameter("results_set2_player2");
        if (!resultSet2Player1.isEmpty() && ! resultSet2Player2.isEmpty()){
            result.addSet(new Set(challengerPlayer,challengeePlayer,getPointsInteger(resultSet2Player1),getPointsInteger(resultSet2Player2)));
        }
        String resultSet3Player1 = request.getParameter("results_set3_player1");
        String resultSet3Player2 = request.getParameter("results_set3_player2");
        if (!resultSet3Player1.isEmpty() && !resultSet3Player2.isEmpty()){
            result.addSet(new Set(challengerPlayer, challengeePlayer,getPointsInteger(resultSet3Player1),getPointsInteger(resultSet3Player2)));
        }

        boolean continueTask = true;
        Date date = null;
        try {
            String datePlayed = request.getParameter("datePlayed");
            date = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH).parse(datePlayed);
            match.setMatchDate(date);
        } catch(Exception e) {
            writeSimpleData(writer, "errors", "The date is not in the correct dd-MM-yyyy format (example: 15-10-2013 for 15th October 2013)");
            continueTask = false;
        }
        if(continueTask && date == null) {
            writeSimpleData(writer, "errors", "The date is empty");
            continueTask = false;
        }

        if(continueTask && !isDateCorrect(match.getCreation(), match.getMatchDate())) {
            writeSimpleData(writer, "errors", "The date is prior to the creation date, it should be later than the creation date, but not after current date... Creation date was: " + match.getCreation());
            continueTask = false;
        }
        if(continueTask && !result.isResultCorrect()) {
            writeSimpleData(writer, "errors", "The results are not correct, one of the players needs to win 2 sets, not more, not less");
            continueTask = false;
        }

        // validation completed
        if(continueTask) {
            processResult(match, result,loggedPlayer,challengerPlayer,challengeePlayer);
            response.setStatus(200);
            writeSimpleData(writer, "success", "true");
        } else {
            response.setStatus(400);
        }

        closeWriter(writer);

    }

    /**
     * Save this result to DB - if logged Player lost, just switch positions. if logged Player won, the looser has to confirm.
     * @param match
     * @param result
     * @param loggedPlayer
     * @param challengerPlayer
     * @param challengeePlayer
     */
    private void processResult(Match match, Result result, Player loggedPlayer, Player challengerPlayer, Player challengeePlayer) {
        match.setResult(result);

        Player winner = result.getMatchWinner();
        Player looser = result.getMatchLooser();

        if(loggedPlayer.equals(looser)) {
            match.setConfirmed(true);
            matchService.update(match);
            if(loggedPlayer.getPyramidPosition() < winner.getPyramidPosition()) {
                playerService.swap(loggedPlayer, winner);
            } else {
                playerService.sendEmailResults(challengerPlayer, challengeePlayer, ResultsUtil.isChallengerWinner(result), result);
            }
        } else {
            match.setConfirmed(false);
            String validationId = PasswordGenerator.getRandomString();
            match.setValidationId(validationId);
            matchService.update(match);

            String validationLink = SpeedbadmintonConfig.getLinkServer() + validationId;
            LOG.info("New Validation Link: "+validationLink);

            playerService.sendEmailResultsLooserValidation(looser, winner, result, validationLink);
        }
    }


    protected static boolean isDateCorrect(Date creationDate, Date matchDate) {
        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        Calendar calTomorrow = Calendar.getInstance();
        calTomorrow.add(Calendar.DAY_OF_MONTH,1);
        calTomorrow.clear(Calendar.MILLISECOND);
        calTomorrow.clear(Calendar.SECOND);
        calTomorrow.clear(Calendar.MINUTE);
        calTomorrow.clear(Calendar.HOUR);

        cal1.setTime(creationDate);
        cal2.setTime(matchDate);
        if(cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) && cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR)) {
            return true;
        } else if(!creationDate.after(matchDate) && matchDate.before(calTomorrow.getTime())) {
            return true;
        }
        return false;
    }

    private static Integer getPointsInteger(String points) {
        try {
            return Integer.parseInt(points);
        } catch (NumberFormatException e){
            LOG.debug("A Point Score could not be parsed (value:"+points+")");
            return null;
        }

    }


    @RequestMapping(value={"/confirmMatchResults"}, method = RequestMethod.POST)
    public void handleRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Writer writer = getResponseWriter(response);

        if(SecurityContext.get() != null) {
            String id = SecurityContext.get().getPlayerId();
            Player loggedPlayer = playerService.getPlayerById(id);

            // you can only confirm a match where you are the looser
            Match match = matchService.getUnconfirmedLostMatch(loggedPlayer);
            // TODO check if match id is same but should be as there is always max 1 match unconfirmed
            match.setConfirmed(true);
            matchService.update(match);
            playerService.swap(match.getChallenger(),match.getChallengee());

            // TODO we have to get the result and determin winner for the email...
            writeSimpleData(writer,"success","true");
        } else {
            writeSimpleData(writer, "success", "false");
        }
        closeWriter(writer);
    }
     
}
