package macewan_dust.smiles;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.util.Log;

import macewan_dust.smiles.database.SMILES_DatabaseHelper;
import macewan_dust.smiles.database.SMILES_DatabaseSchema;
import macewan_dust.smiles.database.SMILES_CursorWrapper;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;


public class ScoringLab {

    public static final String TAG = "ScoringLab";
    private static ScoringLab sScoringLab;

    // for database
    private Context mContext;
    private SQLiteDatabase mDatabase;
    private List<Score> mScoresList;

    /**
     * constructor
     *
     * @param context
     */
    public ScoringLab(Context context) {
        // database
        mContext = context.getApplicationContext();
        mDatabase = new SMILES_DatabaseHelper(mContext)
                .getWritableDatabase();
        mScoresList = loadScoresFromDB();

        sortList();

        Log.d(TAG, "database setup");
    }

    // creates one and only one scoring lab
    public static ScoringLab get(Context context) {
        if (sScoringLab == null) {
            sScoringLab = new ScoringLab(context);
        }
        return sScoringLab;
    }

    public void sortList(){
        Collections.sort(mScoresList, new SortByDate());
    }

    class SortByDate implements Comparator<Score> {

        @Override
        public int compare(Score o1, Score o2) {
            return o2.getDate().compareTo(o1.getDate()); // Data comparator method
        }
    }


    // ------------------------------ Database methods ------------------------------ //

    /**
     * loadScoresFromDB - pulls all scores out of the database
     * @return scores list (not sorted)
     */
    public List<Score> loadScoresFromDB() {
        List<Score> Scores = new ArrayList<>();

        SMILES_CursorWrapper cursor = queryScores(null, null); // gets all of the database

        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                Score tempScore = cursor.getScoreFromDB();
                Scores.add(tempScore);

                Log.d(TAG, "Date: " + tempScore.getDateString());

                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }
        return Scores;
    }

    /**
     * getScores - scores saved in the scoring lab. these should be up to date with the database
     * at all times.
     * @return
     */
    public List<Score> getScores() {
        return mScoresList;
    }

    /**
     * getScore - gets a score by date
     * @param date date of score. time is ignored
     * @return score or null
     */
    public Score getScore(Date date) {

        for (Score s : mScoresList) {
            if (s.getDateString().equals(Score.timelessDate(date))) {
                return s;
            }
        }
        return null;
    }

    /**
     * queryScore - does queries to the score table, customized by the where clause and args
     * <p>
     * a cursor gives raw column values. it should be passed to a cursor wrapper to unpack it.
     * The wrapper around it converts those values.
     *
     * @param whereClause - where part of sql statement
     * @param whereArgs   - arguments passed into where statement
     * @return SMILES_CursorWrapper - raw table data
     */
    private SMILES_CursorWrapper queryScores(String whereClause, String[] whereArgs) {
        Cursor cursor = mDatabase.query(
                SMILES_DatabaseSchema.ScoreTable.NAME,
                null,
                whereClause,
                whereArgs,
                null,
                null,
                null
        );
        return new SMILES_CursorWrapper(cursor);
    }

    /**
     * getContentValues - gets data from an object and packages it for use with a database
     *
     * @param score - a score object with data to save
     * @return values - content values to unpack
     */
    private static ContentValues getContentValues(Score score) {
        ContentValues values = new ContentValues();
        values.put(SMILES_DatabaseSchema.ScoreTable.Columns.SCORE_ID, score.getScoreID().toString());
        // convert to long to be compatible with database which has no Date type
       // long tempDate = score.getDate().getTime(); // converts data to long
       // values.put(SMILES_DatabaseSchema.ScoreTable.Columns.DATE, score.getDateString());
        values.put(SMILES_DatabaseSchema.ScoreTable.Columns.DATE, score.getDate().getTime()); // long
        values.put(SMILES_DatabaseSchema.ScoreTable.Columns.SLEEP, score.getSleepScore());
        values.put(SMILES_DatabaseSchema.ScoreTable.Columns.MOVEMENT, score.getMovementScore());
        values.put(SMILES_DatabaseSchema.ScoreTable.Columns.IMAGINATION, score.getImaginationScore());
        values.put(SMILES_DatabaseSchema.ScoreTable.Columns.LAUGHTER, score.getLaughterScore());
        values.put(SMILES_DatabaseSchema.ScoreTable.Columns.EATING, score.getEatingScore());
        values.put(SMILES_DatabaseSchema.ScoreTable.Columns.SPEAKING, score.getSpeakingScore());
        Log.d(TAG, "getContentValues put score: " + score);
        return values;
    }

    /**
     * addScore - adds score to the database
     *
     * @param score - question object
     */
    public void addScore(Score score) {
        ContentValues values = getContentValues(score);
        long temp = mDatabase.insert(SMILES_DatabaseSchema.ScoreTable.NAME, null, values);
        mScoresList.add(score);
        sortList();
        Log.i(TAG, "number of rows inserted: " + temp);
    }

    /**
     * updateScore
     *
     * @param score - question object
     */
    public void updateScore(Score score) {
        String scoreIdString = score.getScoreID().toString();
        ContentValues values = getContentValues(score);

        Log.d(TAG, "ScoringAlgorithms updating score: " + score);

        int temp = mDatabase.update(SMILES_DatabaseSchema.ScoreTable.NAME, values,
                SMILES_DatabaseSchema.ScoreTable.Columns.SCORE_ID + " = ? ",
                new String[]{scoreIdString});
        updateScoreList(score); // replace old score with new score object, matched by ID
        Log.i(TAG, "number of rows updated: " + temp);
    }

    /**
     * update score in scoring lab list. must have the same ID
     * @param newScore
     */
    private void updateScoreList(Score newScore){
        for (int i = 0 ; i < mScoresList.size() ; i++ ) {
            if (mScoresList.get(i).getScoreID() == newScore.getScoreID()) {
                mScoresList.set(i, newScore); // replace old score with new one in list
            }
        }
    }

    /**
     * delete score from score table database
     * @param score - score to delete
     */
    public void deleteScore(Score score) {
        String scoreIdString = score.getScoreID().toString();
       // String dateString = String.valueOf(score.getDate().getTime());

      //  Log.d(TAG, "delete by date: " + dateString);

        Log.d(TAG, "ScoringAlgorithms deleting score: " + score);


        int temp = mDatabase.delete(SMILES_DatabaseSchema.ScoreTable.NAME,
                SMILES_DatabaseSchema.ScoreTable.Columns.SCORE_ID + " = ? ",
                new String[]{scoreIdString});

     //   int temp = mDatabase.delete(SMILES_DatabaseSchema.ScoreTable.NAME,
       //         SMILES_DatabaseSchema.ScoreTable.Columns.DATE + " = ? ",
         //       new String[]{dateString});


        mScoresList.remove(score);
        Log.i(TAG, "number of rows deleted: " + temp);
    }

    /**
     * checks to see if a score exists for today
     * @param date - date excluding time
     * @return - true if score exists
     */
    public boolean isScore(Date date) {

        // list method
        String stringDate = Score.timelessDate(date);

        for (int i = 0 ; i < mScoresList.size() ; i++ ) {
            if (mScoresList.get(i).getDateString().equals(stringDate)) {
                return true;
            }
        }
        return false;

        // database method
        /*
        boolean dateFound = false;

        SMILES_CursorWrapper cursor = queryScores(
                SMILES_DatabaseSchema.ScoreTable.Columns.DATE + " = ?",
                new String[]{tempDate}
        );

        try {
            if (cursor.getCount() > 0) {
                dateFound = true;
                Log.d(TAG, "date: " + date + " in database: " + dateFound);
            }

        } finally {
            cursor.close();
        }
                return dateFound;
        */
    }

    /**
     * get score by id by its date
     * @param date - date excluding time
     * @return SCORE_ID unique identiefier for scores
     */
//    public SCORE_ID getScoreID(String date) {
//
//        // list method
//     //   String stringDate = Score.timelessDate(date);
//
//        for (int i = 0 ; i < mScoresList.size() ; i++ ) {
//            if (mScoresList.get(i).getDateString().equals(date)) {
//                return mScoresList.get(i).getScoreID();
//            }
//        }
//        return null;
//
//        /*
//        boolean dateFound = false;
//
//        SMILES_CursorWrapper cursor = queryScores(
//                SMILES_DatabaseSchema.ScoreTable.Columns.DATE + " = ?",
//                new String[]{date}
//        );
//
//        try {
//            if (cursor.getCount() == 0) {
//                return null;
//            }
//
//            cursor.moveToFirst(); // first item in results
//            Log.d(TAG, "date: " + date + " has ID: " + cursor.getScoreFromDB().getScoreID());
//
//            return cursor.getScoreFromDB().getScoreID();
//
//
//        } finally {
//            cursor.close();
//        }
//        */
//    }

    public static void writeCSVFile(){




        // write to file
        String filepath = Environment.getExternalStorageDirectory() + File.separator + "testCSV.txt";
        FileOutputStream fileOutputStream = null;

        Log.d(TAG, "CSV file path: " + filepath);

        try {

            fileOutputStream = new FileOutputStream(filepath);
            byte[] buffer = "test string".getBytes();
            fileOutputStream.write(buffer, 0, buffer.length);
            fileOutputStream.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            {
                if (fileOutputStream != null) {
                    try {
                        fileOutputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }







    //    for (Score s : mScoresList){


    //    }


    }
}
