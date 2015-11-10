package ng.com.tinweb.www.tintracker;

import android.app.Instrumentation;
import android.test.ActivityInstrumentationTestCase2;

import ng.com.tinweb.www.tintracker.activities.LauncherActivity;
import ng.com.tinweb.www.tintracker.activities.WelcomeActivity;
import pl.droidsonroids.gif.GifImageView;

/**
 * Created by kamiye on 11/9/15.
 */
public class LauncherActivityTest extends ActivityInstrumentationTestCase2<LauncherActivity> {

    public LauncherActivityTest() {
        super(LauncherActivity.class);
    }

    public void testPreconditions() {
        LauncherActivity activity = getActivity();
        GifImageView loading_image = (GifImageView) activity.findViewById(R.id.app_loading);
        assertNotNull(activity);
        assertNotNull(loading_image);
    }

    public void testActivityChange() {
        Instrumentation.ActivityMonitor nextActivity = getInstrumentation().addMonitor(WelcomeActivity.class.getName(), null, false);
        getInstrumentation().waitForMonitorWithTimeout(nextActivity, 5000);

        assertNotNull(nextActivity);
    }

}
