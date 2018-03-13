package ca.ciccc.android.wataru.meamo;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by satouwataru on 2018/02/01.
 */

public abstract class SingleFragmentActivity extends AppCompatActivity {
    protected abstract android.support.v4.app.Fragment createFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);

        FragmentManager fm = getSupportFragmentManager();
//        Fragment fragment = fm.findFragmentById(R.id.fragment_container);
        Fragment fragment = fm.findFragmentById(R.id.fragment_container);

        if(fragment == null){
            fragment = createFragment();
//            fm.beginTransaction().add(R.id.fragment_container, fragment).commit();
            fm.beginTransaction().add(R.id.fragment_container, fragment).commit();
        }
    }
}
