package eu.speedbadminton.pyramid.controller.ajax;

import org.junit.Assert;
import org.junit.Test;

import java.util.Calendar;
import java.util.Date;

/**
 * User: lukasgotter
 * Date: 02.12.13
 */
public class ResultsAjaxControllerTest {

    /**
     * A correct date is same day as creation day or greater, but before current date
     */
    @Test
    public void isDateCorrectTest(){
        Calendar creationDate = Calendar.getInstance();
        creationDate.set(2013, Calendar.JANUARY, 15, 12, 56);

        Calendar today = Calendar.getInstance();

        Calendar matchDate = Calendar.getInstance();
        //matchDate.add(Calendar.DAY_OF_WEEK,-1); // yesterday
        //Date match = new Date(Date.parse("2013-11-30T12:08:40.889Z")); // thats how it comes from DB
        //Date creation = new Date(Date.parse("2013-11-29T23:00:00Z"));
        //Assert.assertTrue("parsed from DB workd",ResultsAjaxController.isDateCorrect(creation,match));

        // matchdate before creation
        matchDate.set(2013,Calendar.JANUARY,14, 16, 8);
        Assert.assertFalse("matchdate before creation",ResultsAjaxController.isDateCorrect(creationDate.getTime(), matchDate.getTime()));

        // matchDate same day as creation with different time should be ok
        matchDate.set(2013, Calendar.JANUARY, 15, 6, 56);
        Assert.assertTrue("matchdate same day as creation",ResultsAjaxController.isDateCorrect(creationDate.getTime(), matchDate.getTime()));

        // matchDate something in between January 15, 2013 and today
        matchDate.add(Calendar.DAY_OF_MONTH,50);
        Assert.assertTrue("matchdate between creation and today",ResultsAjaxController.isDateCorrect(creationDate.getTime(), matchDate.getTime()));

        // matchDate today
        matchDate.setTime(new Date());
        Assert.assertTrue("matchdate today",ResultsAjaxController.isDateCorrect(creationDate.getTime(), matchDate.getTime()));

        // matchDate tomorrow (should fail)
        matchDate.add(Calendar.DAY_OF_WEEK,1);
        printDates(creationDate,matchDate);
        Assert.assertFalse("matchdate tomorrow",ResultsAjaxController.isDateCorrect(creationDate.getTime(), matchDate.getTime()));

    }

    private static void printDates(Calendar created, Calendar match){
        System.out.println("created:"+created.getTime()+" - match:"+match.getTime());
    }
}
