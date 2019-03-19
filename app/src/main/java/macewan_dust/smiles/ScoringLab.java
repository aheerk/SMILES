package macewan_dust.smiles;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import macewan_dust.smiles.database.SMILES_CursorWrapper;
import macewan_dust.smiles.database.SMILES_DatabaseHelper;
import macewan_dust.smiles.database.SMILES_DatabaseSchema;


public class ScoringLab {

    public static final String TAG = "ScoringLab";
    private static ScoringLab sScoringLab;

    // for database
    private Context mContext;
    private SQLiteDatabase mDatabase;
    private List<Score> mScoresList;
    private List<Raw> mRawList;

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
        mRawList = loadRawFromDB();

        sortScoresList();

        Log.d(TAG, "database setup");
    }

    // creates one and only one scoring lab
    public static ScoringLab get(Context context) {
        if (sScoringLab == null) {
            sScoringLab = new ScoringLab(context);
        }
        return sScoringLab;
    }

    public void sortScoresList() {
        Collections.sort(mScoresList, new SortScoresByDate());
    }

    class SortScoresByDate implements Comparator<Score> {

        @Override
        public int compare(Score o1, Score o2) {
            return o2.getDate().compareTo(o1.getDate()); // Data comparator method
        }
    }

    public Date getOldestDate(){
        if (!mScoresList.isEmpty())
            return mScoresList.get(mScoresList.size()-1).getDate();
        else
            return null;
    }

    /**
     * only use this before csv. its not necessary otherwise because raw data is not displayed in a list
     */
    public void sortRawsList() {
        Collections.sort(mRawList, new SortRawByDate());
    }

    class SortRawByDate implements Comparator<Raw> {

        @Override
        public int compare(Raw o1, Raw o2) {
            return o2.getDate().compareTo(o1.getDate()); // Data comparator method
        }
    }

    // ------------------------------ Database methods ------------------------------ //

    /**
     * loadScoresFromDB - pulls all scores out of the database
     *
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
     *
     * @return
     */
    public List<Score> getScores() {
        return mScoresList;
    }

    /**
     * getScore - gets a score by date
     *
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
     * returns all scores in a given month
     * @param monthShort
     * @param year
     * @return
     */
    public List<Score> monthScores(String monthShort, String year) {

        List<Score> monthList = new LinkedList<>();
        String tempDate;

        for (int i = 0; i < getScores().size() ; i++) {
            tempDate = mScoresList.get(i).getDateString();

            if (tempDate.contains(monthShort) && tempDate.contains(year)) {
                monthList.add(mScoresList.get(i));
            }
        }
        return monthList;
    }

    public List<Score> getTopScore() {
        List<Score> tempList = new ArrayList<>();
        if (!mScoresList.isEmpty()) {
            tempList.add(mScoresList.get(0));
        }
        return tempList;
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
        sortScoresList();
        Log.i(TAG, "number of rows inserted to score: " + temp);
    }

    /**
     * updateScore
     *
     * @param score - question object
     */
    public void updateScore(Score score) {
        String scoreIdString = score.getScoreID().toString();
        ContentValues values = getContentValues(score);

        Log.d(TAG, "updating score: " + score);

        int temp = mDatabase.update(SMILES_DatabaseSchema.ScoreTable.NAME, values,
                SMILES_DatabaseSchema.ScoreTable.Columns.SCORE_ID + " = ? ",
                new String[]{scoreIdString});
        updateScoreList(score); // replace old score with new score object, matched by ID
        Log.i(TAG, "number of rows updated: " + temp);
    }

    /**
     * update score in scoring lab list. must have the same ID
     *
     * @param newScore
     */
    private void updateScoreList(Score newScore) {
        for (int i = 0; i < mScoresList.size(); i++) {
            if (mScoresList.get(i).getScoreID() == newScore.getScoreID()) {
                mScoresList.set(i, newScore); // replace old score with new one in list
            }
        }
    }

    public void deleteScore(Date date) {
        if (this.isScore(date)) {
            deleteScore(getScore(date));
            Log.d(TAG, "score deleted by date");
        }
    }

    /**
     * delete score from score table database
     *
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
     *
     * @param date - date excluding time
     * @return - true if score exists
     */
    public boolean isScore(Date date) {

        // list method
        String stringDate = Score.timelessDate(date);

        for (int i = 0; i < mScoresList.size(); i++) {
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
     *
     * @param date - date excluding time
     * @return RAW_ID unique identifier for scores
     */
//    public RAW_ID getScoreID(String date) {
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


    // ------------------------------ RAW Database methods ------------------------------ //

    /**
     * loadScoresFromDB - pulls all scores out of the database
     *
     * @return scores list (not sorted)
     */
    public List<Raw> loadRawFromDB() {
        List<Raw> Raws = new ArrayList<>();

        SMILES_CursorWrapper cursor = queryRaws(null, null); // gets all of the database

        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                Raw tempRaw = cursor.getRawFromDB();
                Raws.add(tempRaw);

                Log.d(TAG, "Date: " + tempRaw.getDateString());

                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }
        return Raws;
    }


    private SMILES_CursorWrapper queryRaws(String whereClause, String[] whereArgs) {                /// ---- check this. corsor wrapper could be broken down into 2
        Cursor cursor = mDatabase.query(
                SMILES_DatabaseSchema.RawTable.NAME,
                null,
                whereClause,
                whereArgs,
                null,
                null,
                null
        );
        return new SMILES_CursorWrapper(cursor);
    }


    public List<Raw> getRaws() {
        return mRawList;
    }


    public Raw getRaw(Date date) {

        for (Raw r : mRawList) {
            if (r.getDateString().equals(Score.timelessDate(date))) {
                return r;
            }
        }
        return null;
    }


    private static ContentValues getContentValues(Raw raw) {
        ContentValues values = new ContentValues();
        values.put(SMILES_DatabaseSchema.RawTable.Columns.RAW_ID, raw.getRawID().toString());
        values.put(SMILES_DatabaseSchema.RawTable.Columns.DATE, raw.getDate().getTime()); // long

        values.put(SMILES_DatabaseSchema.RawTable.Columns.SLEEP1, raw.getSleep1());
        values.put(SMILES_DatabaseSchema.RawTable.Columns.SLEEP2, raw.getSleep2());
        values.put(SMILES_DatabaseSchema.RawTable.Columns.MOVEMENT1, raw.getMovement1());
        values.put(SMILES_DatabaseSchema.RawTable.Columns.MOVEMENT2, raw.isMovement2() ? 1 : 0);        // convert boolean to 1 or 0
        values.put(SMILES_DatabaseSchema.RawTable.Columns.MOVEMENT3, raw.getMovement3());
        values.put(SMILES_DatabaseSchema.RawTable.Columns.IMAGINATION1, raw.getImagination1());
        values.put(SMILES_DatabaseSchema.RawTable.Columns.IMAGINATION2, raw.getImagination2());
        values.put(SMILES_DatabaseSchema.RawTable.Columns.IMAGINATION3, raw.getImagination3());
        values.put(SMILES_DatabaseSchema.RawTable.Columns.LAUGHTER1, raw.getLaughter1());
        values.put(SMILES_DatabaseSchema.RawTable.Columns.EATING1, raw.getEating1());
        values.put(SMILES_DatabaseSchema.RawTable.Columns.EATING2, raw.getEating2());
        values.put(SMILES_DatabaseSchema.RawTable.Columns.EATING3, raw.getEating3());
        values.put(SMILES_DatabaseSchema.RawTable.Columns.EATING4, raw.isEating4() ? 1 : 0);
        values.put(SMILES_DatabaseSchema.RawTable.Columns.EATING5, raw.isEating5() ? 1 : 0);
        values.put(SMILES_DatabaseSchema.RawTable.Columns.EATING6, raw.isEating6() ? 1 : 0);
        values.put(SMILES_DatabaseSchema.RawTable.Columns.EATING7, raw.isEating7() ? 1 : 0);
        values.put(SMILES_DatabaseSchema.RawTable.Columns.SPEAKING1, raw.getSpeaking1());
        values.put(SMILES_DatabaseSchema.RawTable.Columns.SPEAKING2, raw.isSpeaking2() ? 1 : 0);
        values.put(SMILES_DatabaseSchema.RawTable.Columns.SPEAKING3, raw.isSpeaking3() ? 1 : 0);
        values.put(SMILES_DatabaseSchema.RawTable.Columns.SPEAKING4, raw.isSpeaking4() ? 1 : 0);
        values.put(SMILES_DatabaseSchema.RawTable.Columns.SPEAKING5, raw.isSpeaking5() ? 1 : 0);

        Log.d(TAG, "getContentValues put raw: " + raw);
        return values;
    }

    public void addRaw(Raw raw) {
        ContentValues values = getContentValues(raw);
        long temp = mDatabase.insert(SMILES_DatabaseSchema.RawTable.NAME, null, values);
        mRawList.add(raw);
        Log.i(TAG, "number of rows inserted to raw: " + temp);
    }

    public void updateRaw(Raw raw) {
        String rawIdString = raw.getRawID().toString();
        ContentValues values = getContentValues(raw);

        Log.d(TAG, "updating raw: " + raw);

        int temp = mDatabase.update(SMILES_DatabaseSchema.RawTable.NAME, values,
                SMILES_DatabaseSchema.RawTable.Columns.RAW_ID + " = ? ",
                new String[]{rawIdString});
        updateRawList(raw); // replace old score with new score object, matched by ID
        Log.i(TAG, "number of rows updated in raw: " + temp);
    }

    private void updateRawList(Raw newRaw) {
        for (int i = 0; i < mRawList.size(); i++) {
            if (mRawList.get(i).getRawID() == newRaw.getRawID()) {
                mRawList.set(i, newRaw); // replace old score with new one in list
            }
        }
    }

    public void deleteRaw(Date date) {
        if (this.isRaw(date)) {
            deleteRaw(getRaw(date));
            Log.d(TAG, "raw deleted by date");
        }
    }

    public void deleteRaw(Raw raw) {
        String rawIdString = raw.getRawID().toString();

        Log.d(TAG, "deleting raw: " + raw);

        int temp = mDatabase.delete(SMILES_DatabaseSchema.RawTable.NAME,
                SMILES_DatabaseSchema.RawTable.Columns.RAW_ID + " = ? ",
                new String[]{rawIdString});

        mRawList.remove(raw);
        Log.i(TAG, "number of rows deleted from raw: " + temp);
    }

    public boolean isRaw(Date date) {

        // list method
        String stringDate = Score.timelessDate(date); // note Score has the timeless date method rather than raw.   ------ should move it to Scoring Lab

        for (int i = 0; i < mRawList.size(); i++) {
            if (mRawList.get(i).getDateString().equals(stringDate)) {
                return true;
            }
        }
        return false;
    }
}


