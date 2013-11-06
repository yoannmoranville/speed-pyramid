package eu.speedbadminton.pyramid.service;

import junit.framework.Assert;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * User: Yoann Moranville
 * Date: 26/06/2013
 *
 * @author Yoann Moranville
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:META-INF/context-base.xml"})
public class PyramidServiceTest {
    private static final Logger LOG = Logger.getLogger(PyramidServiceTest.class);

    @Autowired
    private PyramidService pyramidService;

    @Test
    public void countPyramidRowsFromNumberOfPlayersTest() {
        Assert.assertEquals(pyramidService.countPyramidRowsFromNumberOfPlayers(2), 2);
        Assert.assertEquals(pyramidService.countPyramidRowsFromNumberOfPlayers(5), 3);
        Assert.assertEquals(pyramidService.countPyramidRowsFromNumberOfPlayers(11), 5);
        Assert.assertEquals(pyramidService.countPyramidRowsFromNumberOfPlayers(12), 5);
        Assert.assertEquals(pyramidService.countPyramidRowsFromNumberOfPlayers(15), 5);
        Assert.assertEquals(pyramidService.countPyramidRowsFromNumberOfPlayers(16), 6);
    }

}
