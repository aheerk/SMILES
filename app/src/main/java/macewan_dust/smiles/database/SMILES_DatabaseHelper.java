package macewan_dust.smiles.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import macewan_dust.smiles.database.SMILES_DatabaseSchema.ScoreTable;
import macewan_dust.smiles.database.SMILES_DatabaseSchema.RawTable;


/**
 * create and update table methods
 */
public class SMILES_DatabaseHelper extends SQLiteOpenHelper {
    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "SMILES_Database.db";

    public SMILES_DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    /**
     * Creates tables
     * @param db
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + SMILES_DatabaseSchema.ScoreTable.NAME + " ( " +
                " _id integer primary key autoincrement, " +
                ScoreTable.Columns.SCORE_ID + ", " +
                ScoreTable.Columns.DATE + ", " +
                ScoreTable.Columns.SLEEP + ", " +
                ScoreTable.Columns.MOVEMENT + ", " +
                ScoreTable.Columns.IMAGINATION + ", " +
                ScoreTable.Columns.LAUGHTER + ", " +
                ScoreTable.Columns.EATING + ", " +
                ScoreTable.Columns.SPEAKING +
                " )"
        );

        db.execSQL("create table " + SMILES_DatabaseSchema.RawTable.NAME + " ( " +
                " _id integer primary key autoincrement, " +
                RawTable.Columns.SCORE_ID + ", " +
                RawTable.Columns.DATE + ", " +
                RawTable.Columns.SLEEP1 + ", " +
                RawTable.Columns.SLEEP2 + ", " +
                RawTable.Columns.MOVEMENT1 + ", " +
                RawTable.Columns.MOVEMENT2 + ", " +
                RawTable.Columns.MOVEMENT3 + ", " +
                RawTable.Columns.IMAGINATION1 + ", " +
                RawTable.Columns.IMAGINATION2 + ", " +
                RawTable.Columns.IMAGINATION3 + ", " +
                RawTable.Columns.LAUGHTER1 + ", " +
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
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}

