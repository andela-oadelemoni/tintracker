package ng.com.tinweb.www.tintracker.activities;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.Toolbar;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

import java.io.IOException;

import ng.com.tinweb.www.tintracker.R;
import ng.com.tinweb.www.tintracker.sensor.TinTrackerSensor;
import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifImageView;

public class WelcomeActivity extends AppCompatActivity implements View.OnClickListener, PopupMenu.OnMenuItemClickListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private boolean buttonUp = false;
    private Button action_button;
    private GifDrawable gifFromResource;
    private RelativeLayout container;
    private GifImageView gifImage;
    private GoogleApiClient googleClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        container = (RelativeLayout) findViewById(R.id.container);

        setupActionButton();
        setupGifImage();

        buildGoogleApiClient();

    }

    private void setupGifImage() {
        gifImage = (GifImageView) findViewById(R.id.gif_image);
        try {
            gifFromResource = new GifDrawable( getResources(), R.drawable.walking );
            gifImage.setImageDrawable(gifFromResource);
            gifFromResource.stop();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setupActionButton() {
        action_button = (Button) findViewById(R.id.action_button);
        action_button.setOnClickListener(this);
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
                showTimeSetting();
                break;
            case R.id.action_history:
                showHistoryPopUp();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void showTimeSetting() {
        LinearLayout timeSetting = (LinearLayout) findViewById(R.id.time_setting);
        int visibility = timeSetting.getVisibility();

        Animation fadeIn = AnimationUtils.loadAnimation(this, R.anim.abc_fade_in);
        Animation fadeOut = AnimationUtils.loadAnimation(this, R.anim.abc_fade_out);

        if (visibility == View.GONE) {
            timeSetting.setVisibility(View.VISIBLE);
            timeSetting.startAnimation(fadeIn);
        }
        else {
            timeSetting.startAnimation(fadeOut);
            timeSetting.setVisibility(View.GONE);
        }
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
                switchButtonAnimation();
                break;
        }
    }

    private void switchButtonAnimation() {

        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) action_button.getLayoutParams();
        if (buttonUp) {
            params.addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE);
            action_button.setText(R.string.start_tracking);
            buttonUp = false;
            gifFromResource.stop();
            switchGifAnimation();
        } else {
            params.addRule(RelativeLayout.CENTER_VERTICAL, 0);
            params.setMargins(0, 50, 0, 0);
            action_button.setText(R.string.stop_tracking);
            switchGifAnimation();
            buttonUp = true;
        }
        setTransitionAnimation(params);

    }

    private void switchGifAnimation() {
        int visibility = gifImage.getVisibility();
        Animation fadeIn = AnimationUtils.loadAnimation(this, R.anim.abc_fade_in);
        Animation fadeOut = AnimationUtils.loadAnimation(this, R.anim.abc_fade_out);

        if (visibility == View.GONE) {
            gifImage.setVisibility(View.VISIBLE);
            gifImage.startAnimation(fadeIn);
        }
        else {
            gifImage.startAnimation(fadeOut);
            gifImage.setVisibility(View.GONE);
        }
    }

    private void setTransitionAnimation(RelativeLayout.LayoutParams params) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            TransitionManager.beginDelayedTransition(container);
            action_button.setLayoutParams(params);
        }
    }

    public void sensorIsh(SensorEvent event) {
        TextView textView = (TextView) findViewById(R.id.time_limit);
        if (event.values[0] > 4) {
            textView.setText(String.valueOf(event.values[0]));
        }
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        int id = item.getItemId();

        switch(id) {
            case R.id.by_date:
                TinTrackerSensor sensor = new TinTrackerSensor(this);
                Sensor speedCheck = sensor.getSensor();
                Toast.makeText(this, "Sensor name: "+speedCheck.getName()+". Sensor vendor: "+speedCheck.getVendor(), Toast.LENGTH_LONG).show();
                break;
            case R.id.by_location:
                Toast.makeText(this, "Location Option Picked", Toast.LENGTH_LONG).show();
                break;
        }
        return false;
    }

    @Override
    public void onConnected(Bundle bundle) {
        TextView text = (TextView) findViewById(R.id.time_limit);
        Location location = LocationServices.FusedLocationApi.getLastLocation(
                googleClient);
        if (location != null) {
            Log.i("Location", "Location is so not null");
            text.setText("Lat: "+location.getLatitude());
            Toast.makeText(this, "Lat: "+location.getLatitude()+"\nLong: "+location.getLongitude(), Toast.LENGTH_LONG).show();
        }
        else Log.i("Location", "Location is null");
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    protected synchronized void buildGoogleApiClient() {
        googleClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
        googleClient.connect();
    }
}
