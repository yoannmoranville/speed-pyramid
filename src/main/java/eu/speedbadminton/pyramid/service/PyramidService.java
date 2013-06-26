package eu.speedbadminton.pyramid.service;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

/**
 * User: Yoann Moranville
 * Date: 26/06/2013
 *
 * @author Yoann Moranville
 */

@Service
public class PyramidService {
    private static final Logger LOG = Logger.getLogger(PyramidService.class);

    public int countPyramidRowsFromNumberOfPlayers(int playerCount) {
        int rows = 0;
        int currentPlayersThatCanPlay = 0;
        do {
            rows++;
            currentPlayersThatCanPlay += rows;
        } while (currentPlayersThatCanPlay < playerCount);

        return rows;
    }

}
