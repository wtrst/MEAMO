package ca.ciccc.android.wataru.meamo;

import android.support.v4.app.Fragment;

/**
 * Created by satouwataru on 2018/02/01.
 */

public class MeamoListActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment(){
        return new MeamoListFragment();
    }
}
