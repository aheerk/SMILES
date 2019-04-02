package macewan_dust.smiles;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v4.app.DialogFragment;
import android.widget.Toast;

/**
 * DeleteConfirmDialog is used to show a dialog box meant for confirming
 * that the user wants to delete all their SMILES data
 */
public class DeleteConfirmDialog extends DialogFragment {

    /**
     * newInstance returns a new instance of the Delete Confirm Dialog.
     * @return
     */
    public static DeleteConfirmDialog newInstance () {
        return new DeleteConfirmDialog();
    }

    @Override
    public Dialog onCreateDialog(Bundle bundle) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        Resources resources = getResources();

        // Build and return dialog
        return builder
                .setMessage(resources.getString(R.string.delete_confirm_message))

                // If the user clicks yes, initiate delete
                .setPositiveButton(resources.getString(R.string.yes), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Context context = getContext();
                        // Delete the files
                        ScoringLab.get(context).deleteAll();
                        Toast.makeText(context, R.string.delete_success, Toast.LENGTH_SHORT)
                                .show();
                    }
                })

                // Do nothing if the user clicks no
                .setNegativeButton(resources.getString(R.string.no), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Do nothing
                    }
                })
                .setTitle(resources.getString(R.string.delete_confirm_title))
                .create();
    }


}
