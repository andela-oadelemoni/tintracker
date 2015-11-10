package ng.com.tinweb.www.tintracker;

import android.content.Context;
import android.test.InstrumentationTestCase;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import ng.com.tinweb.www.tintracker.animation.AppViewAnimation;

/**
 * Created by kamiye on 11/10/15.
 */
public class AppViewAnimationTest extends InstrumentationTestCase {


    public void testViewAnimation_toggleVisibility() {
        View view = new TextView(getInstrumentation().getContext());
        int visibility = view.getVisibility();
        AppViewAnimation.toggleViewAnimation(view);
        int visibility2 = view.getVisibility();
        assertTrue("View Animation error", visibility != visibility2);
    }

    public void testViewTransition() {
        Context context = getInstrumentation().getContext();
        ViewGroup viewGroup = new RelativeLayout(context);

        RelativeLayout.LayoutParams params1 = new RelativeLayout.LayoutParams(
                RelativeLayout.ALIGN_PARENT_TOP,
                RelativeLayout.CENTER_HORIZONTAL);

        RelativeLayout.LayoutParams params2 = new RelativeLayout.LayoutParams(
                RelativeLayout.ALIGN_PARENT_BOTTOM,
                RelativeLayout.CENTER_HORIZONTAL);

        View childView = new TextView(context);
        viewGroup.addView(childView);
        childView.setLayoutParams(params1);
        AppViewAnimation.setViewTransition(params2, viewGroup, childView);

        RelativeLayout.LayoutParams actualParams = (RelativeLayout.LayoutParams) childView.getLayoutParams();

        assertEquals("View Params Error", params2, actualParams);

    }
}
