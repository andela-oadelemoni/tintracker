package ng.com.tinweb.www.tintracker;

import android.test.InstrumentationTestCase;

import ng.com.tinweb.www.tintracker.data.TrackerTimeSetting;

/**
 * Created by kamiye on 11/10/15.
 */
public class TrackerTimeSettingTest extends InstrumentationTestCase {

    public TrackerTimeSettingTest() {
        super();
    }

    public void testTrackerTimeSetting() {
        TrackerTimeSetting timeSetting = new TrackerTimeSetting();
        int initialSetting = timeSetting.getTimeSetting();

        timeSetting.saveTimeSetting(2);
        int initialTime1 = timeSetting.getTimeSetting();
        assertEquals("Time setting error", 2, initialTime1);

        timeSetting.saveTimeSetting(5);
        int initialTime2 = timeSetting.getTimeSetting();
        assertEquals("Time setting error", 5, initialTime2);

        timeSetting.saveTimeSetting(initialSetting);
    }
}
