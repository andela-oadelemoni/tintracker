package ng.com.tinweb.www.tintracker.activities;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

import ng.com.tinweb.www.tintracker.R;
import ng.com.tinweb.www.tintracker.animation.AppViewAnimation;
import ng.com.tinweb.www.tintracker.data.ActivityRecognitionReceiver;
import ng.com.tinweb.www.tintracker.data.TrackerTimeSetting;
import ng.com.tinweb.www.tintracker.database.LocationData;
import ng.com.tinweb.www.tintracker.fragment.LocationHistoryFragment;
import ng.com.tinweb.www.tintracker.helpers.AppAlertDialog;
import ng.com.tinweb.www.tintracker.helpers.LocationHelper;
import ng.com.tinweb.www.tintracker.helpers.SeekBarHandler;
import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifImageView;

public class WelcomeActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int MY_PERMISSIONS_REQUEST_CODE = 1;
    // Layout container
    private RelativeLayout container;
    private LinearLayout timeSettingLayout;
    private ActivityRecognitionReceiver activityReceiver;

    // Button
    private boolean buttonUp = false;
    private Button action_button;
    private RelativeLayout.LayoutParams actionButtonParams;

    //  Tracker Settings
    private SeekBar seekBarTimeSetting;
    private SeekBar timeBar;
    private int timeSetting;
    private int seekbarSteps;
    private TrackerTimeSetting trackerTimeSetting;
    private TextView timeLimit;
    private TextView progressView;

    // Gif Image
    private GifDrawable gifFromResource;
    private GifImageView gifImage;

    private SeekBarHandler seekBarHandler;
    private boolean timerStarted = false;

    // fragments
    private LocationHistoryFragment historyFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        trackerTimeSetting = new TrackerTimeSetting();

        setupViewProperties();
        setupGifImage();
        setupSettingBar();
        setUpTrackingTime();

        seekBarHandler = new SeekBarHandler(timeBar, seekbarSteps);
        setupFragment();
        activityReceiver = new ActivityRecognitionReceiver();
    }

    private void setupViewProperties() {

        // View Group
        container = (RelativeLayout) findViewById(R.id.container);
        timeSettingLayout = (LinearLayout) findViewById(R.id.time_setting);
        gifImage = (GifImageView) findViewById(R.id.gif_image);

        // TextView
        timeLimit = (TextView) findViewById(R.id.time_limit);
        progressView = (TextView) findViewById(R.id.progress_text);

        // SeekBar
        seekBarTimeSetting = (SeekBar) findViewById(R.id.time_settings_bar);
        timeBar = (SeekBar) findViewById(R.id.timebar);
        timeBar.setEnabled(false);

        // Button
        action_button = (Button) findViewById(R.id.action_button);
        actionButtonParams = (RelativeLayout.LayoutParams) action_button.getLayoutParams();
        action_button.setOnClickListener(this);

        // Clear screen buttons
        TextView okText = (TextView) findViewById(R.id.ok);
        okText.setOnClickListener(this);
    }

    private void setupGifImage() {
        try {
            gifFromResource = new GifDrawable(getResources(), R.drawable.walking);
            gifImage.setImageDrawable(gifFromResource);
            gifFromResource.stop();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setupSettingBar() {
        seekBarTimeSetting.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                timeSetting = progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                if (timeSetting < 10) timeSetting = timeSetting + 1;
                updateTimeSetting(timeSetting);
            }
        });
    }

    private void setUpTrackingTime() {
        int timeSetting = trackerTimeSetting.getTimeSetting();
        seekbarSteps = timeSetting * 60;
        seekBarTimeSetting.setProgress(timeSetting - 1);
        timeBar.setMax(seekbarSteps);
        timeBar.setProgress(0);
        String display = timeSetting + ":00";
        timeLimit.setText(display);
        if (seekBarHandler != null) seekBarHandler.setSteps(seekbarSteps);
    }

    private void setupFragment() {
        historyFragment = new LocationHistoryFragment();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.history_fragment_container, historyFragment)
                .detach(historyFragment)
                .commit();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        switch (id) {
            case R.id.action_button:
                trackingButtonAction();
                break;
            case R.id.ok:
                clearLayoutScreen();
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_welcome, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        switch (id) {
            case R.id.action_settings:
                toggleTimeSetting();
                break;
            case R.id.action_history:
                toggleHistory();
                break;
            case R.id.action_info:
                AppAlertDialog alertDialog = new AppAlertDialog(this);
                alertDialog.showApplicationInfo();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void toggleTimeSetting() {
        if (buttonUp) {
            AppAlertDialog alertDialog = new AppAlertDialog(this);
            alertDialog.settingsError();
        }
        else {
            clearLayoutFragment();
            AppViewAnimation.toggleViewAnimation(timeSettingLayout);
        }
    }

    private void clearLayoutScreen() {
        clearLayoutFragment();
        AppViewAnimation.fadeOut(timeSettingLayout);
    }

    private void clearLayoutFragment() {
        getSupportFragmentManager().beginTransaction()
                .detach(historyFragment)
                .commit();
    }

    private void setupLocationHelper() {
        LocationHelper locationHelper = new LocationHelper();
        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
        if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
            locationHelper.getLocation(new LocationHelper.LocationHelperCallback() {
                @Override
                public void onSuccess(Location location) {
                    new LocationData(location);
                }
            });
        } else {
            Toast.makeText(this, R.string.location_permission_denial_message, Toast.LENGTH_LONG).show();
            requestLocationPermission();
        }
    }

    private void requestLocationPermission() {

        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.ACCESS_FINE_LOCATION)) {
            Toast.makeText(this, R.string.permission_request_explanation, Toast.LENGTH_LONG).show();
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    MY_PERMISSIONS_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_CODE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    setupLocationHelper();
                } else {
                    Toast.makeText(this, R.string.location_request_error, Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    private void toggleHistory() {
        toggleFragment(historyFragment);
    }

    private void toggleFragment(Fragment fragment) {
        if (fragment.isAdded()) {
            getSupportFragmentManager().beginTransaction()
                    .detach(fragment).commit();
        } else {
            getSupportFragmentManager().beginTransaction()
                    .attach(fragment).commit();
        }
        AppViewAnimation.fadeOut(timeSettingLayout);
    }

    private void updateTimeSetting(int time) {
        trackerTimeSetting.saveTimeSetting(time);
        Toast.makeText(WelcomeActivity.this, "Tracking time changed to: " + timeSetting + " minute(s)", Toast.LENGTH_LONG).show();
        setUpTrackingTime();
    }

    private void trackingButtonAction() {
        if (buttonUp) {
            actionButtonParams.addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE);
            action_button.setText(R.string.start_tracking);
            gifFromResource.stop();
            disAbleActivityRecognition();

        } else {
            actionButtonParams.addRule(RelativeLayout.CENTER_VERTICAL, 0);
            actionButtonParams.setMargins(0, 50, 0, 0);
            action_button.setText(R.string.stop_tracking);
            setupActivityRecognition();
        }

        buttonUp = !buttonUp;
        AppViewAnimation.toggleViewAnimation(gifImage);
        AppViewAnimation.fadeOut(timeSettingLayout);
        AppViewAnimation.setViewTransition(actionButtonParams, container, action_button);
    }

    private void setupActivityRecognition() {
        activityReceiver.setupActivityRecognition(new ActivityRecognitionReceiver.ActivityRecognitionCallback() {
            @Override
            public void onStandStillDetected() {
                gifFromResource.stop();
                if (!timerStarted) startStandStillTimer();
            }

            @Override
            public void onMovementDetected() {
                gifFromResource.start();
                resetStandStillTimer();
            }
        });
    }

    private void disAbleActivityRecognition() {
        activityReceiver.disAbleActivityRecognition(new ActivityRecognitionReceiver.ActivityRecognitionDisabledCallback() {
            @Override
            public void onActivityRecognitionDisabled() {
                resetStandStillTimer();
            }
        });
    }

    private void startStandStillTimer() {
        timerStarted = true;
        seekBarHandler.setTimer(new SeekBarHandler.SeekBarHandlerCallBack() {
            @Override
            public void onCountDownFinish() {
                minimumTimeAction();
            }

            @Override
            public void seekBarTick(int progress) {
                setProgressLabel(progress);
            }
        });
        seekBarHandler.startTimer();
    }

    private void minimumTimeAction() {
        progressView.setText(getString(R.string.tracking_start_time));
        Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        vibrator.vibrate(500);
        setupLocationHelper();
    }

    private void resetStandStillTimer() {
        progressView.setText(getString(R.string.tracking_start_time));
        timerStarted = false;
        seekBarHandler.resetTimer();
    }

    private void setProgressLabel(int progress) {
        int mins = progress / 60;
        int secs = progress % 60;

        String progressDisplay = (secs < 10) ? mins + ":0" + secs : mins + ":" + secs;
        progressView.setText(progressDisplay);
    }

}
