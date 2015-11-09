package ng.com.tinweb.www.tintracker.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kamiye on 11/5/15.
 */
public class DatabaseHandler extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 5;

    // Database Name
    private static final String DATABASE_NAME = "locationsManager";

    // Contacts table name
    private static final String TABLE_LOCATIONS = "locations";

    // Contacts Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_ADDRESS = "address";
    private static final String KEY_LAT = "latitude";
    private static final String KEY_LONG = "longitude";
    private static final String KEY_DATE = "date";
    private static final String KEY_TIME = "time";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_LOCATIONS_TABLE = "CREATE TABLE " + TABLE_LOCATIONS + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_ADDRESS + " TEXT, "
                + KEY_LAT + " TEXT, "
                + KEY_LONG + " TEXT, "
                + KEY_DATE + " DATE, "
                + KEY_TIME + " TIME "
                + ")";
        db.execSQL(CREATE_LOCATIONS_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LOCATIONS);

        // Create tables again
        onCreate(db);
    }

    // Adding new location
    public void addLocation(LocationData locationData) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ADDRESS, locationData.getAddress());
        values.put(KEY_LAT, locationData.getLatitude());
        values.put(KEY_LONG, locationData.getLongitude());
        values.put(KEY_DATE, locationData.getDate());
        values.put(KEY_TIME, locationData.getTime());

        // Inserting Row
        db.insert(TABLE_LOCATIONS, null, values);
        db.close(); // Closing database connection
    }

    // Getting single location
    public LocationData getLocation(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_LOCATIONS, new String[]{KEY_ID, KEY_ADDRESS,
                        KEY_LAT, KEY_LONG, KEY_DATE, KEY_TIME}, KEY_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        LocationData locationData = new LocationData();
        locationData.setID(Integer.parseInt(cursor.getString(0)));
        locationData.setAddress(cursor.getString(1));
        locationData.setLatitude(cursor.getString(2));
        locationData.setLongitude(cursor.getString(3));
        locationData.setDate(cursor.getString(4));
        locationData.setTime(cursor.getString(5));

        // return location data
        return locationData;
    }

    // Getting All locations
    public List<LocationData> getAllLocations() {
        List<LocationData> locationList = new ArrayList<>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_LOCATIONS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                LocationData locationData = new LocationData();
                locationData.setID(Integer.parseInt(cursor.getString(0)));
                locationData.setAddress(cursor.getString(1));
                locationData.setLatitude(cursor.getString(2));
                locationData.setLongitude(cursor.getString(3));
                locationData.setDate(cursor.getString(4));
                locationData.setTime(cursor.getString(5));
                // Adding contact to list
                locationList.add(locationData);
            } while (cursor.moveToNext());
        }

        // return contact list
        return locationList;
    }

    // Getting locations Count
    public int getLocationsCount() {
        String countQuery = "SELECT  * FROM " + TABLE_LOCATIONS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        // return count
        return cursor.getCount();
    }

    // Updating single location
    public int updateLocation(LocationData locationData) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ADDRESS, locationData.getAddress());
        values.put(KEY_LAT, locationData.getLatitude());
        values.put(KEY_LONG, locationData.getLongitude());
        values.put(KEY_DATE, locationData.getDate());
        values.put(KEY_TIME, locationData.getTime());

        // updating row
        return db.update(TABLE_LOCATIONS, values, KEY_ID + " = ?",
                new String[]{String.valueOf(locationData.getID())});

    }

    // Deleting single location
    public void deleteLocation(LocationData locationData) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_LOCATIONS, KEY_ID + " = ?",
                new String[]{String.valueOf(locationData.getID())});
        db.close();
    }

    public List<LocationData> getLocationsByGroup() {
        List<LocationData> locationList = new ArrayList<>();
        // Select All Query
        String selectQuery = "SELECT  *, COUNT(" + KEY_ADDRESS + ") AS address_count FROM " + TABLE_LOCATIONS + " GROUP BY "
                + KEY_ADDRESS + " ORDER BY address_count DESC";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                LocationData locationData = new LocationData();
                locationData.setID(Integer.parseInt(cursor.getString(0)));
                locationData.setAddress(cursor.getString(1));
                locationData.setLatitude(cursor.getString(2));
                locationData.setLongitude(cursor.getString(3));
                locationData.setDate(cursor.getString(4));
                locationData.setTime(cursor.getString(5));
                locationData.setOccurence(cursor.getInt(6)); // for column count result
                // Adding contact to list
                locationList.add(locationData);
            } while (cursor.moveToNext());
        }

        // return contact list
        return locationList;
    }
}
