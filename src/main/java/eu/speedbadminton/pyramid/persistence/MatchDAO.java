package eu.speedbadminton.pyramid.persistence;

import eu.speedbadminton.pyramid.model.Match;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

/**
 * User: Yoann Moranville
 * Date: 26/06/2013
 *
 * @author Yoann Moranville
 */

@Repository
public class MatchDAO {

    @PersistenceContext
    protected EntityManager entityManager;

    public EntityManager getEntityManager(){
        return entityManager;
    }

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void create(Match match) {
        getEntityManager().persist(match);
    }


    public void delete(Match match) {
        getEntityManager().remove(match);
    }

    public Match find(Long id) {
        return getEntityManager().find(Match.class, id);
    }

    public List<Match> findAll() {
        Query query = getEntityManager().createQuery("from Match");
        List<Match> matches = query.getResultList();
        return matches;
    }

}
