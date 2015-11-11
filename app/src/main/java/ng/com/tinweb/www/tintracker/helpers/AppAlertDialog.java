package ng.com.tinweb.www.tintracker.helpers;

import android.app.Activity;
import android.content.DialogInterface;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.widget.Button;
import android.widget.TextView;

import ng.com.tinweb.www.tintracker.R;


/**
 * Created by kamiye on 10/27/15.
 */
public class AppAlertDialog implements DialogInterface.OnClickListener {

    // Internet Error Dialog Constants
    private static final String ACTION = "OK";

    private Activity activity;

    public AppAlertDialog(Activity activity) {
        this.activity = activity;
    }

    public void settingsError() {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle(activity.getString(R.string.error_title));
        builder.setMessage(activity.getString(R.string.settings_error));
        builder.setNegativeButton(ACTION, this);
        builder.setIcon(R.drawable.ic_warning);

        createDialog(builder, false);
    }

    public void showApplicationInfo() {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle(activity.getString(R.string.info_title));
        builder.setMessage(activity.getString(R.string.part_one));
        builder.setNegativeButton(ACTION, this);
        builder.setIcon(R.drawable.ic_info_black);

        createDialog(builder, true);
    }

    private void createDialog(AlertDialog.Builder builder, boolean isAppInfo) {
        AlertDialog dialog = builder.create();
        dialog.show();

        TextView message = (TextView) dialog.findViewById(android.R.id.message);
        if (isAppInfo) message.setTextSize(14);

        Button action = dialog.getButton(DialogInterface.BUTTON_NEGATIVE);
        action.setTextColor(ContextCompat.getColor(activity, R.color.colorAccent));
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        dialog.dismiss();
    }
}
