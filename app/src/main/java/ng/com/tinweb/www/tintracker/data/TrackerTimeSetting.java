package ng.com.tinweb.www.tintracker.data;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import ng.com.tinweb.www.tintracker.R;
import ng.com.tinweb.www.tintracker.appConfig.ContextProvider;

/**
 * Created by kamiye on 11/3/15.
 */
public class TrackerTimeSetting {

    private static final int DEFAULT_TIME = 5;

    private Context context = ContextProvider.getContext();
    private SharedPreferences sharedPref;

    public TrackerTimeSetting() {
        createSharedPreference();
    }

    private void createSharedPreference() {
        sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public void saveTimeSetting(int time) {
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt(context.getString(R.string.saved_tracking_time), time);
        editor.apply();
    }

    public int getTimeSetting() {
        return sharedPref.getInt(context.getString(R.string.saved_tracking_time), DEFAULT_TIME);
    }

}
