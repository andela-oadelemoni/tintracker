package ng.com.tinweb.www.tintracker;

import android.test.InstrumentationTestCase;

import java.util.ArrayList;
import java.util.List;

import ng.com.tinweb.www.tintracker.adapter.LocationHistoryAdapter;
import ng.com.tinweb.www.tintracker.database.LocationData;

/**
 * Created by kamiye on 11/10/15.
 */
public class LocationHistoryAdaperTest extends InstrumentationTestCase {

    private List<LocationData> locationDataList = new ArrayList<>();
    private LocationData locationData;
    private LocationHistoryAdapter adapter;

    public void setUp() throws Exception {
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
        locationDataList.add(locationData);
        adapter = new LocationHistoryAdapter(locationDataList, false);
    }

    public void testAdapter_getCount() {
        assertEquals("Adapter Count Error", 1, adapter.getItemCount());
    }

    public void testAdapter_viewHolder() {
        int viewType = adapter.getItemViewType(0);
        assertTrue("ViewType error", viewType == 0);
    }

}
