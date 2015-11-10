package ng.com.tinweb.www.tintracker;

import android.graphics.Rect;
import android.support.v4.app.Fragment;
import android.support.v7.internal.view.menu.ActionMenuItemView;
import android.test.ActivityInstrumentationTestCase2;
import android.test.TouchUtils;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.List;

import ng.com.tinweb.www.tintracker.activities.WelcomeActivity;
import ng.com.tinweb.www.tintracker.fragment.InfoFragment;
import ng.com.tinweb.www.tintracker.fragment.LocationHistoryFragment;
import pl.droidsonroids.gif.GifImageView;

/**
 * Created by kamiye on 11/9/15.
 */
public class WelcomeActivityTest extends ActivityInstrumentationTestCase2<WelcomeActivity> {

    private WelcomeActivity activity;

    private LinearLayout timeSetting;
    private SeekBar settingsSeekBar;
    private GifImageView walkingMan;
    private Button actionButton;
    private SeekBar timeBar;
    private TextView progressText;
    private TextView timeLimit;

    // Options menu
    private ActionMenuItemView infoItem;
    private ActionMenuItemView historyItem;
    private ActionMenuItemView settingsItem;

    public WelcomeActivityTest() {
        super(WelcomeActivity.class);
    }

    public void setUp() throws Exception {
        activity = getActivity();
        setupTestingViews();
    }

    private void setupTestingViews() {
        timeSetting = (LinearLayout) activity.findViewById(R.id.time_setting);
        settingsSeekBar = (SeekBar) activity.findViewById(R.id.time_settings_bar);
        walkingMan = (GifImageView) activity.findViewById(R.id.gif_image);
        actionButton = (Button) activity.findViewById(R.id.action_button);
        timeBar = (SeekBar) activity.findViewById(R.id.timebar);
        progressText = (TextView) activity.findViewById(R.id.progress_text);
        timeLimit = (TextView) activity.findViewById(R.id.time_limit);

        // menu item
        infoItem = (ActionMenuItemView) activity.findViewById(R.id.action_info);
        historyItem = (ActionMenuItemView) activity.findViewById(R.id.action_history);
        settingsItem = (ActionMenuItemView) activity.findViewById(R.id.action_settings);
    }

    private void touchAction(View view) {
        TouchUtils.clickView(this, view);
        getInstrumentation().waitForIdleSync();

    }

    public void testPreconditions_layoutVisibility() {
        int visibility;
        visibility = timeSetting.getVisibility();
        assertEquals("Setting Visibility Error", visibility, View.GONE);

        visibility = walkingMan.getVisibility();
        assertEquals("Walking Man Visibility Error", visibility, View.GONE);

    }

    public void testSettings_visibilityOnIconClick() {
        touchAction(settingsItem);
        int visibility = timeSetting.getVisibility();
        assertEquals("Setting Visibility Error", visibility, View.VISIBLE);
    }

    public void testSettings_SettingsBarMoved() {
        touchAction(settingsItem);
        int progress1 = settingsSeekBar.getProgress();
        Rect rect = new Rect();

        settingsSeekBar.setProgress(6);

        int progress2 = settingsSeekBar.getProgress();

        assertNotSame("Seekbar setting error", progress1, progress2);
    }

    public void testHistory_FragmentAdded() {
        int count = 0;
        touchAction(historyItem);
        List<Fragment> fragments = activity.getSupportFragmentManager().getFragments();
        for(Fragment frag: fragments) {
            if (frag instanceof LocationHistoryFragment && frag.isAdded()) {
                count++;
            }
        }
        assertTrue(count == 1);
    }

    public void testHistory_FragmentDetached() {
        int count = 0;
        touchAction(historyItem);
        touchAction(historyItem);
        List<Fragment> fragments = activity.getSupportFragmentManager().getFragments();
        for(Fragment frag: fragments) {
            if (frag instanceof LocationHistoryFragment && frag.isAdded()) {
                count++;
            }
        }
        assertTrue(count == 0);
    }

    public void testInfo_FragmentAdded() {
        int count = 0;
        touchAction(infoItem);
        List<Fragment> fragments = activity.getSupportFragmentManager().getFragments();
        for(Fragment frag: fragments) {
            if (frag instanceof InfoFragment && frag.isAdded()) {
                count++;
            }
        }
        assertTrue(count == 1);
    }

    public void testInfo_FragmentDetached() {
        int count = 0;
        touchAction(infoItem);
        touchAction(infoItem);
        List<Fragment> fragments = activity.getSupportFragmentManager().getFragments();
        for(Fragment frag: fragments) {
            if (frag instanceof InfoFragment && frag.isAdded()) {
                count++;
            }
        }
        assertTrue(count == 0);
    }

    public void testActionButton_layoutChange() {
        RelativeLayout.LayoutParams params1 = (RelativeLayout.LayoutParams) actionButton.getLayoutParams();
        int margin1 = params1.topMargin;
        touchAction(actionButton);
        RelativeLayout.LayoutParams params2 = (RelativeLayout.LayoutParams) actionButton.getLayoutParams();
        int margin2 = params2.topMargin;
        assertNotSame("Layout Params error", margin1, margin2);
    }

    public void testActionButton_textChange() {
        String text1 = actionButton.getText().toString();
        touchAction(actionButton);
        String text2 = actionButton.getText().toString();
        assertFalse(text1.equals(text2));
    }

    public void testGifImage_visibilityAfterButtonClick() {
        int visibility1 = walkingMan.getVisibility();
        touchAction(actionButton);
        int visibility2 = walkingMan.getVisibility();
        assertFalse(visibility1 == visibility2);
    }


}
