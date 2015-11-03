package ng.com.tinweb.www.tintracker.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
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
import ng.com.tinweb.www.tintracker.data.TrackerTimeSetting;
import ng.com.tinweb.www.tintracker.helpers.LocationHelper;
import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifImageView;

public class WelcomeActivity extends AppCompatActivity implements View.OnClickListener, PopupMenu.OnMenuItemClickListener {

    // Layout container
    private RelativeLayout container;

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

    // Gif Image
    private GifDrawable gifFromResource;
    private GifImageView gifImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        trackerTimeSetting = new TrackerTimeSetting(this);

        setupViewProperties();
        setupGifImage();
        setupSettingBar();
        setUpTrackingTime();

        new LocationHelper();

    }

    private void setupViewProperties() {

        // View Group
        container = (RelativeLayout) findViewById(R.id.container);
        gifImage = (GifImageView) findViewById(R.id.gif_image);

        // TextView
        timeLimit = (TextView) findViewById(R.id.time_limit);

        // SeekBar
        seekBarTimeSetting = (SeekBar) findViewById(R.id.time_settings_bar);
        timeBar = (SeekBar) findViewById(R.id.timebar);

        // Button
        action_button = (Button) findViewById(R.id.action_button);
        actionButtonParams = (RelativeLayout.LayoutParams) action_button.getLayoutParams();
        action_button.setOnClickListener(this);
    }

    private void setupGifImage() {
        try {
            gifFromResource = new GifDrawable( getResources(), R.drawable.walking );
            gifImage.setImageDrawable(gifFromResource);
            gifFromResource.stop();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setUpTrackingTime() {
        int timeSetting = trackerTimeSetting.getTimeSetting();
        seekbarSteps = timeSetting * 60;
        seekBarTimeSetting.setProgress(timeSetting - 1);
        timeBar.setMax(seekbarSteps);
        timeBar.setProgress(0);
        String display = timeSetting+":00";
        timeLimit.setText(display);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_welcome, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch (id) {
            case R.id.action_settings:
                LinearLayout timeSetting = (LinearLayout) findViewById(R.id.time_setting);
                AppViewAnimation.toggleViewAnimation(timeSetting);
                break;
            case R.id.action_history:
                showHistoryPopUp();
                break;
        }

        return super.onOptionsItemSelected(item);
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

    private void updateTimeSetting(int time) {
        trackerTimeSetting.saveTimeSetting(time);
        Toast.makeText(WelcomeActivity.this, "Tracking time changed to: " + timeSetting + " minute(s)", Toast.LENGTH_LONG).show();
        setUpTrackingTime();
    }

    private void showHistoryPopUp() {
        View view = findViewById(R.id.action_history);

        PopupMenu popupMenu = new PopupMenu(this, view);
        popupMenu.setOnMenuItemClickListener(this);
        popupMenu.inflate(R.menu.history);
        popupMenu.show();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        switch (id) {
            case R.id.action_button:
                trackingButtonAction();
                break;
        }
    }

    private void trackingButtonAction() {

        if (buttonUp) {
            actionButtonParams.addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE);
            action_button.setText(R.string.start_tracking);
            gifFromResource.stop();
        } else {
            actionButtonParams.addRule(RelativeLayout.CENTER_VERTICAL, 0);
            actionButtonParams.setMargins(0, 50, 0, 0);
            action_button.setText(R.string.stop_tracking);
        }

        buttonUp = !buttonUp;
        AppViewAnimation.toggleViewAnimation(gifImage);
        AppViewAnimation.setViewTransition(actionButtonParams, container, action_button);

    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        int id = item.getItemId();

        switch(id) {
            case R.id.by_date:
                Toast.makeText(this, "Google API does all this shit ", Toast.LENGTH_LONG).show();
                break;
            case R.id.by_location:
                Toast.makeText(this, "Location Option Picked", Toast.LENGTH_LONG).show();
                break;
        }
        return false;
    }

}
