package macewan_dust.smiles;

import android.content.Context;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class CsvFileManager {

    public static final String TAG = "CsvFileManager";

    private static final String RAW_COLUMNS = "date,sleep1_time,sleep2_interruptions,movement1_aerobic," +
            "movement2_bone_and_muscle,movement3_relaxation,imagination1_mindfulness," +
            "imagination2_meditation,imagination3_creativity,laughter1_rating," +
            "eating1_vegetables,eating2_grains,eating3_protein,eating4_sodium," +
            "eating5_sugar,eating6_fat,eating7_water,speaking1_rating," +
            "speaking2_debrief,speaking3_prevented,speaking4_social_media," +
            "speaking5_negative_impact";

    private static final String SCORE_COLUMNS = "date,sleepScore,movementScore,imaginationScore," +
            "laughterScore,eatingScore,speakingScore";

    private static final int READ_SCORE_CODE = 1;
    private static final int READ_RESPONSE_CODE = 2;
    private static ScoringLab mScoringLab;

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
     * @param context
     * @param uri
     */
    public static void writeResponsesFile(Context context, Uri uri) {
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

            mScoringLab = ScoringLab.get(context);
            //ScoringLab lab = new ScoringLab(context);
            List<Raw> responses = mScoringLab.getRaws();

            // add column titles
            fileWriter.append(RAW_COLUMNS + "\n");

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
    public static void writeScoresFile(Context context, Uri uri) {
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

            mScoringLab = ScoringLab.get(context);
            //ScoringLab lab = new ScoringLab(context);
            List<Score> scores = mScoringLab.getScores();

            // add column titles
            fileWriter.append(SCORE_COLUMNS + "\n");

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
     * readTextFromUri retrieves the string contents of the file pointed to by the provided URI
     * source: https://developer.android.com/guide/topics/providers/document-provider#java
     * @param context
     * @param uri
     * @return Row List
     * @throws IOException
     */
    private static List<String[]> readTextFromUri(Context context, Uri uri, int readCode)
            throws IOException {
        InputStream inputStream = context.getContentResolver().openInputStream(uri);
        BufferedReader reader = new BufferedReader(new InputStreamReader(
                inputStream));
        List<String[]> rows = new LinkedList<>();

        // check if we have the correct columns
        String line = reader.readLine();
        String compareString = "";
        if (readCode == READ_RESPONSE_CODE) {
            compareString = RAW_COLUMNS;
        } else {
            compareString = SCORE_COLUMNS;
        }

        if (!compareString.equals(line)) {
            Log.d(TAG, line);
            throw new IOException("Invalid columns");
        }

        while ((line = reader.readLine()) != null) {
            String[] row = line.split(",");
            rows.add(row);
            Log.d(TAG, line);
        }

        reader.close();
        inputStream.close();
        return rows;
    }

    /**
     * referenced: https://developer.android.com/guide/topics/providers/document-provider#java
     * @param context
     * @param uri
     */
    public static void importResponsesFile(Context context, Uri uri) {
        List<String[]> rows;
        try {
            rows = readTextFromUri(context, uri, READ_RESPONSE_CODE);
        } catch (IOException e){
            e.printStackTrace();
            Log.d(TAG, "Error importing.");
            Toast.makeText(context, R.string.csv_import_failure_file_error, Toast.LENGTH_SHORT).show();
            return;
        }
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-dd-MM");
            List<Raw> raws = new LinkedList<>();
            for(String[] row: rows) {
                // Create a raw object
                Log.d(TAG, row[0]);
                Date date = formatter.parse((row[0]).replace("\"", ""));
                Raw responseSet = new Raw(date);

                responseSet.setSleep(
                        Integer.parseInt(row[1]),
                        Integer.parseInt(row[2]));
                responseSet.setMovement(
                        Integer.parseInt(row[3]),
                        Boolean.parseBoolean(row[4]),
                        Integer.parseInt(row[5]));
                responseSet.setImagination(
                        Integer.parseInt(row[6]),
                        Integer.parseInt(row[7]),
                        Integer.parseInt(row[8]));
                responseSet.setLaughter(
                        Integer.parseInt(row[9]));
                responseSet.setEating(
                        Integer.parseInt(row[10]),
                        Integer.parseInt(row[11]),
                        Integer.parseInt(row[12]),
                        Boolean.parseBoolean(row[13]),
                        Boolean.parseBoolean(row[14]),
                        Boolean.parseBoolean(row[15]),
                        Boolean.parseBoolean(row[16]));
                responseSet.setSpeaking(
                        Integer.parseInt(row[17]),
                        Boolean.parseBoolean(row[18]),
                        Boolean.parseBoolean(row[19]),
                        Boolean.parseBoolean(row[20]),
                        Boolean.parseBoolean(row[21]));

                raws.add(responseSet);
            }

            // Don't add raw scores unless we're certain we were given good data
            mScoringLab = ScoringLab.get(context);
            //ScoringLab lab = new ScoringLab(context);
            Iterator iterator = raws.iterator();
            while (iterator.hasNext()) {
                Raw raw = (Raw)iterator.next();
                mScoringLab.deleteRaw(raw.getDate());
                mScoringLab.addRaw(raw);
            }

            Toast.makeText(context, R.string.csv_import_success, Toast.LENGTH_SHORT).show();
        } catch (ParseException e) {
            Log.d(TAG, "Cannot parse date");
            Toast.makeText(context, R.string.csv_import_failure_file_error, Toast.LENGTH_SHORT).show();
            return;
        }
    }

    /**
     * importScoresFile imports the scores data included in the file pointed to by the uri
     * @param context
     * @param uri
     */
    public static void importScoresFile(Context context, Uri uri) {
        List<String[]> rows;
        try {
            rows = readTextFromUri(context, uri, READ_SCORE_CODE);
        } catch (IOException e){
            e.printStackTrace();
            Log.d(TAG, "Error importing.");
            Toast.makeText(context, R.string.csv_import_failure_system_error, Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-dd-MM");
            List<Score> scores = new LinkedList<>();
            for (String[] row : rows) {
                Date date = formatter.parse((row[0]).replace("\"", ""));
                Score scoreSet = new Score(date);

                scoreSet.setSleepScore(Integer.parseInt(row[1]));
                scoreSet.setMovementScore(Integer.parseInt(row[2]));
                scoreSet.setImaginationScore(Integer.parseInt(row[3]));
                scoreSet.setLaughterScore(Integer.parseInt(row[4]));
                scoreSet.setEatingScore(Integer.parseInt(row[5]));
                scoreSet.setSpeakingScore(Integer.parseInt(row[6]));

                scores.add(scoreSet);
                Log.d(TAG, scoreSet.toString());
            }
            mScoringLab = ScoringLab.get(context);
            //ScoringLab lab = new ScoringLab(context);
            Iterator iterator = scores.iterator();
            while (iterator.hasNext()) {
                Score score = (Score)iterator.next();
                mScoringLab.deleteScore(score.getDate());
                mScoringLab.addScore(score);
            }

            Toast.makeText(context, R.string.csv_import_success, Toast.LENGTH_SHORT).show();
        } catch (ParseException e) {
            Log.d(TAG, "Cannot parse date");
            Toast.makeText(context, R.string.csv_import_failure_file_error, Toast.LENGTH_SHORT).show();
            return;
        }

    }
}
