package ng.com.tinweb.www.tintracker.helpers;

import android.content.Context;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import ng.com.tinweb.www.tintracker.appConfig.ContextProvider;

/**
 * Created by kamiye on 11/3/15.
 */
public class LocationHelper implements GoogleApiClient.OnConnectionFailedListener {

    private GoogleApiClient googleClient;
    private Context context = ContextProvider.getContext();
    private Location location;
    private LocationRequest locationRequest;

    public LocationHelper() {
        buildGoogleApiClient();
        buildLocationRequest();
    }

    private void buildLocationRequest() {
        // Create the LocationRequest object
        locationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(3 * 1000)        // 3 seconds, in milliseconds
                .setFastestInterval(1000); // 1 second (in milliseconds)
    }

    private void buildGoogleApiClient() {
        googleClient = new GoogleApiClient.Builder(context)
                .addApi(LocationServices.API)
                .addOnConnectionFailedListener(this)
                .build();
        googleClient.connect();
    }

    public void getLocation(final LocationHelperCallback callback) {
        googleClient.registerConnectionCallbacks(new GoogleApiClient.ConnectionCallbacks() {
            @Override
            public void onConnected(Bundle bundle) {
                Toast.makeText(context, "Listener is called now", Toast.LENGTH_LONG).show();
                location = LocationServices.FusedLocationApi.getLastLocation(
                        googleClient);
                if (location != null) {
                    Toast.makeText(context, "Lat: " + location.getLatitude() + "\nLong: " + location.getLongitude(), Toast.LENGTH_LONG).show();
                    callback.onSuccess(location);
                } else {
                    Toast.makeText(context, "Location is null", Toast.LENGTH_LONG).show();
                    getLocationFromUpdate(callback);
                }
            }

            @Override
            public void onConnectionSuspended(int i) {
                Toast.makeText(context, "Connection suspended", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void getLocationFromUpdate(final LocationHelperCallback callback) {
        LocationServices.FusedLocationApi.requestLocationUpdates(googleClient, locationRequest, new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                Log.i("From Request", "location object called");
                LocationHelper.this.location = location;
                callback.onSuccess(LocationHelper.this.location);
                LocationServices.FusedLocationApi.removeLocationUpdates(googleClient, this);
            }
        });
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Toast.makeText(context, "Connection failed", Toast.LENGTH_LONG).show();
    }

    public interface LocationHelperCallback {
        void onSuccess(Location location);
    }

}
