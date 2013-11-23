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

    public void delete(String playerId) {
        Player player = getPlayerById(playerId);
        delete(player);
    }

    public void delete(Player player) {
        mongoTemplate.remove(player, COLLECTION_NAME_PLAYER);
    }

    public void addOnePositionToPlayers(long firstPositionToBeFilled) {
        List<Player> players = getPlayers();
        for(Player player : players) {
            if(player.getPyramidPosition() > firstPositionToBeFilled) {
                player.setPyramidPosition(player.getPyramidPosition() - 1);
                update(player);
            }
        }
    }

    public void update(Player player) {
        mongoTemplate.save(player, COLLECTION_NAME_PLAYER);
    }

    public void swap(String challengerId, String challengeeId) {
        Player challengee = getPlayerById(challengeeId);
        Player challenger = getPlayerById(challengerId);

        swap(challenger, challengee);
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

    public List<Player> getEnabledPlayers() {
        Query query = new Query(Criteria.where("enabled").is(true));
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
        Criteria criteria = new Criteria().orOperator(Criteria.where("challenger.$id").is(player.getId()), Criteria.where("challengee.$id").is(player.getId()));
        Query query = new Query(criteria);
        return mongoTemplate.find(query, Match.class, COLLECTION_NAME_MATCH);
    }

    public List<Match> getPlayedMatchesOfPlayer(Player player) {
        Criteria criteria = new Criteria().orOperator(Criteria.where("challenger.$id").is(player.getId()), Criteria.where("challengee.$id").is(player.getId())).andOperator(Criteria.where("matchDate").exists(true));
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
        return new Gson().toJson(getAvailablePlayers(yourId));
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

    public void sendEmailResultsLooserValidation(Player looser, Player winner, Result result, String link) {
        String body = "And the results of your match:\n" +
                "Winner: " + winner.getName() + "\n" +
                "Looser: " + looser.getName() + "\n" +
                "\n" +
                "You lost: " + ResultsUtil.createResultString(result) + "\n" +
                "\n" +
                "Please confirm that you lost by clicking this link: " + link + "\n" +
                "\n";

        MailService.sendEmailResultsLooserValidation(body, looser.getEmail(), looser.getName());
    }

    public void sendEmailResultsWaitingForLooserValidation(Player winner, Player looser, Result result) {
        String body = "And the results of your match:\n" +
                "Winner: " + winner.getName() + "\n" +
                "Looser: " + looser.getName() + "\n" +
                "\n" +
                "You won: " + ResultsUtil.createResultString(result) + "\n" +
                "\n" +
                "Please wait for the looser to confirm the score, you will receive an email when this is done.\n" +
                "If it takes too long, please write the admins about it\n" +
                "\n";

        MailService.sendEmailResultsWaitingForLooserValidation(body, winner.getEmail(), winner.getName());
    }

    public List<String> getAvailablePlayers(String id) {
        long yourPosition = getPlayerById(id).getPyramidPosition();
        long untilPosition = untilWhichPositionCanPlayerChallenge(yourPosition);
        List<String> availablePlayerIds = new ArrayList<String>();
        for(long position = yourPosition-1; position >= untilPosition; position--) {
            Player player = getPlayerWithPosition(position);

            assert player!=null;

            boolean isBusy = false;
            for(Match match : getMatchesOfPlayer(player)) {
                if(match.getMatchDate() == null || match.getValidationId() != null) {
                    isBusy = true;
                }
            }
            if(!isBusy)
                availablePlayerIds.add(getPlayerWithPosition(position).getId());
        }
        return availablePlayerIds;
    }
}