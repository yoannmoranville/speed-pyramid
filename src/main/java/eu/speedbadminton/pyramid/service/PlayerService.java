package eu.speedbadminton.pyramid.service;

/**
 * User: Yoann Moranville
 * Date: 25/06/2013
 *
 * @author Yoann Moranville
 */
import java.util.List;

import eu.speedbadminton.pyramid.persistence.PlayerDAO;
import eu.speedbadminton.pyramid.model.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class PlayerService {

    @Autowired
    private PlayerDAO playerDAO;

    @Transactional
    public void save(Player player) {
        playerDAO.create(player);
    }

    @Transactional
    public Player getPlayerById(long playerId) {
        return playerDAO.find(playerId);
    }

    @Transactional
    public List<Player> getPlayers() {
        return playerDAO.findAll();
    }
}