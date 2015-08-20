package org.jenkinsci.plugins.jenkinscutestparser;

import java.util.List;

/**
 * Created by 31525132 on 19/08/2015.
 */
public class PlanckOutputRoot {
    private List<PlanckOutput> results;
    private int total_tests;
    private int total_passed;

    public boolean allPassed() {
        return total_passed == total_tests;
    }

    public String getSummary() {
        String ret = "";
        for(PlanckOutput po: results) {
            ret = ret.concat(po.passed() ? ". " : "F ");
        }

        return ret;
    }

    public List<PlanckOutput> getResults() {
        return results;
    }

    public int getTotalTests() {
        return total_tests;
    }

    public int getTotalPassed() {
        return total_passed;
    }
}
