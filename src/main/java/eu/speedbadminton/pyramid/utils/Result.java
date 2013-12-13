package eu.speedbadminton.pyramid.utils;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

/**
 * User: Yoann Moranville
 * Date: 05/11/2013
 *
 * @author Yoann Moranville
 */
public class Result {

    private static final Logger LOG = Logger.getLogger(Result.class);

    private Set set1;
    private Set set2;
    private Set set3;

    public Result(Set set1, Set set2, Set set3) {
        this.set1 = set1;
        this.set2 = set2;
        this.set3 = set3;
    }

    public Result(Integer set11, Integer set12, Integer set21, Integer set22, Integer set31, Integer set32) {
        this.set1 = new Set(set11,set12);
        this.set2 = new Set(set21,set22);
        if(set31!=null && set32!=null) {
            this.set3 = new Set(set31,set32);
        } else {
            this.set3 = null;
        }
    }

    public Set getSet1() {
        return set1;
    }

    public void setSet1(Set set1) {
        this.set1 = set1;
    }

    public Set getSet2() {
        return set2;
    }

    public void setSet2(Set set2) {
        this.set2 = set2;
    }

    public Set getSet3() {
        return set3;
    }

    public void setSet3(Set set3) {
        this.set3 = set3;
    }

    public boolean isResultCorrect() {
        final int SET_POINT = 16;
        final int MINIMUM_DIFF = 2;
        int winningSetForChallenger = 0;
        int winningSetForChallengee = 0;
        Set set;

        for(int i = 1; i <= 3; i++) {
            if(i == 1) {
                set = this.getSet1();
            } else if(i == 2) {
                set = this.getSet2();
            } else {
                set = this.getSet3();
                if(set == null) {
                    break;
                } else if (winningSetForChallenger == 2 || winningSetForChallengee == 2) {
                    return false;
                }
            }

            if(set.getPointOfChallengee() == SET_POINT && set.getPointOfChallenger() <= (SET_POINT - MINIMUM_DIFF)) {
                winningSetForChallengee++;
            } else if(set.getPointOfChallenger() == SET_POINT && set.getPointOfChallengee() <= (SET_POINT - MINIMUM_DIFF)) {
                winningSetForChallenger++;
            } else if(set.getPointOfChallengee() > SET_POINT && (set.getPointOfChallengee() - set.getPointOfChallenger() == MINIMUM_DIFF)) {
                winningSetForChallengee++;
            } else if(set.getPointOfChallenger() > SET_POINT && (set.getPointOfChallenger() - set.getPointOfChallengee() == MINIMUM_DIFF)) {
                winningSetForChallenger++;
            } else {
                return false;
            }
        }
        return winningSetForChallenger == 2 || winningSetForChallengee == 2;
    }


}
