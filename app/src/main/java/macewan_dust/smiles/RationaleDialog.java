package macewan_dust.smiles;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;

import java.util.Date;
import java.util.ResourceBundle;

/**
 * RationaleDialog is used to display the system's rationale for using external storage
 * for both import and export purposes
 */
// Referenced: https://stackoverflow.com/questions/20405070/how-to-use-dialog-fragment-showdialog-deprecated-android
public class RationaleDialog extends DialogFragment {

    private static final String TAG = "DialogFragment";

    /**
     * RationaleDialog returns a new instance of a Rationale Dialog with its
     * arguments set to the provided arguments.
     * @param permissionCode
     * @param requestCode
     * @return
     */
    public static RationaleDialog newInstance(String permissionCode, int requestCode) {
        RationaleDialog dialog = new RationaleDialog();

        // Create new bundle to store arguments and store arguments
        Bundle args = new Bundle();
        args.putString("permissionCode", permissionCode);
        args.putInt("requestCode", requestCode);
        dialog.setArguments(args);

        return dialog;
    }

    /**
     * onCreateDialog creates the new dialog and displays it on the screen
     * @param bundle
     * @return
     */
    @Override
    public Dialog onCreateDialog(Bundle bundle) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        Resources resources = getResources();

        // Build and return dialog
        return builder
                // Insert rationale
                .setMessage(resources.getString(R.string.rationale))

                // If the user clicks yes, create a new permissions window
                .setPositiveButton(resources.getString(R.string.yes), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Retrieve the passed permission code and request code
                        Bundle args = getArguments();
                        String permissionCode = args.getString("permissionCode");
                        int requestCode = args.getInt("requestCode");

                        // Open permissions window
                        MainActivity activity = (MainActivity) getActivity();
                        activity.initiatePermissions(permissionCode, requestCode);
                    }
                })

                // Do nothing if the user clicks no
                .setNegativeButton(resources.getString(R.string.no), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {// Do nothing
                    }
                })
                .setTitle(resources.getString(R.string.rationale_title))
                .create();
    }

}
