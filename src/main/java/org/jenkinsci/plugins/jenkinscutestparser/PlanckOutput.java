package org.jenkinsci.plugins.jenkinscutestparser;

/**
 * Created by 31525132 on 19/08/2015.
 */
public class PlanckOutput {
    private int error_at_line;
    private String file;
    private String function;
    private String message;

    public boolean passed() {
        return error_at_line == -1;
    }

    public String status() {
        return passed() ? "Pass" : "Fail";
    }

    public int getLine() {
        return error_at_line;
    }

    public String getFile() {
        return file;
    }

    public String getFunction() {
        return function;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return String.format("in function '%s', at %s:%d: %s", function, file, error_at_line, message);
    }
}