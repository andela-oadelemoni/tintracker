package ng.com.tinweb.www.tintracker;

import android.test.AndroidTestCase;
import android.test.RenamingDelegatingContext;

import java.util.List;

import ng.com.tinweb.www.tintracker.database.DatabaseHandler;
import ng.com.tinweb.www.tintracker.database.LocationData;

/**
 * Created by kamiye on 11/10/15.
 */
public class DatabaseHandlerTest extends AndroidTestCase {


    private DatabaseHandler db;
    private LocationData locationData;

    @Override
    public void setUp() throws Exception {
        super.setUp();
        RenamingDelegatingContext context = new RenamingDelegatingContext(getContext(), "test_");
        db = new DatabaseHandler(context);
        setupLocationData();
    }

    private void setupLocationData() {
        locationData = new LocationData();
        locationData.setID(1);
        locationData.setOccurrence(1);
        locationData.setAddress("2, Address St.");
        locationData.setDate("23-12-1989");
        locationData.setLatitude("3.2353");
        locationData.setLongitude("6.2345");
        locationData.setTime("23:23");
    }

    @Override
    public void tearDown() throws Exception {
        db.close();
        super.tearDown();
    }

    public void testDatabaseHandler() {
        // Here i have my new database wich is not connected to the standard database of the App
        db.addLocation(locationData);
        List<LocationData> locations = db.getAllLocations();
        assertEquals("Location insertion error", 1, locations.size());

        db.addLocation(locationData);
        List<LocationData> locations2 = db.getAllLocations();
        assertEquals("Location listing error", 2, locations2.size());

        List<LocationData> locationByGroup = db.getLocationsByGroup();
        assertEquals("Location listing by group error", 2, locationByGroup.get(0).getOccurrence());
    }

}
