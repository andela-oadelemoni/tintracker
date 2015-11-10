package ng.com.tinweb.www.tintracker;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.test.ServiceTestCase;

import ng.com.tinweb.www.tintracker.data.ActivityRecognitionService;
import ng.com.tinweb.www.tintracker.data.AddressResultReceiver;

/**
 * Created by kamiye on 11/10/15.
 */
public class ActivityRecognitionServiceTest extends ServiceTestCase<ActivityRecognitionService> {

    private Intent intent;
    private AddressResultReceiver resultReceiver;

    public ActivityRecognitionServiceTest() {
        super(ActivityRecognitionService.class);
    }

    public void testActivityRecognitionService() {
        intent = new Intent(getSystemContext(), ActivityRecognitionService.class);
        super.startService(intent);
        assertNotNull(getService());
    }

    public void testActivityRecognitionResultReceiver() {
        resultReceiver = new AddressResultReceiver(new Handler());
        resultReceiver.setReceiver(new AddressResultReceiver.Receiver() {
            @Override
            public void onReceiveResult(int resultCode, Bundle resultData) {
                assertNotNull(resultData);
            }
        });
    }
}
