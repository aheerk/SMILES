package macewan_dust.smiles.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import macewan_dust.smiles.database.SMILES_DatabaseSchema.ScoreTable;


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
                ScoreTable.Columns.UUID + ", " +
                ScoreTable.Columns.DATE + ", " +
                ScoreTable.Columns.SLEEP + ", " +
                ScoreTable.Columns.MOVEMENT + ", " +
                ScoreTable.Columns.IMAGINATION + ", " +
                ScoreTable.Columns.LAUGHTER + ", " +
                ScoreTable.Columns.EATING + ", " +
                ScoreTable.Columns.SPEAKING +
                " )"
        );
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}

