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
    public static final int WRITE_SCORES = 1;
    public static final int WRITE_RESPONSES = 1;

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
        return formatted + ".csv";
    }

    /**
     * This makes the file available for viewing for the user ASAP. Taken from android site
     * @param context
     * @param filepath
     */
    private static void runMediaScanner(Context context, String filepath) {
        MediaScannerConnection.scanFile(context,
                new String[]{filepath}, null,
                new MediaScannerConnection.OnScanCompletedListener() {
                    @Override
                    public void onScanCompleted(String path, Uri uri) {
                        Log.i(TAG, "Scanned " + path + ":");
                        Log.i(TAG, "-> uri=" + uri);
                    }
                }
        );
    }

    /**
     * writeResponsesFile writes raw inputs to the file pointed to by the provided uri
     * @param activity
     * @param context
     * @param uri
     */
    public static void writeResponsesFile(Activity activity, Context context, Uri uri) {
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
            List<Raw> responses = lab.getRaws();

            // add column titles
            fileWriter.append("date, sleep1_time, sleep2_interruptions, movement1_aerobic, " +
                    "movement2_bone_and_muscle, movement3_relaxation, imagination1_mindfulness, " +
                    "imagination2_meditation, imagination3_creativity, laughter1_rating, " +
                    "eating1_vegetables, eating2_grains, eating3_protein, eating4_sodium," +
                    "eating5_sugar, eating6_fat, eating7_water, speaking1_rating, " +
                    "speaking2_debrief, speaking3_prevented, speaking4_social_media," +
                    "speaking5_negative_impact \n");

            // Write every score
            for (Raw r : responses) {
                Log.d(TAG, "Writing a line");
                fileWriter.append(r.rawCSVFormat() + "\n");
            }

            runMediaScanner(context, uri.toString());

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
     * writeScoresFile writes scores to the file pointed to by the provided uri
     *
     * Referenced https://stackoverflow.com/questions/31063216/filenotfoundexception-storage-emulated-0-android
     * https://stackoverflow.com/questions/35132693/set-encoding-as-utf-8-for-a-filewriter
     */
    public static void writeScoresFile(Activity activity, Context context, Uri uri) {
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

            runMediaScanner(context, uri.toString());

            Toast.makeText(context, R.string.csv_export_success, Toast.LENGTH_SHORT).show();

            fileWriter.close();
            pfd.close();

        } catch (IOException e) {
            e.printStackTrace();
            Log.w(TAG, "Error writing " + uri, e);
            Toast.makeText(context, R.string.csv_export_failure, Toast.LENGTH_SHORT).show();
        }
    }

    /*----IMPORT FUNCTIONALITY------------------------------------------------------------------------*/
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
