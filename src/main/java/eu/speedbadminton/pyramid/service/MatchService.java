package eu.speedbadminton.pyramid.service;

import eu.speedbadminton.pyramid.model.Match;
import eu.speedbadminton.pyramid.model.Player;
import eu.speedbadminton.pyramid.utils.Result;
import eu.speedbadminton.pyramid.utils.ResultsUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;
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
        match.setId(UUID.randomUUID().toString());
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

    public List<Match> getLastMatchesWithResults() {
        Criteria criteria = Criteria.where("confirmed").is(true);
        Query query = new Query(criteria).limit(MAX_RESULTS_PAST_MATCHES);
        query.with(new Sort(Sort.Direction.DESC, "matchDate"));
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

    private Match getUnconfirmedMatch(Player player) {
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

    /**
     * Winner waiting for confirmation.
     * @param loggedPlayer
     * @return
     */
    public Match getWaitingForConfirmationMatch(Player loggedPlayer){
        Match match = getUnconfirmedMatch(loggedPlayer);

        if (match!=null){
            Result result = ResultsUtil.parseResultString(match.getResult());
            if (ResultsUtil.getWinner(match,result).equals(loggedPlayer)){
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
            Result result = ResultsUtil.parseResultString(match.getResult());
            if (ResultsUtil.getLooser(match, result).equals(loggedPlayer)){
                return match;
            }
        }
        return null;
    }

    public List<Match> getOpenChallenges() {
        return getOpenChallenges(null);
    }

    public List<Match> getOpenChallenges(Player player) {
        Criteria criteria = Criteria.where("matchDate").exists(false);
        if(player != null) {
            Criteria criteriaOr = new Criteria().orOperator(Criteria.where("challenger.$id").is(player.getId()), Criteria.where("challengee.$id").is(player.getId()));
            criteria.andOperator(criteriaOr);
        }
        Query query = new Query(criteria).limit(MAX_RESULTS_PAST_MATCHES);
        query.with(new Sort(Sort.Direction.DESC, "creation"));
        return mongoTemplate.find(query, Match.class, COLLECTION_NAME);
    }
}
