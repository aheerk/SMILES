package macewan_dust.smiles;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import macewan_dust.smiles.database.SMILES_DatabaseHelper;
import macewan_dust.smiles.database.SMILES_DatabaseSchema;

public class ScoringLab {

    public static final String TAG = "ScoringLab";

    // for database
    private Context mContext;
    private SQLiteDatabase mDatabase;

    /**
     * constructor
     * @param context
     */
    public ScoringLab(Context context){
        // database
        mContext = context.getApplicationContext();
        mDatabase = new SMILES_DatabaseHelper(mContext)
                .getWritableDatabase();
        Log.d(TAG, "database setup");
    }

    // ------------------------------ Database methods ------------------------------ //

    /**
     * getContentValues - gets data from an object and packages it for use with a database
     *
     * @param score - a score object with data to save
     * @return values - content values to unpack
     */
    private static ContentValues getContentValues(Score score) {
        ContentValues values = new ContentValues();
        values.put(SMILES_DatabaseSchema.ScoreTable.Columns.UUID, score.getID().toString());
        //     values.put(ScoreTable.Columns.DATE, score.getDate());                                 //// --- date is not implemented yet
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

}
