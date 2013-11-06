package eu.speedbadminton.pyramid.utils;

import org.junit.Assert;
import org.junit.Test;

/**
 * User: Yoann Moranville
 * Date: 06/11/2013
 *
 * @author Yoann Moranville
 */
public class ResultsUtilTest {

    @Test
    public void isResultCorrectTest() {
        Result result = new Result("2", "16", "20", "18", "7", "16");
        Assert.assertTrue(ResultsUtil.isResultCorrect(result));
        result = new Result("2", "16", "18", "20", null, null);
        Assert.assertTrue(ResultsUtil.isResultCorrect(result));
        result = new Result("2", "16", "21", "18", "7", "16");
        Assert.assertFalse(ResultsUtil.isResultCorrect(result));
    }
}
