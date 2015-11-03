package ng.com.tinweb.www.tintracker.sensor;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import ng.com.tinweb.www.tintracker.activities.WelcomeActivity;
import ng.com.tinweb.www.tintracker.appConfig.ContextProvider;

/**
 * Created by kamiye on 11/1/15.
 */
public class TinTrackerSensor implements SensorEventListener {

    private Context context = ContextProvider.getContext();
    private SensorManager sensorManager;
    private WelcomeActivity activity;

    public TinTrackerSensor(WelcomeActivity activity) {
        this.activity = activity;
    }

    public Sensor getSensor() {
        sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);

        Sensor sensor = sensorManager.getDefaultSensor(Sensor.TYPE_SIGNIFICANT_MOTION);
        sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);
        return sensor;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        activity.sensorIsh(event);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    public void sensorEventGetter(SensorChangeCallback callback) {
        callback.onChange();
    }

    public interface SensorChangeCallback {
        void onChange();
    }
}
