package macewan_dust.smiles.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import macewan_dust.smiles.database.SMILES_DatabaseSchema.ScoreTable;
import macewan_dust.smiles.database.SMILES_DatabaseSchema.RawTable;


/**
 * create and update table methods
 */
public class SMILES_DatabaseHelper extends SQLiteOpenHelper {
    private static final int VERSION = 2;
    private static final String DATABASE_NAME = "SMILES_Database.db";
    public static final String TAG = "DatabaseHelper";

    public SMILES_DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    /**
     * Creates an instance of the score table with the given name
     * @param db SQLiteDatabase instance
     * @param tableName
     */
    public static void createScoreTable(SQLiteDatabase db, String tableName){
        db.execSQL("create table " + tableName + " ( " +
                " _id integer primary key autoincrement, " +
                ScoreTable.Columns.SCORE_ID + ", " +
                ScoreTable.Columns.DATE + ", " +
                ScoreTable.Columns.SLEEP + ", " +
                ScoreTable.Columns.MOVEMENT + ", " +
                ScoreTable.Columns.IMAGINATION + ", " +
                ScoreTable.Columns.LIFE_SATISFACTION + ", " +
                ScoreTable.Columns.EATING + ", " +
                ScoreTable.Columns.SPEAKING +
                " )"
        );
    }

    /**
     * Creates an instance of the raw table with the given name
     * @param db SQLiteDatabase instance
     * @param tableName
     */
    public static void createRawTable(SQLiteDatabase db, String tableName){
        db.execSQL("create table " + tableName + " ( " +
                " _id integer primary key autoincrement, " +
                RawTable.Columns.RAW_ID + ", " +
                RawTable.Columns.DATE + ", " +
                RawTable.Columns.SLEEP1 + ", " +
                RawTable.Columns.SLEEP2 + ", " +
                RawTable.Columns.MOVEMENT1 + ", " +
                RawTable.Columns.MOVEMENT2 + ", " +
                RawTable.Columns.MOVEMENT3 + ", " +
                RawTable.Columns.IMAGINATION1 + ", " +
                RawTable.Columns.IMAGINATION2 + ", " +
                RawTable.Columns.IMAGINATION3 + ", " +
                RawTable.Columns.LIFE_SATISFACTION1 + ", " +
                RawTable.Columns.LIFE_SATISFACTION2 + ", " +
                RawTable.Columns.LIFE_SATISFACTION3 + ", " +
                RawTable.Columns.LIFE_SATISFACTION4 + ", " +
                RawTable.Columns.LIFE_SATISFACTION5 + ", " +
                RawTable.Columns.LIFE_SATISFACTION6 + ", " +
                RawTable.Columns.LIFE_SATISFACTION7 + ", " +
                RawTable.Columns.LIFE_SATISFACTION8 + ", " +
                RawTable.Columns.LIFE_SATISFACTION9 + ", " +
                RawTable.Columns.LIFE_SATISFACTION10 + ", " +
                RawTable.Columns.LIFE_SATISFACTION11 + ", " +
                RawTable.Columns.EATING1 + ", " +
                RawTable.Columns.EATING2 + ", " +
                RawTable.Columns.EATING3 + ", " +
                RawTable.Columns.EATING4 + ", " +
                RawTable.Columns.EATING5 + ", " +
                RawTable.Columns.EATING6 + ", " +
                RawTable.Columns.EATING7 + ", " +
                RawTable.Columns.SPEAKING1 + ", " +
                RawTable.Columns.SPEAKING2 + ", " +
                RawTable.Columns.SPEAKING3 + ", " +
                RawTable.Columns.SPEAKING4 + ", " +
                RawTable.Columns.SPEAKING5 +
                " )"
        );
    }

    /**
     * Creates tables
     * @param db
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        createScoreTable(db, SMILES_DatabaseSchema.ScoreTable.NAME);
        createRawTable(db, SMILES_DatabaseSchema.RawTable.NAME);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d(TAG, String.format("Upgrading database from version %d to %d", oldVersion, newVersion));

        /**
         * Since the SQLite ALTER command is limited, we must use their suggested approach:
         * https://www.sqlite.org/lang_altertable.html#:~:text=SQLite%20supports%20a%20limited%20subset,column%20to%20an%20existing%20table.
         */
        if (oldVersion == 1 && newVersion == 2){
            db.execSQL("BEGIN TRANSACTION;");

            // Create new tables
            String tempScoreName = SMILES_DatabaseSchema.ScoreTable.NAME + "Temp";
            String tempRawName = SMILES_DatabaseSchema.RawTable.NAME + "Temp";
            createScoreTable(db, tempScoreName);
            createRawTable(db, tempRawName);

            // Copy data from old tables
            db.execSQL("INSERT INTO " + tempScoreName + " SELECT " +
                    "_id," +
                    ScoreTable.Columns.SCORE_ID + ", " +
                    ScoreTable.Columns.DATE + ", " +
                    ScoreTable.Columns.SLEEP + ", " +
                    ScoreTable.Columns.MOVEMENT + ", " +
                    ScoreTable.Columns.IMAGINATION + ", 0, " + // Zero for life satisfaction
                    ScoreTable.Columns.EATING + ", " +
                    ScoreTable.Columns.SPEAKING +
                    " FROM " + SMILES_DatabaseSchema.ScoreTable.NAME + ";");

            db.execSQL("INSERT INTO " + tempRawName + " SELECT " +
                            "_id," +
                            RawTable.Columns.RAW_ID + ", " +
                            RawTable.Columns.DATE + ", " +
                            RawTable.Columns.SLEEP1 + ", " +
                            RawTable.Columns.SLEEP2 + ", " +
                            RawTable.Columns.MOVEMENT1 + ", " +
                            RawTable.Columns.MOVEMENT2 + ", " +
                            RawTable.Columns.MOVEMENT3 + ", " +
                            RawTable.Columns.IMAGINATION1 + ", " +
                            RawTable.Columns.IMAGINATION2 + ", " +
                            RawTable.Columns.IMAGINATION3 +
                            ", 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, " + // Nothing for life satisfaction
                            RawTable.Columns.EATING1 + ", " +
                            RawTable.Columns.EATING2 + ", " +
                            RawTable.Columns.EATING3 + ", " +
                            RawTable.Columns.EATING4 + ", " +
                            RawTable.Columns.EATING5 + ", " +
                            RawTable.Columns.EATING6 + ", " +
                            RawTable.Columns.EATING7 + ", " +
                            RawTable.Columns.SPEAKING1 + ", " +
                            RawTable.Columns.SPEAKING2 + ", " +
                            RawTable.Columns.SPEAKING3 + ", " +
                            RawTable.Columns.SPEAKING4 + ", " +
                            RawTable.Columns.SPEAKING5 +
                            " FROM " + SMILES_DatabaseSchema.RawTable.NAME + ";"
                    );

            // Drop old table
            db.execSQL("DROP TABLE " + SMILES_DatabaseSchema.ScoreTable.NAME + ";");
            db.execSQL("DROP TABLE " + SMILES_DatabaseSchema.RawTable.NAME + ";");

            // Rename new tables
            db.execSQL("ALTER TABLE " + tempScoreName +
                    " RENAME TO " + SMILES_DatabaseSchema.ScoreTable.NAME + ";");
            db.execSQL("ALTER TABLE " + tempRawName +
                    " RENAME TO " + SMILES_DatabaseSchema.RawTable.NAME + ";");
            db.execSQL("COMMIT;");
        }
        else {
            Log.e(TAG, "A database versioning error has occurred. The database has not been upgraded.");
        }
    }
}

