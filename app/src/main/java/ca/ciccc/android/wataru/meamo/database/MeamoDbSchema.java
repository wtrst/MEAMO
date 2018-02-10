package ca.ciccc.android.wataru.meamo.database;

/**
 * Created by satouwataru on 2018/02/05.
 */

public class MeamoDbSchema {
    public static final class MeamoTable {
        public static final String NAME = "restaurants";

        public static final class Cols {
            public static final String UUID = "uuid";
            public static final String CATEGORY_ID = "category_id";
            public static final String CATEGORY = "category";
            public static final String RESTAURANT_NAME = "restaurant_name";
            public static final String RATE = "rate";
            public static final String RATE_FOOD = "rate_food";
            public static final String RATE_DRINK = "rate_drink";
            public static final String RATE_CP = "rate_cp";
            public static final String RATE_SERVICE = "rate_service";
            public static final String RATE_ATMOSPHERE = "rate_atmosphere";
            public static final String ADDRESS = "address";
            public static final String MEMO = "memo";
            public static final String LAST_VISIT_DATE = "last_visit_date";

        }
    }

}
