package ca.ciccc.android.wataru.meamo;

import android.database.Cursor;
import android.database.CursorWrapper;

import java.util.Date;
import java.util.UUID;

import ca.ciccc.android.wataru.meamo.database.MeamoDbSchema;

/**
 * Created by satouwataru on 2018/02/05.
 */

public class MeamoCursorWrapper extends CursorWrapper {
    public MeamoCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public Meamo getMeamo(){
        String uuidString = getString(getColumnIndex(MeamoDbSchema.MeamoTable.Cols.UUID));
        String category = getString(getColumnIndex(MeamoDbSchema.MeamoTable.Cols.CATEGORY));
        String name = getString(getColumnIndex(MeamoDbSchema.MeamoTable.Cols.RESTAURANT_NAME));
        float rateWhole = getFloat(getColumnIndex(MeamoDbSchema.MeamoTable.Cols.RATE));
        int rateFood = getInt(getColumnIndex(MeamoDbSchema.MeamoTable.Cols.RATE_FOOD));
        int rateDrink = getInt(getColumnIndex(MeamoDbSchema.MeamoTable.Cols.RATE_DRINK));
        int rateCp = getInt(getColumnIndex(MeamoDbSchema.MeamoTable.Cols.RATE_CP));
        int rateService = getInt(getColumnIndex(MeamoDbSchema.MeamoTable.Cols.RATE_SERVICE));
        int rateAtmosphere = getInt(getColumnIndex(MeamoDbSchema.MeamoTable.Cols.RATE_ATMOSPHERE));
        String address = getString(getColumnIndex(MeamoDbSchema.MeamoTable.Cols.ADDRESS));
        String memo = getString(getColumnIndex(MeamoDbSchema.MeamoTable.Cols.MEMO));
        long date =  getLong(getColumnIndex(MeamoDbSchema.MeamoTable.Cols.LAST_VISIT_DATE));

        Meamo meamo = new Meamo(UUID.fromString(uuidString));
        meamo.setCategory(category);
        meamo.setName(name);
        meamo.setWholeRating(rateFood, rateDrink, rateCp, rateService, rateAtmosphere);
        meamo.setFoodRating(rateFood);
        meamo.setDrinkRating(rateDrink);
        meamo.setCpRating(rateCp);
        meamo.setServRating(rateService);
        meamo.setAtmRating(rateAtmosphere);
        meamo.setAddress(address);
        meamo.setMemo(memo);
        meamo.setDate(new Date(date));

        return meamo;
    }
}
