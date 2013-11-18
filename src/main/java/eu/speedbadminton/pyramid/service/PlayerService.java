package eu.speedbadminton.pyramid.service;

/**
 * User: Yoann Moranville
 * Date: 25/06/2013
 *
 * @author Yoann Moranville
 */
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.google.gson.Gson;
import eu.speedbadminton.pyramid.mail.MailService;
import eu.speedbadminton.pyramid.model.Match;
import eu.speedbadminton.pyramid.model.Player;
import eu.speedbadminton.pyramid.utils.Result;
import eu.speedbadminton.pyramid.utils.ResultsUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Order;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
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

    public void create(Player player) {
        if (!mongoTemplate.collectionExists(Player.class)) {
            mongoTemplate.createCollection(Player.class);
        }
        player.setId(UUID.randomUUID().toString());
        mongoTemplate.insert(player, COLLECTION_NAME_PLAYER);
    }

    public void delete(Player player) {
        mongoTemplate.remove(player, COLLECTION_NAME_PLAYER);
    }

    public void update(Player player) {
        mongoTemplate.save(player, COLLECTION_NAME_PLAYER);
    }

    public void swap(Player challenger, Player challengee) {
        long newChallengerPosition = challengee.getPyramidPosition();
        long newChallengeePosition = challenger.getPyramidPosition();

        challenger.setPyramidPosition(newChallengerPosition);
        challengee.setPyramidPosition(newChallengeePosition);

        update(challenger);
        update(challengee);
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
        Criteria criteria = new Criteria().orOperator(Criteria.where("challenger_id").is(player.getId()), Criteria.where("challengee_id").is(player.getId()));
        Query query = new Query(criteria);
        return mongoTemplate.find(query, Match.class, COLLECTION_NAME_MATCH);
    }

    //todo: Use inside the colorbox of users when requesting an encounter
    public List<Match> getPlayedMatchesOfPlayer(Player player) {
        Criteria criteria = new Criteria().orOperator(Criteria.where("challenger_id").is(player.getId()), Criteria.where("challengee_id").is(player.getId())).andOperator(Criteria.where("matchDate").exists(true));
        Query query = new Query(criteria);
        query.with(new Sort(Sort.Direction.DESC, "matchDate"));
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
            for(Match match : getMatchesOfPlayer(player)) {
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
        Criteria criteria = new Criteria().andOperator(Criteria.where("email").is(email), Criteria.where("password").is(password));
        Query query = new Query(criteria);
        return mongoTemplate.findOne(query, Player.class, COLLECTION_NAME_PLAYER);
    }

    public boolean sendEmail(Player asker, Player asked) {
        String body = "I want to destroy you on the field! I will take your pyramid place!\n" +
                "Take care and reply to me so we can find a place where to meet and play together!\n" +
                "\n" +
                "Once you have played together, one of you will need to enter the results online.\n" +
                "BE CAREFUL! You only have 21 days to play, after this, you will be automatically removed from your place!";
        MailService.sendEmailEncounter(asker.getEmail(), body, asked.getEmail(), asked.getName(), asker.getName());
        return true;
    }

    public boolean sendEmailResults(Player asker, Player asked, boolean isChallengerWinner, Result result) {
        String winnerName;
        if(isChallengerWinner) {
            winnerName = asker.getName();
        } else {
            winnerName = asked.getName();
        }

        String body = "And the results of your match:\n" +
                "Challenger: " + asker.getName() + "\n" +
                "Challengee: " + asked.getName() + "\n" +
                "\n" +
                winnerName + " won!\n" +
                "\n" +
                asker.getName() + " vs " + asked.getName() + ": " + ResultsUtil.createResultString(result);

        MailService.sendEmailResults(asker.getEmail(), body, asked.getEmail(), asked.getName(), asker.getName());
        return true;
    }

    public void sendEmailPassword(String name, String email, String password) {
        String body = "In order to connect to the pyramid system of Gekkos Berlin, you will need to use those credentials:\n" +
                "Username: " + email + "\n" +
                "Password: " + password;

        MailService.sendEmailPassword(body, email, name);
    }
}