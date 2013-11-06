package eu.speedbadminton.pyramid.utils;

import org.apache.commons.lang.StringUtils;

/**
 * User: Yoann Moranville
 * Date: 05/11/2013
 *
 * @author Yoann Moranville
 */
public class Result {
    private Set set1;
    private Set set2;
    private Set set3;

    public Result(Set set1, Set set2, Set set3) {
        this.set1 = set1;
        this.set2 = set2;
        this.set3 = set3;
    }

    public Result(String set11, String set12, String set21, String set22, String set31, String set32) {
        this.set1 = new Set(Integer.parseInt(set11), Integer.parseInt(set12));
        this.set2 = new Set(Integer.parseInt(set21), Integer.parseInt(set22));
        if(StringUtils.isNotEmpty(set31) && StringUtils.isNotEmpty(set32)) {
            this.set3 = new Set(Integer.parseInt(set31), Integer.parseInt(set32));
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
}
