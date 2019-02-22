package macewan_dust.smiles;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import macewan_dust.smiles.database.SMILES_DatabaseHelper;
import macewan_dust.smiles.database.SMILES_DatabaseSchema;
import macewan_dust.smiles.database.SMILES_CursorWrapper;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;


public class ScoringLab {

    public static final String TAG = "ScoringLab";

    private static ScoringLab sScoringLab;

    // for database
    private Context mContext;
    private SQLiteDatabase mDatabase;

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
        Log.d(TAG, "database setup");
    }

    // creates one and only one scoring lab
    public static ScoringLab get(Context context) {
        if (sScoringLab == null) {
            sScoringLab = new ScoringLab(context);
        }
        return sScoringLab;
    }


    // ------------------------------ Database methods ------------------------------ //

    public List<Score> getScores() {
        List<Score> Scores = new ArrayList<>();

        SMILES_CursorWrapper cursor = queryScores(null, null); // gets all of the database

        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                Score tempScore = cursor.getScoreFromDB();
                Scores.add(tempScore);

                Log.d(TAG, "Date: " + tempScore.getDate());

                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }
        return Scores;
    }

    /**
     * gets one score from the Score Table database by UUID
     *
     * @param id - UUID of score object
     * @return Score - Score object
     */
    public Score getScore(UUID id) {

        SMILES_CursorWrapper cursor = queryScores(
                SMILES_DatabaseSchema.ScoreTable.Columns.UUID + " = ?",
                new String[]{id.toString()}
        );

        try {
            if (cursor.getCount() == 0) {
                return null;
            }

            cursor.moveToFirst();
            return cursor.getScoreFromDB();

        } finally {
            cursor.close();
        }
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
        values.put(SMILES_DatabaseSchema.ScoreTable.Columns.UUID, score.getID().toString());


        // convert to long to be compatible with database which has no Date type
     //   long tempDate = score.getDate().getTime(); // converts data to long

        values.put(SMILES_DatabaseSchema.ScoreTable.Columns.DATE, score.getDate());
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
        Log.i(TAG, "number of rows inserted: " + temp);
    }

    /**
     * updateScore
     *
     * @param score - question object
     */
    public void updateScore(Score score) {
        String uuidString = score.getID().toString();
        ContentValues values = getContentValues(score);

        Log.d(TAG, "ScoringAlgorithms updating score: " + score);

        int temp = mDatabase.update(SMILES_DatabaseSchema.ScoreTable.NAME, values,
                SMILES_DatabaseSchema.ScoreTable.Columns.UUID + " = ? ",
                new String[]{uuidString});
        Log.i(TAG, "number of rows updated: " + temp);
    }


    /**
     * delete score from score table database
     * @param score - score to delete
     */
    public void deleteScore(Score score) {
        String uuidString = score.getID().toString();

        Log.d(TAG, "ScoringAlgorithms deleting score: " + score);

        int temp = mDatabase.delete(SMILES_DatabaseSchema.ScoreTable.NAME,
                SMILES_DatabaseSchema.ScoreTable.Columns.UUID + " = ? ",
                new String[]{uuidString});
        Log.i(TAG, "number of rows deleted: " + temp);
    }

    /**
     * checks to see if a score exists for today
     * @param date - date excluding time
     * @return - true if score exists
     */
    public boolean isScore(Date date) {

        String tempDate = Score.timelessDate(date);
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
    }

    /**
     * get score by id by its date
     * @param date - date excluding time
     * @return UUID unique identiefier for scores
     */
    public UUID getScoreID(String date) {
        boolean dateFound = false;

        SMILES_CursorWrapper cursor = queryScores(
                SMILES_DatabaseSchema.ScoreTable.Columns.DATE + " = ?",
                new String[]{date}
        );

        try {
            if (cursor.getCount() == 0) {
                return null;
            }

            cursor.moveToFirst(); // first item in results
            Log.d(TAG, "date: " + date + " has ID: " + cursor.getScoreFromDB().getID());

            return cursor.getScoreFromDB().getID();


        } finally {
            cursor.close();
        }
    }
}
