package org.jenkinsci.plugins.jenkinscutestparser;

import hudson.model.Action;

import java.util.List;

/**
 * Created by 31525132 on 19/08/2015.
 */
public class JenkinsCutestDisplay implements Action {
    private boolean testsfinished;
    private PlanckOutputRoot testresult;

    public JenkinsCutestDisplay(PlanckOutputRoot testresult, boolean testsfinished) {
        this.testresult = testresult;
        this.testsfinished = testsfinished;
    }

    public boolean allPassed() {
        return testresult.allPassed();
    }

    public int getTotalPassed() {
        return testresult.getTotalPassed();
    }

    public int getTotalTests() {
        return testresult.getTotalTests();
    }

    public String getSummary() {
        return testresult.getSummary();
    }

    public List<PlanckOutput> getResults() {
        return testresult.getResults();
    }

    public boolean getTestsFinished() {
        return testsfinished;
    }

    @Override
    public String getIconFileName() {
        return "/images/32x32/star-gold.png";
    }

    @Override
    public String getDisplayName() {
        return "Planck Unit";
    }

    @Override
    public String getUrlName() {
        return "planck";
    }
}
