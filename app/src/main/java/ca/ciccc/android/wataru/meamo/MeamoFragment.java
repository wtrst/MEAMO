package ca.ciccc.android.wataru.meamo;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.FileProvider;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Spinner;

import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by satouwataru on 2018/01/31.
 */

public class MeamoFragment extends Fragment {
    private static final String ARG_RESTAURANT_ID = "restaurant_id";
    private static final String DIALOG_DATE = "DialogDate";

    private static final int REQUEST_DATE = 0;
    private static final int REQUEST_PHOTO = 2;

    private Meamo mMeamo;
    private File mPhotoFile;
    private Spinner mCategory;
    private EditText mNameField;
    private EditText mAddressField;
    private ImageButton mMapButton;
    private Button mDateButton;
    private RatingBar mRBarFood;
    private RatingBar mRBarDrink;
    private RatingBar mRBarCp;
    private RatingBar mRBarService;
    private RatingBar mRBarAtmosphere;
    private RatingBar mRBarWhole;
    private EditText mMemo;
    private ImageButton mPhotoButton;
    private ImageView mPhotoView;

    public static MeamoFragment newInstance(UUID meamoId) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_RESTAURANT_ID, meamoId);

        MeamoFragment fragment = new MeamoFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UUID meamoId = (UUID) getArguments().getSerializable(ARG_RESTAURANT_ID);
        mMeamo = MeamoLab.get(getActivity()).getMeamo(meamoId);
        mPhotoFile = MeamoLab.get(getActivity()).getPhotoFile(mMeamo);
    }

    @Override
    public void onPause() {
        super.onPause();

        MeamoLab.get(getActivity()).updateMeamo(mMeamo);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_meamo, container, false);

        mCategory = (Spinner) v.findViewById(R.id.rest_category);
        mCategory.setSelection(mMeamo.getCategoryId());
        mCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mMeamo.setCategoryId(mCategory.getSelectedItemPosition());
                mMeamo.setCategory(mCategory.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        mNameField = (EditText) v.findViewById(R.id.rest_name);
        mNameField.setText(mMeamo.getName());
        mNameField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mMeamo.setName(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mAddressField = (EditText) v.findViewById(R.id.rest_address);
        mAddressField.setText(mMeamo.getAddress());
        mAddressField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mMeamo.setAddress(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mDateButton = (Button) v.findViewById(R.id.last_visit_date);
        updateDate();
        mDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager manager = getFragmentManager();
                DatePickerFragment dialog = DatePickerFragment.newInstance(mMeamo.getDate());
                dialog.setTargetFragment(MeamoFragment.this, REQUEST_DATE);
                dialog.show(manager, DIALOG_DATE);
            }
        });


        mRBarFood = (RatingBar) v.findViewById(R.id.ratingBar_food);
        mRBarFood.setRating((float) mMeamo.getFoodRating());
        mRBarFood.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                mMeamo.setFoodRating((int) mRBarFood.getRating());
                updateWholeRating(mRBarWhole);
            }
        });

        mRBarDrink = (RatingBar) v.findViewById(R.id.ratingBar_drink);
        mRBarDrink.setRating((float) mMeamo.getDrinkRating());
        mRBarDrink.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                mMeamo.setDrinkRating((int) mRBarDrink.getRating());
                updateWholeRating(mRBarWhole);
            }
        });

        mRBarCp = (RatingBar) v.findViewById(R.id.ratingBar_cp);
        mRBarCp.setRating((float) mMeamo.getCpRating());
        mRBarCp.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                mMeamo.setCpRating((int) mRBarCp.getRating());
                updateWholeRating(mRBarWhole);
            }
        });

        mRBarService = (RatingBar) v.findViewById(R.id.ratingBar_service);
        mRBarService.setRating((float) mMeamo.getServRating());
        mRBarService.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                mMeamo.setServRating((int) mRBarService.getRating());
                updateWholeRating(mRBarWhole);
            }
        });

        mRBarAtmosphere = (RatingBar) v.findViewById(R.id.ratingBar_atmosphere);
        mRBarAtmosphere.setRating((float) mMeamo.getAtmRating());
        mRBarAtmosphere.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                mMeamo.setAtmRating((int) mRBarAtmosphere.getRating());
                updateWholeRating(mRBarWhole);
            }
        });


        mRBarWhole = (RatingBar) v.findViewById(R.id.ratingBar);
        mRBarWhole.setRating(mMeamo.getWholeRating());

        mMemo = (EditText) v.findViewById(R.id.memo);
        mMemo.setText(mMeamo.getMemo());
        mMemo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mMeamo.setMemo(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        mMapButton = (ImageButton) v.findViewById(R.id.meamo_map);
        mMapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MeamoMapsActivity.class);
                intent.putExtra("location", mMeamo.getAddress());
                startActivity(intent);
            }
        });


        final Intent pickContact = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);

        PackageManager packageManager = getActivity().getPackageManager();

        mPhotoButton = (ImageButton) v.findViewById(R.id.meamo_camera);
        final Intent captureImage = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        boolean canTakePhoto = mPhotoFile != null &&
                captureImage.resolveActivity(packageManager) != null;
        mPhotoButton.setEnabled(canTakePhoto);

        mPhotoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = FileProvider.getUriForFile(getActivity(),
                        "com.ciccc.android.meamo.fileprovider",
                        mPhotoFile);
                captureImage.putExtra(MediaStore.EXTRA_OUTPUT, uri);

                List<ResolveInfo> cameraActivities = getActivity().getPackageManager().queryIntentActivities(captureImage,
                        PackageManager.MATCH_DEFAULT_ONLY);

                for (ResolveInfo activity : cameraActivities) {
                    getActivity().grantUriPermission(activity.activityInfo.packageName, uri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                }

                startActivityForResult(captureImage, REQUEST_PHOTO);
            }
        });


        mPhotoView = (ImageView) v.findViewById(R.id.meamo_photo);
        updatePhotoView();

        return v;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent date) {
        if (resultCode != Activity.RESULT_OK) {
            return;
        }

        if (requestCode == REQUEST_DATE) {
            Date date1 = (Date) date.getSerializableExtra(DatePickerFragment.EXTRA_DATE);
            mMeamo.setDate(date1);
            updateDate();
        } else if (requestCode == REQUEST_PHOTO) {
            Uri uri = FileProvider.getUriForFile(getActivity(), "com.ciccc.android.meamo.fileprovider", mPhotoFile);

            getActivity().revokeUriPermission(uri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION);

            updatePhotoView();
        }
    }


    private void updateDate() {
        mDateButton.setText(mMeamo.getDate().toString());
    }

    private void updateWholeRating(RatingBar mRBarWhole) {
        mMeamo.setWholeRating(mMeamo.getFoodRating(), mMeamo.getDrinkRating(),
                mMeamo.getCpRating(), mMeamo.getServRating(), mMeamo.getAtmRating());

        mRBarWhole.setRating(mMeamo.getWholeRating());
    }

    private void updatePhotoView() {
        if (mPhotoFile == null || !mPhotoFile.exists()) {
            mPhotoView.setImageDrawable(null);
        } else {
            Bitmap bitmap = PictureUtils.getScaledBitmap(mPhotoFile.getPath(), getActivity());
            mPhotoView.setImageBitmap(bitmap);
        }
    }
}
