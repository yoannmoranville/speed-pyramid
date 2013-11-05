package eu.speedbadminton.pyramid.utils;

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
