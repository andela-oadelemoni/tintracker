package ng.com.tinweb.www.tintracker.database;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import ng.com.tinweb.www.tintracker.R;
import ng.com.tinweb.www.tintracker.appConfig.ContextProvider;
import ng.com.tinweb.www.tintracker.data.AddressLookup;
import ng.com.tinweb.www.tintracker.data.AddressResultReceiver;

/**
 * Created by kamiye on 11/5/15.
 */
public class LocationData implements AddressResultReceiver.Receiver {

    private Context context = ContextProvider.getContext();

    //private variables
    private Location location;
    private AddressResultReceiver resultReceiver;

    private int _id;
    private String longitude;
    private String latitude;
    private String date = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
    private String time = new SimpleDateFormat("HH:mm", Locale.getDefault()).format(new Date());
    private String address;

    public LocationData() {}

    public LocationData(Location location) {
        setLocation(location);
        setupAddressResultReceiver();
    }

    private void setLocation(Location location) {
        this.location = location;
        this.longitude = String.valueOf(location.getLongitude());
        this.latitude = String.valueOf(location.getLatitude());
    }

    private void setupAddressResultReceiver() {
        resultReceiver = new AddressResultReceiver(new Handler());
        resultReceiver.setReceiver(this);
        startAddressLookup();
    }

    // setting id
    public void setID(int id){
        this._id = id;
    }

    // setting longitude
    public void setLongitude(String _long){
        this.longitude = _long;
    }

    // setting latitude
    public void setLatitude(String lat){
        this.latitude = lat;
    }

    // setting phone number
    public void setAddress(String address){
        this.address = address;
    }

    // set date
    public void setDate(String date) {
        this.date = date;
    }

    // set time
    public void setTime(String time) {
        this.time = time;
    }

    // getting ID
    public int getID(){
        return this._id;
    }

    // getting longitude
    public String getLongitude(){
        return this.longitude;
    }

    // getting latitude
    public String getLatitude(){
        return this.latitude;
    }

    // getting date
    public String getDate(){
        return this.date;
    }

    // getting time
    public String getTime() {
        return this.time;
    }

    // getting phone number
    public String getAddress(){
        return this.address;
    }

    protected void startAddressLookup() {
        Intent intent = new Intent(context, AddressLookup.class);
        intent.putExtra(AddressLookup.Constants.RECEIVER, resultReceiver);
        intent.putExtra(AddressLookup.Constants.LOCATION_DATA_EXTRA, location);
        context.startService(intent);
    }

    @Override
    public void onReceiveResult(int resultCode, Bundle resultData) {
        String mAddressOutput = resultData.getString(AddressLookup.Constants.RESULT_DATA_KEY);

        Toast.makeText(context, "Address: " + mAddressOutput, Toast.LENGTH_LONG).show();

        // Show a toast message if an address was found.
        if (resultCode == AddressLookup.Constants.SUCCESS_RESULT) {
            Toast.makeText(context, R.string.address_found, Toast.LENGTH_LONG).show();
        }
        this.address = mAddressOutput;
        saveLocationData();
    }

    private void saveLocationData() {
        DatabaseHandler database = new DatabaseHandler(context);
        // Inserting Contacts
        Log.i("Insert: ", "Inserting ..");
        database.addLocation(this);

        // Reading all contacts
        Log.i("Reading: ", "Reading all contacts..");
        List<LocationData> locations = database.getAllLocations();

        for (LocationData cn : locations) {
            String log = "Id: " + cn.getID() + " ,Lat: " + cn.getLatitude() + " ,Long: " + cn.getLongitude()+ " ,Address: " + cn.getAddress();
            // Writing Contacts to log
            Log.i("Name: ", log);
        }
    }

    public List<LocationData> getLocations() {
        DatabaseHandler database = new DatabaseHandler(context);

        return database.getAllLocations();
    }

}
