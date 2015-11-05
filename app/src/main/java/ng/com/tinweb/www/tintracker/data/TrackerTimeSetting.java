package ng.com.tinweb.www.tintracker.data;

import android.app.Activity;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import ng.com.tinweb.www.tintracker.R;

/**
 * Created by kamiye on 11/3/15.
 */
public class TrackerTimeSetting {

    private static final int DEFAULT_TIME = 5;

    private SharedPreferences sharedPref;
    private Activity activity;

    public TrackerTimeSetting(Activity activity) {
        this.activity = activity;
        createSharedPreference();
    }

    private void createSharedPreference() {
        sharedPref = PreferenceManager.getDefaultSharedPreferences(activity);
    }

    public void saveTimeSetting(int time) {
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt(activity.getString(R.string.saved_tracking_time), time);
        editor.apply();
    }

    public int getTimeSetting() {
        return sharedPref.getInt(activity.getString(R.string.saved_tracking_time), DEFAULT_TIME);
    }

}
