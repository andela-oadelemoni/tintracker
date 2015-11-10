package ng.com.tinweb.www.tintracker.data;

import android.app.IntentService;
import android.content.Intent;

import com.google.android.gms.location.ActivityRecognitionResult;
import com.google.android.gms.location.DetectedActivity;

import ng.com.tinweb.www.tintracker.R;

/**
 * Created by kamiye on 11/4/15.
 */
public class ActivityRecognitionService extends IntentService {
    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */

    private static final String TAG = "ActivityRecognition";

    public ActivityRecognitionService() {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if(ActivityRecognitionResult.hasResult(intent)) {
            //Extract the result from the Response
            ActivityRecognitionResult result = ActivityRecognitionResult.extractResult(intent);
            DetectedActivity detectedActivity = result.getMostProbableActivity();

            //Get the Confidence and Name of Activity
            int confidence = detectedActivity.getConfidence();
            String mostProbableName = getActivityName(detectedActivity.getType());

            //Fire the intent with activity name & confidence
            Intent broadCastIntent = new Intent(getString(R.string.activity_recognition_intent_filter));
            broadCastIntent.putExtra(getString(R.string.activity_recognition_activity_name), mostProbableName);
            broadCastIntent.putExtra(getString(R.string.activity_recognition_confidence_level), confidence);

            //Send Broadcast to be listen in MainActivity
            this.sendBroadcast(broadCastIntent);

        }
    }

    //Get the activity name
    private String getActivityName(int type) {
        switch (type)
        {
            case DetectedActivity.IN_VEHICLE:
            case DetectedActivity.ON_BICYCLE:
            case DetectedActivity.ON_FOOT:
            case DetectedActivity.WALKING:
            case DetectedActivity.RUNNING:
                return getString(R.string.movement_notification);
            case DetectedActivity.STILL:
            case DetectedActivity.TILTING:
            case DetectedActivity.UNKNOWN:
                return getString(R.string.still_notification);
        }
        return getString(R.string.n_a);
    }
}
