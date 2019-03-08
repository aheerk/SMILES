package macewan_dust.smiles.database;

import android.database.Cursor;
import android.database.CursorWrapper;

import macewan_dust.smiles.Score;

import java.util.Date;
import java.util.UUID;

/**
 * Reference: https://developer.android.com/reference/java/util/Date
 */
public class SMILES_CursorWrapper extends CursorWrapper {

    private static final String TAG = "Dabase CursorWrapper";

    public SMILES_CursorWrapper(Cursor cursor) {
        super(cursor);
    }

    /**
     * get Score - unpacks cursor data and returns it in a new question object
     *
     * @return question object
     */
    public Score getScoreFromDB() {
        String uuidString = getString(getColumnIndex(SMILES_DatabaseSchema.ScoreTable.Columns.SCORE_ID));
        int sleepScore = getInt(getColumnIndex(SMILES_DatabaseSchema.ScoreTable.Columns.SLEEP));
        int movementScore = getInt(getColumnIndex(SMILES_DatabaseSchema.ScoreTable.Columns.MOVEMENT));
        int imaginationScore = getInt(getColumnIndex(SMILES_DatabaseSchema.ScoreTable.Columns.IMAGINATION));
        int laughterScore = getInt(getColumnIndex(SMILES_DatabaseSchema.ScoreTable.Columns.LAUGHTER));
        int eatingScore = getInt(getColumnIndex(SMILES_DatabaseSchema.ScoreTable.Columns.EATING));
        int speakingScore = getInt(getColumnIndex(SMILES_DatabaseSchema.ScoreTable.Columns.SPEAKING));
        long dateScore = getLong(getColumnIndex(SMILES_DatabaseSchema.ScoreTable.Columns.DATE));

        Score scoreFromDB = new Score(UUID.fromString(uuidString)); // new score object with old SCORE_ID
  //      Log.d(TAG, "Date Long: " + dateScore);
        scoreFromDB.setDate(new Date(dateScore));
        //scoreFromDB.setDate(dateScore);

        //      Log.d(TAG, "Date Date: " + scoreFromDB.getDateString());

        scoreFromDB.setSleepScore(sleepScore);
        scoreFromDB.setMovementScore(movementScore);
        scoreFromDB.setImaginationScore(imaginationScore);
        scoreFromDB.setLaughterScore(laughterScore);
        scoreFromDB.setEatingScore(eatingScore);
        scoreFromDB.setSpeakingScore(speakingScore);

        return scoreFromDB;
    }

}
