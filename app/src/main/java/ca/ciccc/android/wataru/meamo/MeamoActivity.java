//package ca.ciccc.android.wataru.meamo;
//
//import android.content.Context;
//import android.content.Intent;
//import android.support.v4.app.Fragment;
//import android.support.v4.app.FragmentManager;
//import android.support.v7.app.AppCompatActivity;
//import android.os.Bundle;
//
//import java.util.UUID;
//
//public class MeamoActivity extends SingleFragmentActivity {
//
//    private static final String EXTRA_RESTAURANT_ID = "com.ciccc.android.meamo.restaurant_id";
//    private static final String ARG_ITEM_ID = "item_id";
//
//    public static Intent newIntent(Context packageContext, UUID restaurantId, String itemId){
//        Intent intent = new Intent(packageContext, MeamoActivity.class);
//        intent.putExtra(EXTRA_RESTAURANT_ID, restaurantId);
//        intent.putExtra(ARG_ITEM_ID, itemId);
//        return intent;
//    }
//
//    @Override
//    protected Fragment createFragment(){
//        UUID restaurantId = (UUID) getIntent().getSerializableExtra(EXTRA_RESTAURANT_ID);
//        String itemId = (String) getIntent().getSerializableExtra(ARG_ITEM_ID);
//
//        if (itemId.equals(R.id.new_restaurant)){
//            return MeamoFragment.newInstance(restaurantId);
//        }else {
//            return MeamoReferenceFragment.newInstance(restaurantId);
//        }
//
//    }
//
//}
