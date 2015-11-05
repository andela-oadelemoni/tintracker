package ng.com.tinweb.www.tintracker.data;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.os.ResultReceiver;
import android.widget.Toast;
import ng.com.tinweb.www.tintracker.R;
import ng.com.tinweb.www.tintracker.appConfig.ContextProvider;

/**
 * Created by kamiye on 11/4/15.
 */
public class AddressResultReceiver extends ResultReceiver {
    private String mAddressOutput;
    private Context context = ContextProvider.getContext();

    public AddressResultReceiver(Handler handler) {
        super(handler);
    }

    @Override
    protected void onReceiveResult(int resultCode, Bundle resultData) {

        // Display the address string
        // or an error message sent from the intent service.
        mAddressOutput = resultData.getString(AddressLookup.Constants.RESULT_DATA_KEY);
        displayAddressOutput();

        // Show a toast message if an address was found.
        if (resultCode == AddressLookup.Constants.SUCCESS_RESULT) {
            showToast(context.getString(R.string.address_found));
        }

    }

    private void displayAddressOutput() {
        Toast.makeText(context, "Address: "+mAddressOutput, Toast.LENGTH_LONG).show();
    }

    private void showToast(String string) {
        Toast.makeText(context, string, Toast.LENGTH_LONG).show();
    }
}
