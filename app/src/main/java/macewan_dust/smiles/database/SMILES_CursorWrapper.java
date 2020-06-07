package macewan_dust.smiles.database;

import android.database.Cursor;
import android.database.CursorWrapper;
import macewan_dust.smiles.Score;
import macewan_dust.smiles.Raw;
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
        String scoreIDString = getString(getColumnIndex(SMILES_DatabaseSchema.ScoreTable.Columns.SCORE_ID));
        int sleepScore = getInt(getColumnIndex(SMILES_DatabaseSchema.ScoreTable.Columns.SLEEP));
        int movementScore = getInt(getColumnIndex(SMILES_DatabaseSchema.ScoreTable.Columns.MOVEMENT));
        int imaginationScore = getInt(getColumnIndex(SMILES_DatabaseSchema.ScoreTable.Columns.IMAGINATION));
        int lifeSatisfactionScore = getInt(getColumnIndex(SMILES_DatabaseSchema.ScoreTable.Columns.LIFE_SATISFACTION));
        int eatingScore = getInt(getColumnIndex(SMILES_DatabaseSchema.ScoreTable.Columns.EATING));
        int speakingScore = getInt(getColumnIndex(SMILES_DatabaseSchema.ScoreTable.Columns.SPEAKING));
        long dateScore = getLong(getColumnIndex(SMILES_DatabaseSchema.ScoreTable.Columns.DATE));

        Score scoreFromDB = new Score(UUID.fromString(scoreIDString)); // new score object with old RAW_ID
        scoreFromDB.setDate(new Date(dateScore));

        scoreFromDB.setSleepScore(sleepScore);
        scoreFromDB.setMovementScore(movementScore);
        scoreFromDB.setImaginationScore(imaginationScore);
        scoreFromDB.setLifeSatisfactionScore(lifeSatisfactionScore);
        scoreFromDB.setEatingScore(eatingScore);
        scoreFromDB.setSpeakingScore(speakingScore);

        return scoreFromDB;
    }

    /**
     * pulls raw data from the database and returns a raw object
     * @return
     */
    public Raw getRawFromDB() {
        String rawIDString = getString(getColumnIndex(SMILES_DatabaseSchema.RawTable.Columns.RAW_ID));
        long dateRaw = getLong(getColumnIndex(SMILES_DatabaseSchema.RawTable.Columns.DATE));

        int sleep1 = getInt(getColumnIndex(SMILES_DatabaseSchema.RawTable.Columns.SLEEP1));
        int sleep2 = getInt(getColumnIndex(SMILES_DatabaseSchema.RawTable.Columns.SLEEP2));

        int movement1 = getInt(getColumnIndex(SMILES_DatabaseSchema.RawTable.Columns.MOVEMENT1));
        int movement2 = getInt(getColumnIndex(SMILES_DatabaseSchema.RawTable.Columns.MOVEMENT2));
        int movement3 = getInt(getColumnIndex(SMILES_DatabaseSchema.RawTable.Columns.MOVEMENT3));

        int imagination1 = getInt(getColumnIndex(SMILES_DatabaseSchema.RawTable.Columns.IMAGINATION1));
        int imagination2 = getInt(getColumnIndex(SMILES_DatabaseSchema.RawTable.Columns.IMAGINATION2));
        int imagination3 = getInt(getColumnIndex(SMILES_DatabaseSchema.RawTable.Columns.IMAGINATION3));

        int life_satisfaction1 = getInt(getColumnIndex(SMILES_DatabaseSchema.RawTable.Columns.LIFE_SATISFACTION1));
        int life_satisfaction2 = getInt(getColumnIndex(SMILES_DatabaseSchema.RawTable.Columns.LIFE_SATISFACTION2));
        int life_satisfaction3 = getInt(getColumnIndex(SMILES_DatabaseSchema.RawTable.Columns.LIFE_SATISFACTION3));
        int life_satisfaction4 = getInt(getColumnIndex(SMILES_DatabaseSchema.RawTable.Columns.LIFE_SATISFACTION4));
        int life_satisfaction5 = getInt(getColumnIndex(SMILES_DatabaseSchema.RawTable.Columns.LIFE_SATISFACTION5));
        int life_satisfaction6 = getInt(getColumnIndex(SMILES_DatabaseSchema.RawTable.Columns.LIFE_SATISFACTION6));
        int life_satisfaction7 = getInt(getColumnIndex(SMILES_DatabaseSchema.RawTable.Columns.LIFE_SATISFACTION7));
        int life_satisfaction8 = getInt(getColumnIndex(SMILES_DatabaseSchema.RawTable.Columns.LIFE_SATISFACTION8));
        int life_satisfaction9 = getInt(getColumnIndex(SMILES_DatabaseSchema.RawTable.Columns.LIFE_SATISFACTION9));
        int life_satisfaction10 = getInt(getColumnIndex(SMILES_DatabaseSchema.RawTable.Columns.LIFE_SATISFACTION10));
        int life_satisfaction11 = getInt(getColumnIndex(SMILES_DatabaseSchema.RawTable.Columns.LIFE_SATISFACTION11));

        int eating1 = getInt(getColumnIndex(SMILES_DatabaseSchema.RawTable.Columns.EATING1));
        int eating2 = getInt(getColumnIndex(SMILES_DatabaseSchema.RawTable.Columns.EATING2));
        int eating3 = getInt(getColumnIndex(SMILES_DatabaseSchema.RawTable.Columns.EATING3));
        int eating4 = getInt(getColumnIndex(SMILES_DatabaseSchema.RawTable.Columns.EATING4));
        int eating5 = getInt(getColumnIndex(SMILES_DatabaseSchema.RawTable.Columns.EATING5));
        int eating6 = getInt(getColumnIndex(SMILES_DatabaseSchema.RawTable.Columns.EATING6));
        int eating7 = getInt(getColumnIndex(SMILES_DatabaseSchema.RawTable.Columns.EATING7));

        int speaking1 = getInt(getColumnIndex(SMILES_DatabaseSchema.RawTable.Columns.SPEAKING1));
        int speaking2 = getInt(getColumnIndex(SMILES_DatabaseSchema.RawTable.Columns.SPEAKING2));
        int speaking3 = getInt(getColumnIndex(SMILES_DatabaseSchema.RawTable.Columns.SPEAKING3));
        int speaking4 = getInt(getColumnIndex(SMILES_DatabaseSchema.RawTable.Columns.SPEAKING4));
        int speaking5 = getInt(getColumnIndex(SMILES_DatabaseSchema.RawTable.Columns.SPEAKING5));

        Raw rawFromDB = new Raw(new Date(dateRaw), UUID.fromString(rawIDString)); // new raw object with old raw ID

        //  != 0 converts the boolean into to a boolean
        rawFromDB.setSleep(sleep1, sleep2);
        rawFromDB.setMovement(movement1, movement2, movement3);
        rawFromDB.setImagination(imagination1, imagination2, imagination3);
        rawFromDB.setLifeSatisfaction(life_satisfaction1, life_satisfaction2,life_satisfaction3, life_satisfaction4,life_satisfaction5, life_satisfaction6,
                life_satisfaction7, life_satisfaction8, life_satisfaction9, life_satisfaction10, life_satisfaction11);
        rawFromDB.setEating(eating1, eating2, eating3, eating4 != 0, eating5 != 0, eating6 != 0,eating7 != 0);
        rawFromDB.setSpeaking(speaking1, speaking2 != 0, speaking3 != 0, speaking4 != 0, speaking5 != 0);

        return rawFromDB;
    }
}
