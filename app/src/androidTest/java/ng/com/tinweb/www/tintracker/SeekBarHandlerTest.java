package ng.com.tinweb.www.tintracker;

import android.test.AndroidTestCase;
import android.widget.SeekBar;

import ng.com.tinweb.www.tintracker.helpers.SeekBarHandler;

/**
 * Created by kamiye on 11/10/15.
 */
public class SeekBarHandlerTest extends AndroidTestCase {

    private SeekBarHandler seekBarHandler;

    public SeekBarHandlerTest() {
        super();
    }

    public void setUp() throws Exception {
        setupSeekBar();
    }

    private void setupSeekBar() {
        SeekBar seekBar = new SeekBar(getContext());
        int steps = 2;
        seekBarHandler = new SeekBarHandler(seekBar, steps);
    }

    public void testSeekBarHandler_setTimer() {
        seekBarHandler.setTimer(new SeekBarHandler.SeekBarHandlerCallBack() {
            @Override
            public void onCountDownFinish() {
                assertTrue(true);
            }

            @Override
            public void seekBarTick(int progress) {}
        });
        seekBarHandler.startTimer();
    }


}
