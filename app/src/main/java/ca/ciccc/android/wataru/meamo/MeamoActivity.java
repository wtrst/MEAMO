package ca.ciccc.android.wataru.meamo;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.UUID;

public class MeamoActivity extends SingleFragmentActivity {

    private static final String EXTRA_RESTAURANT_ID = "com.ciccc.android.meamo.restaurant_id";

    public static Intent newIntent(Context packageContext, UUID restaurantId){
        Intent intent = new Intent(packageContext, MeamoActivity.class);
        intent.putExtra(EXTRA_RESTAURANT_ID, restaurantId);
        return intent;
    }

    @Override
    protected Fragment createFragment(){
        UUID restaurantId = (UUID) getIntent().getSerializableExtra(EXTRA_RESTAURANT_ID);
        return MeamoFragment.newInstance(restaurantId);
    }

}
