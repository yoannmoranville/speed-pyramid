package eu.speedbadminton.pyramid.persistence;

import eu.speedbadminton.pyramid.model.Player;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.List;

/**
 * User: Yoann Moranville
 * Date: 25/06/2013
 *
 * @author Yoann Moranville
 */
@Repository
public class PlayerDAO extends AbstractJpaDAO {

    public void create(Player player) {
        getEntityManager().persist(player);
    }

    public void delete(Player player) {
        getEntityManager().remove(player);
    }

    public Player find(Long id) {
        return getEntityManager().find(Player.class, id);
    }

    public List<Player> findAll() {
        Query query = getEntityManager().createQuery("from Player");
        List<Player> players = query.getResultList();
        return players;
    }
}
