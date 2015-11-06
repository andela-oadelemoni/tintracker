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
public class LocationHistoryAdapter extends RecyclerView.Adapter<LocationHistoryAdapter.ItemViewHolder>{

    public static class ItemViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        static TextView address;
        static TextView coordinates;
        static TextView date;

        ItemViewHolder(View itemView) {
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.location);
            address = (TextView)itemView.findViewById(R.id.location_address);
            coordinates = (TextView)itemView.findViewById(R.id.location_coordinates);
            date = (TextView)itemView.findViewById(R.id.location_date);
        }
    }

    List<LocationData> locationDataList;

    public LocationHistoryAdapter(List<LocationData> locationDataList){
        this.locationDataList = locationDataList;
    }

    @Override
    public int getItemCount() {
        return locationDataList.size();
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.location, viewGroup, false);
        return new ItemViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ItemViewHolder itemViewHolder, int i) {
        LocationData location = locationDataList.get(i);

        String address = location.getAddress();
        String longitude = location.getLongitude();
        String latitude = location.getLatitude();
        String coord = "Lat: " + latitude + " Long: " + longitude;
        String date = location.getDate();

        ItemViewHolder.address.setText(address);
        ItemViewHolder.coordinates.setText(coord);
        ItemViewHolder.date.setText(date);
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

}
