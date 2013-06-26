package eu.speedbadminton.pyramid.persistence;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * User: Yoann Moranville
 * Date: 26/06/2013
 *
 * @author Yoann Moranville
 */
public class AbstractJpaDAO {

    @PersistenceContext
    protected EntityManager entityManager;

    public EntityManager getEntityManager(){
        return entityManager;
    }

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

}
