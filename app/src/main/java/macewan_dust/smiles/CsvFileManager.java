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
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class CsvFileManager {

    public static final String TAG = "CsvFileManager";

    private static final String RAW_COLUMNS = ",sleep1_time,sleep2_interruptions,movement1_aerobic," +
            "movement2_bone_and_muscle,movement3_relaxation,imagination1_mindfulness," +
            "imagination2_meditation,imagination3_creativity,laughter1_rating," +
            "eating1_vegetables,eating2_grains,eating3_protein,eating4_sodium," +
            "eating5_sugar,eating6_fat,eating7_water,speaking1_rating," +
            "speaking2_debrief,speaking3_prevented,speaking4_social_media," +
            "speaking5_negative_impact";

    private static final String SCORE_COLUMNS = "date,sleepScore,movementScore,imaginationScore," +
            "laughterScore,eatingScore,speakingScore";

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
        return "SMILES_" + formatted + ".csv";
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
    public static void exportData(Context context, Uri uri) {
        // referenced: https://www.techotopia.com/index.php/An_Android_Storage_Access_Framework_Example
        if (!isExternalStorageWritable()) {
            Toast.makeText(context, R.string.csv_export_failure, Toast.LENGTH_SHORT).show();
            Log.i(TAG, "External storage is not writable, cannot export.");
            return;
        }
        try {
            ParcelFileDescriptor pfd = context.getContentResolver()
                    .openFileDescriptor(uri, "w");
            OutputStreamWriter fileWriter = new OutputStreamWriter(
                    new FileOutputStream(pfd.getFileDescriptor()), StandardCharsets.UTF_8);

            mScoringLab = ScoringLab.get(context);

            // Retrieve data and ensure they're sorted by date
            List<Raw> raws = mScoringLab.getRaws();
            Collections.sort(raws);
            List<Score> scores = mScoringLab.getScores();
            Collections.sort(scores);

            Log.d(TAG, raws.toString());
            Log.d(TAG, scores.toString());

            // Sanity check
            if (raws.size() != scores.size()) {
                throw new RuntimeException();
            }

            // add column titles
            fileWriter.append(SCORE_COLUMNS + RAW_COLUMNS + "\n");

            // Write the data to the file
            for (int i = 0; i < raws.size(); i ++) {
                Score score = scores.get(i);
                Raw raw = raws.get(i);  // *
                String line = score.scoreCSVFormat() + raw.rawCSVFormat();
                fileWriter.append(line + "\n");
                Log.d(TAG, "ADDED" + line);
            }

            // Ensure files can be viewed ASAP
            runMediaScanner(context, uri.toString());

            Toast.makeText(context, R.string.csv_export_success, Toast.LENGTH_SHORT).show();

            fileWriter.close();
            pfd.close();
        } catch (IOException e) {
            e.printStackTrace();
            Log.w(TAG, "Error writing " + uri, e);
            Toast.makeText(context, R.string.csv_export_failure, Toast.LENGTH_SHORT).show();
        } catch (RuntimeException r) {
            r.printStackTrace();
            Log.w(TAG, "Error retrieving correct scores and raw responses " + uri, r);
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
        if (!(SCORE_COLUMNS + RAW_COLUMNS).equals(line)) {
            Log.d(TAG, "Columns:" + line);
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
    public static void importData(Context context, Uri uri) {
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

            List<Score> scores = new LinkedList<>();
            List<Raw> raws = new LinkedList<>();

            // Parse CSV data into score and raw objects
            for(String[] row: rows) {
                // Create a raw object
                Log.d(TAG, row[0]);
                Date date = formatter.parse((row[0]).replace("\"", ""));
                Score scoreSet = new Score(date);
                Raw responseSet = new Raw(date);

                scoreSet.setSleepScore(Integer.parseInt(row[1]));
                scoreSet.setMovementScore(Integer.parseInt(row[2]));
                scoreSet.setImaginationScore(Integer.parseInt(row[3]));
                scoreSet.setLaughterScore(Integer.parseInt(row[4]));
                scoreSet.setEatingScore(Integer.parseInt(row[5]));
                scoreSet.setSpeakingScore(Integer.parseInt(row[6]));

                responseSet.setSleep(
                        Integer.parseInt(row[7]),
                        Integer.parseInt(row[8]));
                responseSet.setMovement(
                        Integer.parseInt(row[9]),
                        Integer.parseInt(row[10]),
                        Integer.parseInt(row[11]));
                responseSet.setImagination(
                        Integer.parseInt(row[12]),
                        Integer.parseInt(row[13]),
                        Integer.parseInt(row[14]));
                responseSet.setLaughter(
                        Integer.parseInt(row[15]));
                responseSet.setEating(
                        Integer.parseInt(row[16]),
                        Integer.parseInt(row[17]),
                        Integer.parseInt(row[18]),
                        Boolean.parseBoolean(row[19]),
                        Boolean.parseBoolean(row[20]),
                        Boolean.parseBoolean(row[21]),
                        Boolean.parseBoolean(row[22]));
                responseSet.setSpeaking(
                        Integer.parseInt(row[23]),
                        Boolean.parseBoolean(row[24]),
                        Boolean.parseBoolean(row[25]),
                        Boolean.parseBoolean(row[26]),
                        Boolean.parseBoolean(row[27]));

                scores.add(scoreSet);
                raws.add(responseSet);
            }

            // Don't add raw & scores unless we're certain we were given good data
            mScoringLab = ScoringLab.get(context);
            Iterator rawIterator = raws.iterator();
            while (rawIterator.hasNext()) {
                Raw raw = (Raw)rawIterator.next();
                mScoringLab.deleteRaw(raw.getDate());
                mScoringLab.addRaw(raw);
            }

            Iterator scoreIterator = scores.iterator();
            while(scoreIterator.hasNext()) {
                Score score = (Score)scoreIterator.next();
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
