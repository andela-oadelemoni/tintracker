package ng.com.tinweb.www.tintracker.appConfig;

import android.app.Application;

/**
 * Created by kamiye on 11/1/15.
 */
public class TinTrackerApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        new ContextProvider(this);
    }
}
