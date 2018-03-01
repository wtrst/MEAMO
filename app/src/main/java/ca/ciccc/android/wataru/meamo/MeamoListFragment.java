package ca.ciccc.android.wataru.meamo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.List;

/**
 * Created by satouwataru on 2018/02/01.
 */

public class MeamoListFragment extends Fragment {
    private static final String ARG_ITEM_ID = "item_id";

    private RecyclerView mMeamoRecyclerView;
    private MeamoAdapter mAdapter;
    private int mMenuItem;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_meamo_list, container, false);

        mMeamoRecyclerView = (RecyclerView) view.findViewById(R.id.meamo_recycler_view);
        mMeamoRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        updateUI();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_meamo_list, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.new_restaurant:
                Meamo meamo = new Meamo();
                MeamoLab.get(getActivity()).addMeamo(meamo);
                mMenuItem = item.getItemId();
                Intent intent = MeamoPagerActivity.newIntent(getActivity(), meamo.getId(), mMenuItem);
//                intent.putExtra(ARG_ITEM_ID, item.getItemId());
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void updateUI() {
        MeamoLab meamoLab = MeamoLab.get(getActivity());
        List<Meamo> meamos = meamoLab.getMeamos();

        if (mAdapter == null) {
            mAdapter = new MeamoAdapter(meamos);
            mMeamoRecyclerView.setAdapter(mAdapter);
        } else {
            mAdapter.setMeamos(meamos);
            mAdapter.notifyDataSetChanged();
        }
    }

    private class MeamoHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private Meamo mMeamo;
        private TextView mNameTextView;
        private TextView mAddressTextView;
        private TextView mDateTextView;
        private TextView mCategory;
        private RatingBar mRate;


        public void bind(Meamo meamo) {
            mMeamo = meamo;
            mNameTextView.setText(mMeamo.getName());
            mAddressTextView.setText(mMeamo.getAddress());
            mDateTextView.setText(DateFormat.format("MMM dd, yyyy", mMeamo.getDate()));
            mCategory.setText(mMeamo.getCategory());
            mRate.setRating(mMeamo.getWholeRating());
        }

        public MeamoHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_meamo, parent, false));
            itemView.setOnClickListener(this);

            mNameTextView = (TextView) itemView.findViewById(R.id.meamo_res_name);
            mAddressTextView = (TextView) itemView.findViewById(R.id.meamo_res_address);
            mDateTextView = (TextView) itemView.findViewById(R.id.meamo_last_date);
            mCategory = (TextView) itemView.findViewById(R.id.meamo_res_category);
            mRate = (RatingBar) itemView.findViewById(R.id.meamo_res_rate);
        }

        @Override
        public void onClick(View view) {
            Intent intent = MeamoPagerActivity.newIntent(getActivity(), mMeamo.getId(), mMenuItem);
            startActivity(intent);
        }
    }

    private class MeamoAdapter extends RecyclerView.Adapter<MeamoHolder> {
        private List<Meamo> mMeamos;

        public MeamoAdapter(List<Meamo> meamos) {
            mMeamos = meamos;
        }

        @Override
        public MeamoHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());

            return new MeamoHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(MeamoHolder holder, int position) {
            Meamo meamo = mMeamos.get(position);
            holder.bind(meamo);
        }

        @Override
        public int getItemCount() {
            return mMeamos.size();
        }

        public void setMeamos(List<Meamo> meamos) {
            mMeamos = meamos;
        }
    }

}
