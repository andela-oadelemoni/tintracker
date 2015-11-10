package ng.com.tinweb.www.tintracker;

import android.content.Context;
import android.test.InstrumentationTestCase;

import ng.com.tinweb.www.tintracker.appConfig.ContextProvider;

/**
 * Created by kamiye on 11/10/15.
 */
public class ContextProviderTest extends InstrumentationTestCase {


    public void testContextProvider() {
        Context context = ContextProvider.getContext();
        assertNotNull(context);
    }
}
