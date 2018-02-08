package ca.ciccc.android.wataru.meamo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import ca.ciccc.android.wataru.meamo.database.MeamoBaseHelper;
import ca.ciccc.android.wataru.meamo.database.MeamoDbSchema;

/**
 * Created by satouwataru on 2018/02/01.
 */

public class MeamoLab {
    private static MeamoLab sMeamoLab;

    private Context mContext;
    private SQLiteDatabase mDatabase;

    public static MeamoLab get(Context context) {
        if (sMeamoLab == null) {
            sMeamoLab = new MeamoLab(context);
        }
        return sMeamoLab;
    }

    private MeamoLab(Context context) {
        mContext = context.getApplicationContext();
        mDatabase = new MeamoBaseHelper(mContext).getWritableDatabase();
    }

    public void addMeamo(Meamo m){
        ContentValues values = getContentValues(m);
        mDatabase.insert(MeamoDbSchema.MeamoTable.NAME, null, values);
    }

    public List<Meamo> getMeamos() {
        List<Meamo> meamos = new ArrayList<>();

        MeamoCursorWrapper cursor = queryMeamos(null, null);

        try{
            cursor.moveToFirst();
            while (!cursor.isAfterLast()){
                meamos.add(cursor.getMeamo());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }

        return meamos;
    }

    public Meamo getMeamo(UUID id) {
        MeamoCursorWrapper cursor = queryMeamos(
                MeamoDbSchema.MeamoTable.Cols.UUID + " = ?",
                new String[] {id.toString()}
        );

        try{
            if (cursor.getCount() == 0){
                return null;
            }

            cursor.moveToFirst();
            return cursor.getMeamo();
        }finally {
            cursor.close();
        }
    }

    public File getPhotoFile(Meamo meamo){
        File filesDir = mContext.getFilesDir();
        return new File(filesDir, meamo.getPhotoFilename());
    }

    public void updateMeamo(Meamo meamo){
        String uuidString = meamo.getId().toString();
        ContentValues values = getContentValues(meamo);

        mDatabase.update(MeamoDbSchema.MeamoTable.NAME, values,
                MeamoDbSchema.MeamoTable.Cols.UUID + " = ?",
                new String[] {uuidString});
    }

    private MeamoCursorWrapper queryMeamos(String whereClause, String[] whereArgs){
        Cursor cursor = mDatabase.query(
                MeamoDbSchema.MeamoTable.NAME,
                null,
                whereClause,
                whereArgs,
                null,
                null,
                null
        );

        return new MeamoCursorWrapper(cursor);
    }

    private static ContentValues getContentValues(Meamo meamo){
        ContentValues values = new ContentValues();
        values.put(MeamoDbSchema.MeamoTable.Cols.UUID, meamo.getId().toString());
        values.put(MeamoDbSchema.MeamoTable.Cols.CATEGORY, meamo.getCategory());
        values.put(MeamoDbSchema.MeamoTable.Cols.RESTAURANT_NAME, meamo.getName());
        values.put(MeamoDbSchema.MeamoTable.Cols.RATE, meamo.getWholeRating());
        values.put(MeamoDbSchema.MeamoTable.Cols.RATE_FOOD, meamo.getFoodRating());
        values.put(MeamoDbSchema.MeamoTable.Cols.RATE_DRINK, meamo.getDrinkRating());
        values.put(MeamoDbSchema.MeamoTable.Cols.RATE_CP, meamo.getCpRating());
        values.put(MeamoDbSchema.MeamoTable.Cols.RATE_SERVICE, meamo.getServRating());
        values.put(MeamoDbSchema.MeamoTable.Cols.RATE_ATMOSPHERE, meamo.getAtmRating());
        values.put(MeamoDbSchema.MeamoTable.Cols.ADDRESS, meamo.getAddress());
        values.put(MeamoDbSchema.MeamoTable.Cols.MEMO, meamo.getMemo());
        values.put(MeamoDbSchema.MeamoTable.Cols.LAST_VISIT_DATE, meamo.getDate().getTime());

        return values;
    }
}
