package eu.speedbadminton.pyramid.service;

import eu.speedbadminton.pyramid.model.Match;
import eu.speedbadminton.pyramid.persistence.MatchDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * User: Yoann Moranville
 * Date: 26/06/2013
 *
 * @author Yoann Moranville
 */
@Service
public class MatchService {

    @Autowired
    private MatchDAO matchDAO;

    @Transactional
    public void save(Match match) {
        matchDAO.create(match);
    }

    @Transactional
    public Match getMatchById(long matchId) {
        return matchDAO.find(matchId);
    }

    @Transactional
    public List<Match> getMatches() {
        return matchDAO.findAll();
    }

}
