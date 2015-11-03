package ng.com.tinweb.www.tintracker.appConfig;

import android.content.Context;

/**
 * Created by kamiye on 11/1/15.
 */
public class ContextProvider {

    private static Context context;

    public ContextProvider(Context context) {
        ContextProvider.context = context;
    }

    public static Context getContext() {
        return context;
    }
}