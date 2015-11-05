package ng.com.tinweb.www.tintracker.helpers;

import android.os.CountDownTimer;
import android.widget.SeekBar;

/**
 * Created by kamiye on 11/5/15.
 */
public class SeekBarHandler {

    private static final int COUNT_DOWN_INTERVAL = 1000;
    private SeekBar seekBar;
    private int steps;
    private CountDownTimer seekBarTimer;
    private int progressSteps = 0;

    public SeekBarHandler(SeekBar seekBar, int steps) {
        this.seekBar = seekBar;
        this.steps = steps;
    }

    public void setSteps(int steps) {
        this.steps = steps;
    }

    public void setTimer(final SeekBarHandlerCallBack callback) {
        progressSteps = 0;
        seekBarTimer = new CountDownTimer(steps * COUNT_DOWN_INTERVAL, COUNT_DOWN_INTERVAL) {

            public void onTick(long millisUntilFinished) {
                seekBar.setProgress(++progressSteps);
                callback.seekBarTick(progressSteps);
            }

            public void onFinish() {
                callback.onCountDownFinish();
            }
        };
    }

    public void startTimer() {
        seekBarTimer.start();
    }

    public void resetTimer() {
        seekBar.setProgress(0);
        seekBarTimer.cancel();
    }

    public interface SeekBarHandlerCallBack {
        void onCountDownFinish();
        void seekBarTick(int progress);
    }

}
