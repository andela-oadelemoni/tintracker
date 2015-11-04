package ng.com.tinweb.www.tintracker.helpers;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.os.ResultReceiver;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import ng.com.tinweb.www.tintracker.appConfig.ContextProvider;
import ng.com.tinweb.www.tintracker.data.AddressLookup;
import ng.com.tinweb.www.tintracker.data.AddressResultReceiver;

/**
 * Created by kamiye on 11/3/15.
 */
public class LocationHelper implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {

    private GoogleApiClient googleClient;
    private Context context = ContextProvider.getContext();
    private Location location;
    private LocationRequest locationRequest;

    public LocationHelper() {
        buildGoogleApiClient();
    }

    private synchronized void buildGoogleApiClient() {
        googleClient = new GoogleApiClient.Builder(context)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
        googleClient.connect();
    }

    private void createLocationRequest() {
        locationRequest = new LocationRequest();
        locationRequest.setInterval(10000);
        locationRequest.setFastestInterval(5000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    public void startLocationUpdates() {
        LocationServices.FusedLocationApi.requestLocationUpdates(
                googleClient, locationRequest, this);
    }

    public void stopLocationUpdates() {
        LocationServices.FusedLocationApi.removeLocationUpdates(
                googleClient, this);
    }

    @Override
    public void onConnected(Bundle bundle) {
        location = LocationServices.FusedLocationApi.getLastLocation(
                googleClient);
        if (location != null) {
            startIntentService();
            Toast.makeText(context, "Lat: " + location.getLatitude() + "\nLong: " + location.getLongitude(), Toast.LENGTH_LONG).show();
        }
        else Toast.makeText(context, "Location is null", Toast.LENGTH_LONG).show();
        createLocationRequest();
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Toast.makeText(context, "Connection failed", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onConnectionSuspended(int i) {
        Toast.makeText(context, "Connection suspended", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onLocationChanged(Location location) {
        this.location = location;
        Toast.makeText(context, "New Location is:\nLat: " + location.getLatitude() + "\nLong: " + location.getLongitude(), Toast.LENGTH_LONG).show();
    }

    public interface LocationHelperCallback {
        void onSuccess(Location location);
        void onFailure();
    }


    protected void startIntentService() {
        ResultReceiver mReceiver = new AddressResultReceiver(new Handler());
        Intent intent = new Intent(context, AddressLookup.class);
        intent.putExtra(AddressLookup.Constants.RECEIVER, mReceiver);
        intent.putExtra(AddressLookup.Constants.LOCATION_DATA_EXTRA, location);
        context.startService(intent);
    }
}
