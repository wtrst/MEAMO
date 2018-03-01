package ca.ciccc.android.wataru.meamo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import java.util.List;
import java.util.UUID;

/**
 * Created by satouwataru on 2018/02/04.
 */

public class MeamoPagerActivity extends AppCompatActivity {

    private static final String EXTRA_RESTAURANT_ID = "com.ciccc.android.meamo.restaurant_id";
    private static final String ARG_ITEM_ID = "item_id";

    private ViewPager mViewPager;
    private List<Meamo> mMeamos;

    public static Intent newIntent(Context packageContext, UUID meamoId, int item) {
        Intent intent = new Intent(packageContext, MeamoPagerActivity.class);
        intent.putExtra(EXTRA_RESTAURANT_ID, meamoId);
        intent.putExtra(ARG_ITEM_ID, item);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meamo_pager);

        UUID meamoId = (UUID) getIntent().getSerializableExtra(EXTRA_RESTAURANT_ID);

        mViewPager = (ViewPager) findViewById(R.id.meamo_view_pager);

        mMeamos = MeamoLab.get(this).getMeamos();
        FragmentManager fragmentManager = getSupportFragmentManager();
        mViewPager.setAdapter(new FragmentStatePagerAdapter(fragmentManager) {
            @Override
            public Fragment getItem(int position) {
                int menuItem = (int) getIntent().getSerializableExtra(ARG_ITEM_ID);
                Meamo meamo = mMeamos.get(position);

                if (menuItem == R.id.new_restaurant){
                    return MeamoFragment.newInstance(meamo.getId());
                }else{
                    return MeamoReferenceFragment.newInstance(meamo.getId());
                }
            }

            @Override
            public int getCount() {
                return mMeamos.size();
            }
        });

        for (int i = 0; i < mMeamos.size(); i++) {
            if (mMeamos.get(i).getId().equals(meamoId)) {
                mViewPager.setCurrentItem(i);
                break;
            }
        }

    }
}
