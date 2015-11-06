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
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "locationsManager";

    // Contacts table name
    private static final String TABLE_LOCATIONS = "locations";

    // Contacts Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_ADDRESS = "address";
    private static final String KEY_LAT = "latitude";
    private static final String KEY_LONG = "longitude";
    private static final String KEY_DATE = "date";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_LOCATIONS + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT,"
                + KEY_ADDRESS + " TEXT"
                + KEY_LAT + " TEXT"
                + KEY_LONG + " TEXT"
                + KEY_DATE + " DATE"
                + ")";
        db.execSQL(CREATE_CONTACTS_TABLE);
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
    public void addLocation(LocationData location) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, location.getName()); // Contact Name
        values.put(KEY_ADDRESS, location.getAddress()); // Contact Phone Number
        values.put(KEY_LAT, location.getLat()); // Contact Phone Number
        values.put(KEY_LONG, location.getLong()); // Contact Phone Number
        values.put(KEY_DATE, location.getDate()); // Contact Phone Number

        // Inserting Row
        db.insert(TABLE_LOCATIONS, null, values);
        db.close(); // Closing database connection
    }

    // Getting single location
    public LocationData getLocation(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_LOCATIONS, new String[] { KEY_ID,
                        KEY_NAME, KEY_ADDRESS }, KEY_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        LocationData location = new LocationData(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2));
        // return location
        return location;
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
                LocationData location = new LocationData();
                location.setID(Integer.parseInt(cursor.getString(0)));
                location.setName(cursor.getString(1));
                location.setAddress(cursor.getString(2));
                location.setLat(cursor.getString(3));
                location.setLong(cursor.getString(4));
                // Adding contact to list
                locationList.add(location);
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
    public int updateLocation(LocationData location) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, location.getName());
        values.put(KEY_ADDRESS, location.getAddress());
        values.put(KEY_LAT, location.getLat());
        values.put(KEY_LONG, location.getLong());
        values.put(KEY_DATE, location.getDate());

        // updating row
        return db.update(TABLE_LOCATIONS, values, KEY_ID + " = ?",
                new String[] { String.valueOf(location.getID()) });

    }

    // Deleting single location
    public void deleteLocation(LocationData location) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_LOCATIONS, KEY_ID + " = ?",
                new String[] { String.valueOf(location.getID()) });
        db.close();
    }
}
