package org.jenkinsci.plugins.jenkinscutestparser;

import com.google.gson.Gson;
import hudson.Extension;
import hudson.Launcher;
import hudson.model.AbstractBuild;
import hudson.model.AbstractProject;
import hudson.model.BuildListener;
import hudson.model.Result;
import hudson.tasks.BuildStepDescriptor;
import hudson.tasks.BuildStepMonitor;
import hudson.tasks.Publisher;
import hudson.tasks.Recorder;
import net.sf.json.JSONObject;
import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.StaplerRequest;

import java.io.FileNotFoundException;
import java.io.FileReader;

public class JenkinsCutestParser extends Recorder {
    private String targFile;

    // Fields in config.jelly must match the parameter names in the "DataBoundConstructor"
    @DataBoundConstructor
    public JenkinsCutestParser(String targFile) {
        this.targFile = targFile;
    }

    public String getTargFile() {
        return targFile;
    }

    @Override
    public boolean perform(AbstractBuild build, Launcher launcher, BuildListener listener) {
        try {
            PlanckOutputRoot testresult = new Gson().fromJson(new FileReader(build.getWorkspace() + "/" + targFile), PlanckOutputRoot.class);

            for(PlanckOutput testcase: testresult.getResults()) {
                if(testcase.passed()) { //Test passed, skip it
                    continue;
                }

                listener.getLogger().println(testcase);
                build.setResult(Result.FAILURE);
            }

            listener.getLogger().println(testresult.getSummary());
            build.addAction(new JenkinsCutestDisplay(testresult, false)); //TODO capture finish flag
        }
        catch(FileNotFoundException e) {
            listener.getLogger().printf("Couldn't find file %s! Was Planck called correctly before this step?\n", targFile);
            build.setResult(Result.FAILURE);
        }

        return true;
    }

    // Overridden for better type safety.
    // If your plugin doesn't really define any property on Descriptor,
    // you don't have to do this.
    @Override
    public DescriptorImpl getDescriptor() {
        return (DescriptorImpl)super.getDescriptor();
    }

    @Override
    public BuildStepMonitor getRequiredMonitorService() {
        return BuildStepMonitor.NONE;
    }

    @Extension // This indicates to Jenkins that this is an implementation of an extension point.
    public static final class DescriptorImpl extends BuildStepDescriptor<Publisher> {

        /**
         * In order to load the persisted global configuration, you have to 
         * call load() in the constructor.
         */
        public DescriptorImpl() {
            load();
        }

        @Override
        public boolean isApplicable(Class<? extends AbstractProject> jobType) {
            return true;
        }

        @Override
        public String getDisplayName() {
            return "Run and report Cutest Parsing";
        }

        @Override
        public boolean configure(StaplerRequest req, JSONObject formData) throws FormException {
            // To persist global configuration information,
            // set that to properties and call save().
            // ^Can also use req.bindJSON(this, formData);
            //  (easier when there are many fields; need set* methods for this, like setUseFrench)
            save();
            return super.configure(req,formData);
        }
    }
}

