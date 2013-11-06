package eu.speedbadminton.pyramid.service;

import eu.speedbadminton.pyramid.model.Match;
import eu.speedbadminton.pyramid.model.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * User: Yoann Moranville
 * Date: 26/06/2013
 *
 * @author Yoann Moranville
 */
@Service
@Repository
public class MatchService {

    @Autowired
    private MongoTemplate mongoTemplate;
    private static final String COLLECTION_NAME = "match";

    public boolean createMatch(Player asker, Player asked) {
        try {
            Match match = new Match();
            match.setCreation(new Date());
            match.setChallenger(asker);
            match.setChallengee(asked);
            save(match);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public void save(Match match) {
        if (!mongoTemplate.collectionExists(Match.class)) {
            mongoTemplate.createCollection(Match.class);
        }
        match.setId(UUID.randomUUID().toString());
        mongoTemplate.insert(match, COLLECTION_NAME);
    }

    public void update(String matchId, String result, Date date) {
        mongoTemplate.updateMulti(new Query(Criteria.where("_id").is(matchId)),
                new Update().set("result", result), COLLECTION_NAME);
        mongoTemplate.updateMulti(new Query(Criteria.where("_id").is(matchId)),
                new Update().set("matchDate", date), COLLECTION_NAME);
    }

    public Match getMatchById(String matchId) {
        return mongoTemplate.findOne(new Query(Criteria.where("_id").is(matchId)), Match.class, COLLECTION_NAME);
    }

    public List<Match> getMatches() {
        return mongoTemplate.findAll(Match.class, COLLECTION_NAME);
    }

}
