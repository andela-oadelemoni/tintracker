package ng.com.tinweb.www.tintracker;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.TextView;

import ng.com.tinweb.www.tintracker.activities.WelcomeActivity;
import ng.com.tinweb.www.tintracker.fragment.InfoFragment;

/**
 * Created by kamiye on 11/10/15.
 */
public class InfoFragmentTest extends ActivityInstrumentationTestCase2<WelcomeActivity> {

    private InfoFragment infoFragment = new InfoFragment();
    private WelcomeActivity welcomeActivity;

    public InfoFragmentTest() {
        super(WelcomeActivity.class);
    }

    public void setUp() throws Exception {
        super.setUp();

        welcomeActivity = getActivity();

        welcomeActivity.getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.info_fragment_container, infoFragment)
                .commit();
    }

    public void testPreconditions() {
        assertNotNull(welcomeActivity);
        assertNotNull(infoFragment);
    }

    public void testFragment_added() {
        this.getInstrumentation().waitForIdleSync();
        assertTrue(infoFragment.isAdded());
    }

    public void testInfoFragment_containingElements() {

        this.getInstrumentation().waitForIdleSync();

        TextView heading = (TextView) welcomeActivity.findViewById(R.id.info_heading);
        TextView content = (TextView) welcomeActivity.findViewById(R.id.info_text);

        assertNotNull(heading);
        assertNotNull(content);

        String headingText = heading.getText().toString();
        assertTrue("Info Fragment Heading Error",
                headingText.equals(welcomeActivity.getResources()
                        .getString(R.string.info_title)));
    }
}
