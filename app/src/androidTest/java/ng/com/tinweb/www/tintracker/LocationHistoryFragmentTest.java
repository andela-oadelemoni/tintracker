package ng.com.tinweb.www.tintracker;

import android.support.v4.app.Fragment;
import android.test.ActivityInstrumentationTestCase2;

import java.util.List;

import ng.com.tinweb.www.tintracker.activities.WelcomeActivity;
import ng.com.tinweb.www.tintracker.fragment.LocationHistoryFragment;

/**
 * Created by kamiye on 11/10/15.
 */
public class LocationHistoryFragmentTest extends ActivityInstrumentationTestCase2<WelcomeActivity> {

    private LocationHistoryFragment historyFragment = new LocationHistoryFragment();
    private WelcomeActivity welcomeActivity;

    public LocationHistoryFragmentTest() {
        super(WelcomeActivity.class);
    }

    public void setUp() throws Exception {
        super.setUp();

        welcomeActivity = getActivity();

        welcomeActivity.getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.history_fragment_container, historyFragment)
                .commit();
    }

    public void testPreconditions() {
        assertNotNull(welcomeActivity);
        assertNotNull(historyFragment);
    }

    public void testFragment_added() {
        this.getInstrumentation().waitForIdleSync();
        assertTrue(historyFragment.isAdded());
    }

    public void testLocationHistory_containingFragments() {

        List<Fragment> fragmentList = historyFragment.getFragmentManager().getFragments();
        assertTrue(fragmentList.size() == 2);
    }
}
