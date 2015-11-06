package ng.com.tinweb.www.tintracker.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import ng.com.tinweb.www.tintracker.R;
import ng.com.tinweb.www.tintracker.adapter.LocationHistoryAdapter;
import ng.com.tinweb.www.tintracker.database.LocationData;

/**
 * Created by kamiye on 11/6/15.
 */
public class LocationFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.history_location, container, false);
        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.location_recycler_view);

        // set the recyclerview layout manager
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        LocationData location = new LocationData();
        List<LocationData> locations = location.getLocations();

        LocationHistoryAdapter adapter = new LocationHistoryAdapter(locations);
        recyclerView.setAdapter(adapter);

        return rootView;
    }
}
