package ca.ciccc.android.wataru.meamo.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

//import ca.ciccc.android.wataru.meamo.database.MeamoDbSchema.CategoryTable;
import ca.ciccc.android.wataru.meamo.database.MeamoDbSchema.MeamoTable;

/**
 * Created by satouwataru on 2018/02/05.
 */

public class MeamoBaseHelper extends SQLiteOpenHelper {
    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "meamoBase.db";

    public MeamoBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + MeamoTable.NAME + "(" +
                " _id integer primary key autoincrement, " +
                MeamoTable.Cols.UUID + ", " +
                MeamoTable.Cols.CATEGORY_ID + ", " +
                MeamoTable.Cols.CATEGORY + ", " +
                MeamoTable.Cols.RESTAURANT_NAME + ", " +
                MeamoTable.Cols.RATE + ", " +
                MeamoTable.Cols.RATE_FOOD + ", " +
                MeamoTable.Cols.RATE_DRINK + ", " +
                MeamoTable.Cols.RATE_CP + ", " +
                MeamoTable.Cols.RATE_SERVICE + ", " +
                MeamoTable.Cols.RATE_ATMOSPHERE + ", " +
                MeamoTable.Cols.ADDRESS + ", " +
                MeamoTable.Cols.MEMO + ", " +
                MeamoTable.Cols.LAST_VISIT_DATE +
                ")"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
