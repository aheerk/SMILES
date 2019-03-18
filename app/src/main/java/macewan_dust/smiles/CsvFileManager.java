package macewan_dust.smiles;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class CsvFileManager implements ActivityCompat.OnRequestPermissionsResultCallback {

    public static final String TAG = "CsvFileManager";
    private static final int REQUEST_WRITE_EXTERNAL_STORAGE = 222;
    private static final int REQUEST_READ_EXTERNAL_STORAGE = 111;

    private Activity mActivity;
    private Context mContext;

    public CsvFileManager (Activity activity, Context context) {
        mActivity = activity;
        mContext = context;
    }

    /*--------- PERMISSION STUFF ----------------------------------------------------------------*/

    /**
     * checkWriteExternalPermissions checks if the app has permissions to write to external storage
     * If they already have permission, true is returned. Otherwise, false is returned and
     * the device will ask the user for permissions.
     *
     * @param rationale
     * @return
     */
    private boolean checkWriteExternalPermissions(String rationale)  {
        // Check if we have permission to export
        if (ContextCompat.checkSelfPermission(mContext,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)  != PackageManager.PERMISSION_GRANTED) {
            Log.d(TAG, "No permission to write to external storage");

            // Show the dialog box to request permissions
            if (ActivityCompat.shouldShowRequestPermissionRationale(mActivity,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)){
                Log.d(TAG, "Show the rationale!");

                // Show explanation?

            } else {
                Log.d(TAG, "Need to ask for permissions to write external storage.");
                String[] permissions = new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE};
                ActivityCompat.requestPermissions(mActivity, permissions,
                        REQUEST_WRITE_EXTERNAL_STORAGE);
            }
        } else {
            Log.d(TAG, "Write permissions granted from previous use.");
            return true;
        }
        return false;
    }


    /**
     * Determines whether permissions for external storage use
     * have been given and disables corresponding functionality accordingly
     * Android calls this after requesting permissions
     *
     * @param requestCode Unique code used to identify a permission request
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {

            case REQUEST_WRITE_EXTERNAL_STORAGE: {

                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.d(TAG, "Write External Storage permission granted.");
                    // Write to csv
                    writeCSVFile();
                } else {
                    Log.d(TAG, "No permissions given to write external storage.");
                    Toast.makeText(mActivity, R.string.csv_no_write_permission, Toast.LENGTH_SHORT).show();
                }
                return;
            }
            case REQUEST_READ_EXTERNAL_STORAGE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.d(TAG, "Read External Storage permission granted.");

                    // Good to go
                    Toast.makeText(mActivity, R.string.balanced, Toast.LENGTH_SHORT).show();
                } else {
                    Log.d(TAG, "No permissions given to read external storage.");
                    Toast.makeText(mActivity, R.string.csv_no_read_permission, Toast.LENGTH_SHORT).show();
                }
            }
        }

    }

    /* Checks if external storage is available for read and write
     * Source: https://developer.android.com/training/data-storage/files#java
     */
    private static boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }



    /**
     * getPublicStorageDir retrieves the storage directory for the downloads folder on the device
     * @return
     * source: https://developer.android.com/training/data-storage/files#java
     * Referenced: https://developer.android.com/reference/android/os/Environment.html#getExternalStoragePublicDirectory(java.lang.String)
     */
    public static File getPublicStorageDir() {
        // Get the directory for the user's public pictures directory.
        File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS);
        if (!path.exists()) {
            Log.e(TAG, "Directory not created");
        }
        return path;
    }

    //

    /*----EXPORT FUNCTIONALITY------------------------------------------------------------------------*/


    /**
     * exportScores checks permissions for exporting the user's data into a new CSV
     * file. If given permissions, it will save the csv file to the user's documents
     * folder
     */
    public void exportScores() {
        Boolean havePermissions = checkWriteExternalPermissions("");
        if (havePermissions) {
            writeCSVFile();
        }
    }

    /**
     * getFilename retrieves an appropriate filename for an exported
     * CSV file based on today's date
     * @return A string filename
     */
    private static String getFilename() {
        Date today = new Date();
        String formatted = DateFormat.getDateInstance(DateFormat.MEDIUM).format(today);
        return "scores_" + formatted + ".csv";
    }

    /**
     * writeCSVFile creates a new file with the specified file name
     * that includes all the user's scores stored on the device.
     *
     * Referenced https://stackoverflow.com/questions/31063216/filenotfoundexception-storage-emulated-0-android
     * https://stackoverflow.com/questions/35132693/set-encoding-as-utf-8-for-a-filewriter
     */
    private void writeCSVFile() {
        String filename = getFilename();

        // Check if there is room to write
        if (isExternalStorageWritable()) {
            Log.d(TAG, "External storage is writable");
        } else {
            Log.d(TAG, "External storage is not writable. Aborting write.");
            Toast.makeText(mContext, R.string.csv_export_failure, Toast.LENGTH_SHORT).show();
            return;
        }

        // Retrieve filepath to external documents directory
        File path = getPublicStorageDir();
        Log.d(TAG, "PATH:" + path.toString());

        File file = new File(path, filename);

        // Make sure the Documents directory exists before writing to it
        if (!path.exists()) {
            file.mkdir();
            Log.d(TAG, "Documents path exists now: " + path.exists());
        }

        // Create the file if one needs to be created (otherwise, existing file will be overwritten
        if (!file.exists()) {
            Log.d(TAG, filename + " does not exist.");

            try {
                file.createNewFile();
            } catch (IOException e) {
                Log.d(TAG, "Failed to create " + filename);
                e.printStackTrace();
                Toast.makeText(mContext, R.string.csv_export_failure, Toast.LENGTH_SHORT).show();
                return;
            }
            Log.d(TAG, filename + "created: " + file.exists());
        }

        // Attempt to write to the file
        try (OutputStreamWriter fileWriter = new OutputStreamWriter(new FileOutputStream(file),
                StandardCharsets.UTF_8)) {

            ScoringLab lab = new ScoringLab(mContext);
            List<Score> scores = lab.getScores();

            // add column titles
            fileWriter.append("date, sleepScore, movementScore, imaginationScore, " +
                    "laughterScore, eatingScore, speakingScore\n");

            // Write every score
            for (Score s : scores) {
                Log.d(TAG, "Writing a line");
                fileWriter.append(s.scoreCSVFormat() + "\n");
            }
            fileWriter.close();

            // This makes the file available for viewing for the user ASAP
            // Taken from android site
            MediaScannerConnection.scanFile(mContext,
                    new String[]{file.toString()}, null,
                    new MediaScannerConnection.OnScanCompletedListener() {
                        @Override
                        public void onScanCompleted(String path, Uri uri) {
                            Log.i(TAG, "Scanned " + path + ":");
                            Log.i(TAG, "-> uri=" + uri);
                        }
                    }
            );

            Toast.makeText(mContext, R.string.csv_export_success, Toast.LENGTH_SHORT).show();

        } catch (IOException e) {
            e.printStackTrace();
            Log.w(TAG, "Error writing " + file, e);

            Toast.makeText(mContext, R.string.csv_export_failure, Toast.LENGTH_SHORT).show();
        }

    }

/*----IMPORT FUNCTIONALITY------------------------------------------------------------------------*/

    public void readCSVFile () {
        List<String> names = readDocuments();
        Log.d(TAG, names.toString());
    };

    /**
     * readDocuments retrieves a list of all the csv filenames of
     * the files in the phones Documents folder
     * @return
     */
    private List<String> readDocuments () {
        File documentsPath = getPublicStorageDir();
        List<String> filenames = new LinkedList<>();
        if (documentsPath.exists()) {
            File[] files = documentsPath.listFiles();
            for (int i = 0; i < files.length; i ++) {
                if (files[i].canRead()) {
                    filenames.add(files[i].getName());
                }
            }
        } else {
            Log.d(TAG, "The documents folder doesn't exist");
        }
        return filenames;
    }

}
