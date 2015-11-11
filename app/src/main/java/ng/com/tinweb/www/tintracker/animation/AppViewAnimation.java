package ng.com.tinweb.www.tintracker.animation;

import android.content.Context;
import android.os.Build;
import android.transition.TransitionManager;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import ng.com.tinweb.www.tintracker.R;
import ng.com.tinweb.www.tintracker.appConfig.ContextProvider;

/**
 * Created by kamiye on 11/3/15.
 */
public class AppViewAnimation {

    private static final Context CONTEXT = ContextProvider.getContext();

    public static void toggleViewAnimation(View view) {
        int visibility = view.getVisibility();
        Animation fadeIn = AnimationUtils.loadAnimation(CONTEXT, R.anim.abc_fade_in);
        Animation fadeOut = AnimationUtils.loadAnimation(CONTEXT, R.anim.abc_fade_out);

        if (visibility == View.GONE) {
            view.setVisibility(View.VISIBLE);
            view.startAnimation(fadeIn);
        }
        else {
            view.startAnimation(fadeOut);
            view.setVisibility(View.GONE);
        }
    }

    public static void setViewTransition(ViewGroup.LayoutParams params, ViewGroup container, View view) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            TransitionManager.beginDelayedTransition(container);
            view.setLayoutParams(params);
        }
    }

    public static void fadeOut(View view) {
        if (view.getVisibility() == View.VISIBLE)
            toggleViewAnimation(view);
    }
}
