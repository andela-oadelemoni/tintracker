package ng.com.tinweb.www.tintracker;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.transition.TransitionManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class WelcomeActivity extends AppCompatActivity implements View.OnClickListener {

    private Animation moveUpAnimation;
    private Animation moveDownAnimation;
    private boolean buttonUp = false;
    private Button action_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setupActionButton();

        setupAnimation();
    }

    private void setupAnimation() {
        moveUpAnimation = AnimationUtils.loadAnimation(this, R.anim.abc_slide_out_top);
        moveDownAnimation = AnimationUtils.loadAnimation(this, R.anim.abc_slide_in_top);
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
                Toast.makeText(this, "Settings Action", Toast.LENGTH_LONG).show();
                break;
            case R.id.action_history:
                Toast.makeText(this, "History Action", Toast.LENGTH_LONG).show();
                break;
        }

        return super.onOptionsItemSelected(item);
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
        } else {
            params.addRule(RelativeLayout.CENTER_VERTICAL, 0);
            params.setMargins(0, 50, 0, 0);
            action_button.setText(R.string.stop_tracking);
            buttonUp = true;
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
}
