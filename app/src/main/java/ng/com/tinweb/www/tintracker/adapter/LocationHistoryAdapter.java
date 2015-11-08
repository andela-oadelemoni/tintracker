package ng.com.tinweb.www.tintracker.adapter;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import ng.com.tinweb.www.tintracker.R;
import ng.com.tinweb.www.tintracker.database.LocationData;

/**
 * Created by kamiye on 11/6/15.
 */
public class LocationHistoryAdapter extends RecyclerView.Adapter<LocationHistoryAdapter.LocationViewHolder> {

    public static class LocationViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        static TextView dateLabel;
        static TextView address;
        static TextView coordinates;
        //static TextView date;

        LocationViewHolder(View itemView) {
            super(itemView);
            cv = (CardView) itemView.findViewById(R.id.location);
            dateLabel = (TextView) itemView.findViewById(R.id.date_label);
            address = (TextView) itemView.findViewById(R.id.location_address);
            coordinates = (TextView) itemView.findViewById(R.id.location_coordinates);
            //date = (TextView) itemView.findViewById(R.id.location_date);
        }
    }

    List<LocationData> locationDataList;
    boolean isNewDate = true;

    public LocationHistoryAdapter(List<LocationData> locationDataList) {
        this.locationDataList = locationDataList;
    }

    @Override
    public int getItemCount() {
        return locationDataList.size();
    }

    @Override
    public LocationViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.location, viewGroup, false);
        return new LocationViewHolder(v);
    }

    @Override
    public void onBindViewHolder(LocationViewHolder locationViewHolder, int i) {
        LocationData location = locationDataList.get(i);

        String address = location.getAddress();
        String longitude = location.getLongitude();
        String latitude = location.getLatitude();
        String coord = "Lat: " + latitude + " Long: " + longitude;
        String date = location.getDate();

        if (isNewDate) {
            LocationViewHolder.dateLabel.setText(date);
            isNewDate = false;
        } else {
            LocationViewHolder.dateLabel.setHeight(0);
        }
        LocationViewHolder.address.setText(address);
        LocationViewHolder.coordinates.setText(coord);
        //LocationViewHolder.date.setText(date);

        checkForNewDate(date, i);
    }

    private void checkForNewDate(String currentDate, int i) {
        if (i + 1 != locationDataList.size()) {
            String newDate = locationDataList.get(i + 1).getDate();
            if (!currentDate.equals(newDate)) {
                isNewDate = true;
            }
        }
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

}
