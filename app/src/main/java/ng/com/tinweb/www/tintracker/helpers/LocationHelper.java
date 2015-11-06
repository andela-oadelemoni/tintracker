package ng.com.tinweb.www.tintracker.helpers;

import android.content.Context;
import android.location.Location;
import android.os.Bundle;
import android.widget.Toast;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationServices;
import ng.com.tinweb.www.tintracker.appConfig.ContextProvider;

/**
 * Created by kamiye on 11/3/15.
 */
public class LocationHelper implements GoogleApiClient.OnConnectionFailedListener, LocationListener {

    private GoogleApiClient googleClient;
    private Context context = ContextProvider.getContext();
    private Location location;

    public void getLocation(LocationHelperCallback callback) {
        buildGoogleApiClient(callback);
    }

    private synchronized void buildGoogleApiClient(final LocationHelperCallback callback) {
        googleClient = new GoogleApiClient.Builder(context)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(new GoogleApiClient.ConnectionCallbacks() {
                    @Override
                    public void onConnected(Bundle bundle) {
                        location = LocationServices.FusedLocationApi.getLastLocation(
                                googleClient);
                        if (location != null) {
                            //startIntentService();
                            Toast.makeText(context, "Lat: " + location.getLatitude() + "\nLong: " + location.getLongitude(), Toast.LENGTH_LONG).show();
                            callback.onSuccess(location);
                        }
                        else Toast.makeText(context, "Location is null", Toast.LENGTH_LONG).show();
                        /*createLocationRequest();*/
                    }

                    @Override
                    public void onConnectionSuspended(int i) {
                        Toast.makeText(context, "Connection suspended", Toast.LENGTH_LONG).show();
                    }
                })
                .addOnConnectionFailedListener(this)
                .build();
        googleClient.connect();
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Toast.makeText(context, "Connection failed", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onLocationChanged(Location location) {
        this.location = location;
        Toast.makeText(context, "New Location is:\nLat: " + location.getLatitude() + "\nLong: " + location.getLongitude(), Toast.LENGTH_LONG).show();
    }

    public interface LocationHelperCallback {
        void onSuccess(Location location);
    }

}
