package ng.com.tinweb.www.tintracker.data;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.os.ResultReceiver;

/**
 * Created by kamiye on 11/4/15.
 */
public class AddressResultReceiver extends ResultReceiver {
    private Receiver mReceiver;

    public AddressResultReceiver(Handler handler) {
        super(handler);
    }

    @Override
    protected void onReceiveResult(int resultCode, Bundle resultData) {
        if (mReceiver != null) mReceiver.onReceiveResult(resultCode, resultData);
    }

    public void setReceiver(Receiver receiver) {
        mReceiver = receiver;
    }

    public interface Receiver {
        void onReceiveResult(int resultCode, Bundle resultData);
    }
}
