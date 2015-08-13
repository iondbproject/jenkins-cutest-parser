package org.jenkinsci.plugins.jenkinscutestparser;

import hudson.Extension;
import hudson.views.ViewsTabBar;
import hudson.views.ViewsTabBarDescriptor;
import net.sf.json.JSONObject;
import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.StaplerRequest;

/**
 * Created by 31525132 on 13/08/2015.
 */

public class CustomViewsTabBar extends ViewsTabBar {
    @DataBoundConstructor
    public CustomViewsTabBar() {
        super();
    }

    @Extension
    public static final class CustomViewsTabBarDescriptor extends ViewsTabBarDescriptor {
        public CustomViewsTabBarDescriptor() {
            load();
        }

        @Override
        public String getDisplayName() {
            return "I am a custom view tab bar! Whatever that is.";
        }

        @Override
        public boolean configure(StaplerRequest req, JSONObject formData) throws FormException {
            save();
            return false;
        }
    }
}


