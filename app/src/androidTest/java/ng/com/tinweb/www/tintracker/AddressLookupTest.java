package ng.com.tinweb.www.tintracker;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.test.ServiceTestCase;

import ng.com.tinweb.www.tintracker.data.AddressLookup;
import ng.com.tinweb.www.tintracker.data.AddressResultReceiver;

/**
 * Created by kamiye on 11/10/15.
 */
public class AddressLookupTest extends ServiceTestCase<AddressLookup> {

    private Intent intent;
    private AddressResultReceiver resultReceiver;

    public AddressLookupTest() {
        super(AddressLookup.class);
    }

    public void testAddressLookup() {
        intent = new Intent(getSystemContext(), AddressLookup.class);
        super.startService(intent);
        assertNotNull(getService());
    }

    public void testAddressResultReceiver() {
        resultReceiver = new AddressResultReceiver(new Handler());
        resultReceiver.setReceiver(new AddressResultReceiver.Receiver() {
            @Override
            public void onReceiveResult(int resultCode, Bundle resultData) {
                assertNotNull(resultData);
            }
        });
    }
}
