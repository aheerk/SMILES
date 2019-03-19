package macewan_dust.smiles;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class CsvFileManager {

    public static final String TAG = "CsvFileManager";

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

    /*----EXPORT FUNCTIONALITY------------------------------------------------------------------------*/

    /**
     * getFilename retrieves an appropriate filename for an exported
     * CSV file based on today's date
     * @return A string filename
     */
    public static String getFilename() {
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
    public static void writeCSVFile(Activity activity, Context context, Uri uri) {
        // referenced: https://www.techotopia.com/index.php/An_Android_Storage_Access_Framework_Example
         if (!isExternalStorageWritable()) {
             Toast.makeText(context, R.string.csv_export_failure, Toast.LENGTH_SHORT).show();
             Log.i(TAG, "External storage is not writable, cannot export.");
         }

        try {
            ParcelFileDescriptor pfd = context.getContentResolver()
                    .openFileDescriptor(uri, "w");

            OutputStreamWriter fileWriter = new OutputStreamWriter(
                    new FileOutputStream(pfd.getFileDescriptor()), StandardCharsets.UTF_8);

            ScoringLab lab = new ScoringLab(context);
            List<Score> scores = lab.getScores();

            // add column titles
            fileWriter.append("date, sleepScore, movementScore, imaginationScore, " +
                    "laughterScore, eatingScore, speakingScore\n");

            // Write every score
            for (Score s : scores) {
                Log.d(TAG, "Writing a line");
                fileWriter.append(s.scoreCSVFormat() + "\n");
            }

            // This makes the file available for viewing for the user ASAP
            // Taken from android site
            MediaScannerConnection.scanFile(context,
                    new String[]{uri.toString()}, null,
                    new MediaScannerConnection.OnScanCompletedListener() {
                        @Override
                        public void onScanCompleted(String path, Uri uri) {
                            Log.i(TAG, "Scanned " + path + ":");
                            Log.i(TAG, "-> uri=" + uri);
                        }
                    }
            );

            Toast.makeText(context, R.string.csv_export_success, Toast.LENGTH_SHORT).show();

            fileWriter.close();
            pfd.close();

        } catch (IOException e) {
            e.printStackTrace();
            Log.w(TAG, "Error writing " + uri, e);
            Toast.makeText(context, R.string.csv_export_failure, Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * importCSVFile reads data from the user's chosen CSV file and imports it into the database
     * @param activity
     * @param context
     * @param uri
     */
    public static void importCSVFile(MainActivity activity, Context context, Uri uri) {

        try {
            String fileText = readTextFromUri(context, uri);
            Log.d(TAG, "READY FOR IMPORT! " + fileText);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*----IMPORT FUNCTIONALITY------------------------------------------------------------------------*/

    /**
     * readTextFromUri retrieves the string contents of the file pointed to by the provided URI
     * source: https://developer.android.com/guide/topics/providers/document-provider#java
     * @param context
     * @param uri
     * @return
     * @throws IOException
     */
    private static String readTextFromUri(Context context, Uri uri) throws IOException {
        InputStream inputStream = context.getContentResolver().openInputStream(uri);
        BufferedReader reader = new BufferedReader(new InputStreamReader(
                inputStream));
        StringBuilder stringBuilder = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            stringBuilder.append(line);
        }

        reader.close();
        inputStream.close();
        return stringBuilder.toString();
    }

}
