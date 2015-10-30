package ng.com.tinweb.www.tintracker;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.Toolbar;
import android.transition.TransitionManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.io.IOException;

import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifImageView;

public class WelcomeActivity extends AppCompatActivity implements View.OnClickListener, PopupMenu.OnMenuItemClickListener {

    private boolean buttonUp = false;
    private Button action_button;
    private GifDrawable gifFromResource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setupActionButton();

    }

    private void setupActionButton() {
        action_button = (Button) findViewById(R.id.action_button);
        action_button.setOnClickListener(this);

        try {
            gifFromResource = new GifDrawable( getResources(), R.drawable.walking );
            GifImageView image = (GifImageView) findViewById(R.id.gif_image);
            image.setImageDrawable(gifFromResource);
        } catch (IOException e) {
            e.printStackTrace();
        }
        gifFromResource.stop();
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
                Toast.makeText(this, "Settings Action", Toast.LENGTH_LONG).show();
                break;
            case R.id.action_history:
                showHistoryPopUp();
                break;
        }

        return super.onOptionsItemSelected(item);
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
        } else {
            params.addRule(RelativeLayout.CENTER_VERTICAL, 0);
            params.setMargins(0, 50, 0, 0);
            action_button.setText(R.string.stop_tracking);
            buttonUp = true;
            gifFromResource.start();
        }
        setTransitionAnimation(buttonUp, params);

    }

    private void setTransitionAnimation(boolean buttonUp, RelativeLayout.LayoutParams params) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            RelativeLayout container = (RelativeLayout) findViewById(R.id.container);
            TransitionManager.beginDelayedTransition(container);
            action_button.setLayoutParams(params);
        }
        else {
            float transitionDistance;
            if (buttonUp) transitionDistance = (float) -400.0;
            else transitionDistance = (float) 0.0;

            action_button.animate().translationY(transitionDistance).setDuration(500);
        }

    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        int id = item.getItemId();

        switch(id) {
            case R.id.by_date:
                Toast.makeText(this, "Date Option Picked", Toast.LENGTH_LONG).show();
                break;
            case R.id.by_location:
                Toast.makeText(this, "Location Option Picked", Toast.LENGTH_LONG).show();
                break;
        }
        return false;
    }
}
