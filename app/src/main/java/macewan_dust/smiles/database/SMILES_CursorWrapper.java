package macewan_dust.smiles.database;

import android.database.Cursor;
import android.database.CursorWrapper;

import macewan_dust.smiles.Score;

import java.text.DateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * Reference: https://developer.android.com/reference/java/util/Date
 *
 */
public class SMILES_CursorWrapper extends CursorWrapper {

    public SMILES_CursorWrapper(Cursor cursor) {
        super(cursor);
    }

    /**
     * get Score - unpacks cursor data and returns it in a new question object
     *
     * @return question object
     */
    public Score getScoreFromDB() {
        String uuidString = getString(getColumnIndex(SMILES_DatabaseSchema.ScoreTable.Columns.UUID));
        int sleepScore = getInt(getColumnIndex(SMILES_DatabaseSchema.ScoreTable.Columns.SLEEP));
        int movementScore = getInt(getColumnIndex(SMILES_DatabaseSchema.ScoreTable.Columns.MOVEMENT));
        int imaginationScore = getInt(getColumnIndex(SMILES_DatabaseSchema.ScoreTable.Columns.IMAGINATION));
        int laughterScore = getInt(getColumnIndex(SMILES_DatabaseSchema.ScoreTable.Columns.LAUGHTER));
        int eatingScore = getInt(getColumnIndex(SMILES_DatabaseSchema.ScoreTable.Columns.EATING));
        int speakingScore = getInt(getColumnIndex(SMILES_DatabaseSchema.ScoreTable.Columns.SPEAKING));

  //      String tempDate = getString(getColumnIndex(SMILES_DatabaseSchema.ScoreTable.Columns.DATE));
  //      Date date = DateFormat.parse(tempDate);

        Score scoreFromDB = new Score(UUID.fromString(uuidString));
   //     scoreFromDB.setDate(date);
        scoreFromDB.setSleepScore(sleepScore);
        scoreFromDB.setMovementScore(movementScore);
        scoreFromDB.setImaginationScore(imaginationScore);
        scoreFromDB.setLaughterScore(laughterScore);
        scoreFromDB.setEatingScore(eatingScore);
        scoreFromDB.setSpeakingScore(speakingScore);

        return scoreFromDB;
    }


}
