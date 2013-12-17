package eu.speedbadminton.pyramid.service;

import eu.speedbadminton.pyramid.model.Match;
import eu.speedbadminton.pyramid.model.Player;
import eu.speedbadminton.pyramid.model.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * User: Yoann Moranville
 * Date: 26/06/2013
 *
 * @author Yoann Moranville
 */
@Service
public class MatchService {

    @Autowired
    private MongoTemplate mongoTemplate;
    private static final String COLLECTION_NAME = "match";
    private static final int MAX_RESULTS_PAST_MATCHES = 5;

    public boolean createMatch(Player asker, Player asked) {
        try {
            Match match = new Match();
            match.setCreation(new Date());
            match.setChallenger(asker);
            match.setChallengee(asked);
            create(match);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public void create(Match match) {
        if (!mongoTemplate.collectionExists(Match.class)) {
            mongoTemplate.createCollection(Match.class);
        }
        mongoTemplate.insert(match, COLLECTION_NAME);
    }

    public void update(Match match) {
        mongoTemplate.save(match, COLLECTION_NAME);
    }

    public Match getMatchById(String matchId) {
        return mongoTemplate.findOne(new Query(Criteria.where("_id").is(matchId)), Match.class, COLLECTION_NAME);
    }

    public Match getMatchByValidationId(String validationId) {
        return mongoTemplate.findOne(new Query(Criteria.where("validationId").is(validationId)), Match.class, COLLECTION_NAME);
    }

    public List<Match> getMatches() {
        return mongoTemplate.findAll(Match.class, COLLECTION_NAME);
    }

    public void deleteMatchesOfPlayer(Player player) {
        Criteria criteria = new Criteria().orOperator(Criteria.where("challenger.$id").is(player.getId()), Criteria.where("challengee.$id").is(player.getId()));
        Query query = new Query(criteria);
        mongoTemplate.remove(query, COLLECTION_NAME);
    }

    public void delete(Match match) {
        mongoTemplate.remove(match, COLLECTION_NAME);
    }

    private List<Match> getLastMatchesWithResults(int max) {
        Criteria criteria = Criteria.where("confirmed").is(true);
        Query query = new Query(criteria);
        if(max > -1)
            query.limit(max);
        query.with(new Sort(Sort.Direction.DESC, "matchDate"));
        return mongoTemplate.find(query, Match.class, COLLECTION_NAME);
    }
    public List<Match> getLastMatchesWithResults() {
        return getLastMatchesWithResults(MAX_RESULTS_PAST_MATCHES);
    }
    public List<Match> getAllLastMatchesWithResults() {
        return getLastMatchesWithResults(-1);
    }

    public List<Match> getMatchesOfPlayer(Player player) {
        if(player == null)
            return Collections.emptyList();
        Criteria criteria = new Criteria().orOperator(Criteria.where("challenger.$id").is(player.getId()), Criteria.where("challengee.$id").is(player.getId()));
        Query query = new Query(criteria).with(new Sort(Sort.Direction.DESC, "matchDate"));
        return mongoTemplate.find(query, Match.class, COLLECTION_NAME);
    }

    public List<Match> getLastMatchesWithResults(Player player) {
        if (player==null){
            return Collections.emptyList();
        }
        Criteria criteria = Criteria.where("confirmed").is(true);
        Criteria criteriaOr = new Criteria().orOperator(Criteria.where("challenger.$id").is(player.getId()), Criteria.where("challengee.$id").is(player.getId()));
        criteria.andOperator(criteriaOr);
        Query query = new Query(criteria).limit(MAX_RESULTS_PAST_MATCHES);
        query.with(new Sort(Sort.Direction.DESC, "matchDate"));
        return mongoTemplate.find(query, Match.class, COLLECTION_NAME);
    }

    public Match getUnconfirmedMatch(Player player) {
        if (player==null){
            return null;
        }
        Criteria criteria = Criteria.where("confirmed").is(false).and("matchDate").exists(true);
        Criteria criteriaOr = new Criteria().orOperator(Criteria.where("challenger.$id").is(player.getId()), Criteria.where("challengee.$id").is(player.getId()));
        criteria.andOperator(criteriaOr);
        Query query = new Query(criteria).limit(MAX_RESULTS_PAST_MATCHES);
        query.with(new Sort(Sort.Direction.DESC, "matchDate"));
        List<Match> matches = mongoTemplate.find(query, Match.class, COLLECTION_NAME);

        if (matches.size()>0){
            return matches.get(0);
        }

        return null;
    }

    private List<Match> getUnconfirmedMatches(int max) {
        Criteria criteria = Criteria.where("confirmed").is(false).and("matchDate").exists(true);
        Query query = new Query(criteria);
        if(max > -1)
            query.limit(max);
        query.with(new Sort(Sort.Direction.DESC, "matchDate"));
        List<Match> matches = mongoTemplate.find(query, Match.class, COLLECTION_NAME);

        return matches;
    }
    public List<Match> getAllUnconfirmedMatch() {
        return getUnconfirmedMatches(-1);
    }
    public List<Match> getUnconfirmedMatches() {
        return getUnconfirmedMatches(MAX_RESULTS_PAST_MATCHES);
    }

    /**
     * Winner waiting for confirmation.
     * @param loggedPlayer
     * @return
     */
    public Match getWaitingForConfirmationMatch(Player loggedPlayer){
        Match match = getUnconfirmedMatch(loggedPlayer);

        if (match!=null){

            Result result = match.getResult();
            if (result.getMatchWinner().equals(loggedPlayer)){
                return match;
            }
        }
        return null;
    }

    /**
     * Looser has to confirm match
     * @return
     */
    public Match getUnconfirmedLostMatch(Player loggedPlayer){
        Match match = getUnconfirmedMatch(loggedPlayer);

        if (match!=null){
            Result result = match.getResult();
            if (result.getMatchLooser().equals(loggedPlayer)){
                return match;
            }
        }
        return null;
    }

    public List<Match> getOpenChallenges() {
        return getOpenChallenges(null, MAX_RESULTS_PAST_MATCHES);
    }

    public List<Match> getOpenChallenges(Player player) {
        return getOpenChallenges(player, MAX_RESULTS_PAST_MATCHES);
    }

    public List<Match> getAllOpenChallenges() {
        return getOpenChallenges(null, -1);
    }

    public List<Match> getOpenChallenges(Player player, int max) {
        Criteria criteria = Criteria.where("matchDate").exists(false);
        Criteria criteriaOr = new Criteria().orOperator(Criteria.where("matchDate").exists(false), Criteria.where("matchDate").exists(true).and("confirmed").is(false));
        criteria.andOperator(criteriaOr);
        if(player != null) {
            Criteria criteriaOr2 = new Criteria().orOperator(Criteria.where("challenger.$id").is(player.getId()), Criteria.where("challengee.$id").is(player.getId()));
            criteria.andOperator(criteriaOr2);
        }
        Query query = new Query(criteria);
        if(max > -1)
            query.limit(max);
        query.with(new Sort(Sort.Direction.DESC, "creation"));
        return mongoTemplate.find(query, Match.class, COLLECTION_NAME);
    }

    public int getMatchesWon(Player player) {
        return getMatchesWonLost(player, true);
    }
    public int getMatchesLost(Player player) {
        return getMatchesWonLost(player, false);
    }

    private int getMatchesWonLost(Player player, boolean isWon) {
        return getMatchesWonLost(player, getMatchesOfPlayer(player), isWon);
    }

    protected int getMatchesWonLost(Player player, List<Match> matchesOfPlayer, boolean isWon) {
        int number = 0;
        for(Match match : matchesOfPlayer) {
            if(match.getResult() != null && match.isConfirmed()) {
                if(isWon && match.getResult().getMatchWinner().equals(player)) {
                    number++;
                } else if(!isWon && match.getResult().getMatchLooser().equals(player)) {
                    number++;
                }
            }
        }
        return number;
    }
}
