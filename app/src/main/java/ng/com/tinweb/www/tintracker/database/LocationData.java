package ng.com.tinweb.www.tintracker.database;

import java.util.Calendar;

/**
 * Created by kamiye on 11/5/15.
 */
public class LocationData {

    //private variables
    private int _id;
    private String _name;
    private String _long;
    private String _lat;
    private Calendar _date = Calendar.getInstance();
    private String _address;

    // Empty constructor
    public LocationData(){

    }

    public LocationData(int id, String name, String _long, String _lat, String address) {
        this._id = id;
        this._name = name;
        this._long = _long;
        this._lat = _lat;
        this._address = address;
    }

    // constructor
    public LocationData(int id, String name, String _address){
        this._id = id;
        this._name = name;
        this._address = _address;
    }

    // setting id
    public void setID(int id){
        this._id = id;
    }

    // setting name
    public void setName(String name){
        this._name = name;
    }

    // setting name
    public void setLong(String _long){
        this._long = _long;
    }

    // setting name
    public void setLat(String lat){
        this._lat = lat;
    }

    // setting phone number
    public void setAddress(String address){
        this._address = address;
    }

    // getting ID
    public int getID(){
        return this._id;
    }

    // getting name
    public String getName(){
        return this._name;
    }

    // getting name
    public String getLong(){
        return this._long;
    }

    // getting name
    public String getLat(){
        return this._lat;
    }

    // getting name
    public String getDate(){
        return this._date.toString();
    }

    // getting phone number
    public String getAddress(){
        return this._address;
    }
}
