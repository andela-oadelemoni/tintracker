package ng.com.tinweb.www.tintracker.helpers;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.ActivityRecognition;

import ng.com.tinweb.www.tintracker.appConfig.ContextProvider;
import ng.com.tinweb.www.tintracker.data.ActivityRecognitionService;


/**
 * Created by kamiye on 11/4/15.
 */
public class TinTrackerActivityRecognition implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private GoogleApiClient googleClient;
    private Context context = ContextProvider.getContext();


    public TinTrackerActivityRecognition() {
        buildGoogleApiClient();
    }

    private void buildGoogleApiClient() {
        googleClient = new GoogleApiClient.Builder(context)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(ActivityRecognition.API)
                .build();
        googleClient.connect();
    }

    @Override
    public void onConnected(Bundle bundle) {
        Intent intent = new Intent(context, ActivityRecognitionService.class);
        PendingIntent pendingIntent = PendingIntent
                .getService(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        ActivityRecognition.ActivityRecognitionApi.requestActivityUpdates(googleClient, 0, pendingIntent);
    }

    @Override
    public void onConnectionSuspended(int i) {}

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {}
}
