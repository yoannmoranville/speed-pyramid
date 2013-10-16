package eu.speedbadminton.pyramid.service;

/**
 * User: Yoann Moranville
 * Date: 25/06/2013
 *
 * @author Yoann Moranville
 */
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.google.gson.Gson;
import eu.speedbadminton.pyramid.mail.MailService;
import eu.speedbadminton.pyramid.model.Match;
import eu.speedbadminton.pyramid.model.Player;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Order;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;


@Service
@Repository
public class PlayerService {
    private static final Logger LOG = Logger.getLogger(PlayerService.class);

    @Autowired
    private MongoTemplate mongoTemplate;
    private static final String COLLECTION_NAME_PLAYER = "player";
    private static final String COLLECTION_NAME_MATCH = "match";

    public void save(Player player) {
        if (!mongoTemplate.collectionExists(Player.class)) {
            mongoTemplate.createCollection(Player.class);
        }
        player.setId(UUID.randomUUID().toString());
        mongoTemplate.insert(player, COLLECTION_NAME_PLAYER);
    }

    public void delete(Player player) {
        mongoTemplate.remove(player, COLLECTION_NAME_PLAYER);
    }

    public void updatePlayer(Player player) {
        mongoTemplate.insert(player, COLLECTION_NAME_PLAYER);
    }

    public Player getPlayerById(String playerId) {
        return mongoTemplate.findOne(new Query(Criteria.where("_id").is(playerId)), Player.class, COLLECTION_NAME_PLAYER);
    }

    public List<Player> getPlayers() {
        Query query = new Query();
        query.with(new Sort(Sort.Direction.ASC, "pyramidPosition"));
        return mongoTemplate.find(query, Player.class, COLLECTION_NAME_PLAYER);
    }

    public long getLastPlayerPosition() {
        Query query = new Query().limit(1);
        query.with(new Sort(Sort.Direction.DESC, "pyramidPosition"));
        Player player = mongoTemplate.findOne(query, Player.class, COLLECTION_NAME_PLAYER);
        return player.getPyramidPosition();
    }

    public List<Match> getMatchesOfPlayer(Player player) {
        Criteria criteria = new Criteria().orOperator(Criteria.where("player1_id").is(player.getId()), Criteria.where("player2_id").is(player.getId()));
        Query query = new Query(criteria);
        return mongoTemplate.find(query, Match.class, COLLECTION_NAME_MATCH);
    }

    public long untilWhichPositionCanPlayerChallenge(Player player) {
        return untilWhichPositionCanPlayerChallenge(player.getPyramidPosition());
    }

    public long untilWhichPositionCanPlayerChallenge(long position) {
        return position - Math.round(Math.sqrt(2*(position - 1)));
    }

    public String getAvailablePlayerIds(String yourId) {
        long yourPosition = getPlayerById(yourId).getPyramidPosition();
        long untilPosition = untilWhichPositionCanPlayerChallenge(yourPosition);
        List<String> availablePlayerIds = new ArrayList<String>();
        for(long position = yourPosition-1; position >= untilPosition; position--) {
            Player player = getPlayerWithPosition(position);
            boolean isBusy = false;
            for(Match match : player.getMatches()) {
                if(match.getMatchDate() == null) {
                    isBusy = true;
                }
            }
            if(!isBusy)
                availablePlayerIds.add(getPlayerWithPosition(position).getId());
        }
        return new Gson().toJson(availablePlayerIds);
    }

    public Player getPlayerWithPosition(long position) {
        return mongoTemplate.findOne(new Query(Criteria.where("pyramidPosition").is(position)), Player.class, COLLECTION_NAME_PLAYER);
    }

    public Player login(String email, String password) {
//        LOG.info(password);
        Criteria criteria = new Criteria().andOperator(Criteria.where("email").is(email), Criteria.where("password").is(password));
        Query query = new Query(criteria);
        return mongoTemplate.findOne(query, Player.class, COLLECTION_NAME_PLAYER);
    }

    public boolean sendEmail(Player asker, Player asked) {
        MailService.sendEmail(asker.getEmail(), "blabla", asked.getEmail(), asker.getEmail());
        return true;
    }
}