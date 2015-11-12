package ng.com.tinweb.www.tintracker.data;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import ng.com.tinweb.www.tintracker.R;
import ng.com.tinweb.www.tintracker.appConfig.ContextProvider;
import ng.com.tinweb.www.tintracker.helpers.TinTrackerActivityRecognition;

/**
 * Created by kamiye on 11/12/15.
 */
public class ActivityRecognitionReceiver {

    private BroadcastReceiver receiver;
    private Context context = ContextProvider.getContext();

    public void setupActivityRecognition(final ActivityRecognitionCallback callback) {
        //Broadcast receiver
        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String activity = intent.getStringExtra(context.getString(R.string.activity_recognition_activity_name));
                int confidence = intent.getExtras().getInt(context.getString(R.string.activity_recognition_confidence_level));

                setActivityRecognitionAction(activity, confidence, callback);
            }
        };
        //Filter the Intent and register broadcast receiver
        IntentFilter filter = new IntentFilter();
        filter.addAction(context.getString(R.string.activity_recognition_intent_filter));
        context.registerReceiver(receiver, filter);

        new TinTrackerActivityRecognition();
    }

    private void setActivityRecognitionAction(String activity, int confidence, ActivityRecognitionCallback callback) {
        if (confidence > 50) {
            if (activity.equals(context.getString(R.string.still_notification))) {
                callback.onStandStillDetected();
            } else {
                callback.onMovementDetected();
            }
        }
    }

    public void disAbleActivityRecognition(ActivityRecognitionDisabledCallback callback) {
        //Filter the Intent and register broadcast receiver
        context.unregisterReceiver(receiver);
        callback.onActivityRecognitionDisabled();
    }

    public interface ActivityRecognitionCallback {
        void onStandStillDetected();
        void onMovementDetected();
    }

    public interface ActivityRecognitionDisabledCallback {
        void onActivityRecognitionDisabled();
    }
}
