package ca.ciccc.android.wataru.meamo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;

/**
 * Created by satouwataru on 2018/02/15.
 */

public class MeamoMapActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedIntenceState) {
        super.onCreate(savedIntenceState);

        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.layout.fragment_meamo_map);
        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(35.681382, 139.766084), 15));
            }
        });

    }
}
