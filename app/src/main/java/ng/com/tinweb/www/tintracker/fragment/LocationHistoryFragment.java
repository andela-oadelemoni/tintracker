package ng.com.tinweb.www.tintracker.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TabWidget;

import ng.com.tinweb.www.tintracker.R;

/**
 * Created by kamiye on 11/6/15.
 */
public class LocationHistoryFragment extends Fragment {

    private FragmentTabHost fragmentTabHost;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Context context = getActivity();

        View rootView = inflater.inflate(R.layout.fragment_history, container, false);

        fragmentTabHost = (FragmentTabHost) rootView.findViewById(R.id.tabhost);
        fragmentTabHost.setup(getActivity(), getChildFragmentManager(), R.id.realtabcontent);

        fragmentTabHost.addTab(fragmentTabHost.newTabSpec("date").setIndicator(context.getString(R.string.date_fragment_indicator)),
                DateFragment.class, null);
        fragmentTabHost.addTab(fragmentTabHost.newTabSpec("location").setIndicator(context.getString(R.string.location_fragment_indicator)),
                LocationFragment.class, null);

        TabWidget tabWidget = fragmentTabHost.getTabWidget();
        for (int i = 0; i < tabWidget.getTabCount(); i++)
        {
            tabWidget.getChildAt(i).getLayoutParams().height = (int) (40 * getResources().getDisplayMetrics().density);
        }

        return rootView;
    }
}
