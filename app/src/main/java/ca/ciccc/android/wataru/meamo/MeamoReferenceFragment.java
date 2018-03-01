package ca.ciccc.android.wataru.meamo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.io.File;
import java.util.UUID;

/**
 * Created by satouwataru on 2018/02/20.
 */

public class MeamoReferenceFragment extends Fragment {
    private static final String ARG_RESTAURANT_ID = "restaurant_id";

    private Meamo mMeamo;
    private File mPhotoFile;
    private TextView mCategory;
    private TextView mNameField;
    private TextView mAddressField;
    private ImageButton mMapButton;
    private TextView mDate;
    private RatingBar mRBarFood;
    private RatingBar mRBarDrink;
    private RatingBar mRBarCp;
    private RatingBar mRBarService;
    private RatingBar mRBarAtmosphere;
    private RatingBar mRBarWhole;
    private TextView mMemo;
    private ImageView mPhotoView;

    public static MeamoReferenceFragment newInstance(UUID meamoId) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_RESTAURANT_ID, meamoId);

        MeamoReferenceFragment fragment = new MeamoReferenceFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UUID meamoId = (UUID) getArguments().getSerializable(ARG_RESTAURANT_ID);
        mMeamo = MeamoLab.get(getActivity()).getMeamo(meamoId);
        mPhotoFile = MeamoLab.get(getActivity()).getPhotoFile(mMeamo);

        setHasOptionsMenu(true);
    }

    @Override
    public void onPause() {
        super.onPause();

        MeamoLab.get(getActivity()).updateMeamo(mMeamo);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_meamo_reference, container, false);

        mCategory = (TextView) v.findViewById(R.id.rest_category_ref);
        mCategory.setText(mMeamo.getCategory());


        mNameField = (TextView) v.findViewById(R.id.rest_name_ref);
        mNameField.setText(mMeamo.getName());


        mAddressField = (TextView) v.findViewById(R.id.rest_address_ref);
        mAddressField.setText(mMeamo.getAddress());


        mDate = (TextView) v.findViewById(R.id.last_visit_date_ref);
        mDate.setText(mMeamo.getDate().toString());


        mRBarFood = (RatingBar) v.findViewById(R.id.ratingBar_food_ref);
        mRBarFood.setRating((float) mMeamo.getFoodRating());


        mRBarDrink = (RatingBar) v.findViewById(R.id.ratingBar_drink_ref);
        mRBarDrink.setRating((float) mMeamo.getDrinkRating());


        mRBarCp = (RatingBar) v.findViewById(R.id.ratingBar_cp_ref);
        mRBarCp.setRating((float) mMeamo.getCpRating());


        mRBarService = (RatingBar) v.findViewById(R.id.ratingBar_service_ref);
        mRBarService.setRating((float) mMeamo.getServRating());


        mRBarAtmosphere = (RatingBar) v.findViewById(R.id.ratingBar_atmosphere_ref);
        mRBarAtmosphere.setRating((float) mMeamo.getAtmRating());


        mRBarWhole = (RatingBar) v.findViewById(R.id.ratingBar_ref);
        mRBarWhole.setRating(mMeamo.getWholeRating());

        mMemo = (TextView) v.findViewById(R.id.memo_ref);
        mMemo.setText(mMeamo.getMemo());


        mMapButton = (ImageButton) v.findViewById(R.id.meamo_map_ref);
        mMapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MeamoMapsActivity.class);
                intent.putExtra("location", mMeamo.getAddress());
                startActivity(intent);
            }
        });


        mPhotoView = (ImageView) v.findViewById(R.id.meamo_photo_ref);


        return v;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater){
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_meamo_reference, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.delete_restaurant:
                MeamoLab.get(getActivity()).deleteMeamo(mMeamo);
                getActivity().finish();

                return true;
            case R.id.edit_restaurant:
                MeamoFragment meamoFragment = MeamoFragment.newInstance(mMeamo.getId());

                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.meamo_vp_content, meamoFragment);
//                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
