package macewan_dust.smiles;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * Reference pages:
 * https://guides.codepath.com/android/using-the-recyclerview
 * https://developer.android.com/guide/topics/ui/layout/recyclerview#java
 */
public class DailyListFragment extends Fragment {

    private static final String TAG = "DailyListFragment";
    public static final String DAILY_RECYCLER_VIEW_INDEX = "daily_recycler_view_index";
    public static final String DAILY_DATE = "daily_date";

    private static final String DIALOG_DATE = "DialogDate";
    private static final int REQUEST_DATE = 0;

    private RecyclerView mDailyRecyclerView;
    private RecyclerView.Adapter mDailyAdapter;
    private RecyclerView.LayoutManager mDailyLayoutManager;
    private List<DailyItem> mDailyData = new LinkedList<>();

    private Button mDatePicker;
    private Date mScoreDate;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // generating category items. items will be updated based on input later
        mDailyData.add(new DailyItem(R.drawable.icon_sleep,
                getString(R.string.title_sleep), new InputSleepFragment()));
        mDailyData.add(new DailyItem(R.drawable.icon_movement,
                getString(R.string.title_movement), new InputMovementFragment()));
        mDailyData.add(new DailyItem(R.drawable.icon_imagination,
                getString(R.string.title_imagination), new InputImaginationFragment()));
        mDailyData.add(new DailyItem(R.drawable.icon_laughter,
                getString(R.string.title_laughter), new InputLaughterFragment()));
        mDailyData.add(new DailyItem(R.drawable.icon_eating,
                getString(R.string.title_eating), new InputEatingFragment()));
        mDailyData.add(new DailyItem(R.drawable.icon_speaking,
                getString(R.string.title_speaking), new InputSpeakingFragment()));

        ((AppCompatActivity)getActivity()).getSupportActionBar();
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);

        if (mScoreDate == null) {
            mScoreDate = new Date();
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_daily_list, container, false);

        mDailyRecyclerView = v.findViewById(R.id.daily_recycler_view);
        mDailyLayoutManager = new LinearLayoutManager(this.getActivity());
        mDailyRecyclerView.setLayoutManager(mDailyLayoutManager);
        mDailyAdapter = new DailyAdapter(mDailyData);
        mDailyRecyclerView.setAdapter(mDailyAdapter);
        mDailyRecyclerView.setHasFixedSize(true);

        mDatePicker = v.findViewById(R.id.daily_score_date_picker_button);
        mDatePicker.setText(Score.timelessDate(new Date()));

        mDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager manager = getFragmentManager(); // manager of parent
                DatePickerFragment dialog = DatePickerFragment.newInstance(mScoreDate);
                dialog.setTargetFragment(DailyListFragment.this, REQUEST_DATE); // set target to retrieve data from a fragment. p 238
                dialog.show(manager, DIALOG_DATE); // passing in FragmentManager of parent and string id.
                Log.d(TAG, "calendar date: " + mScoreDate.toString());
            }
        });

        getActivity().setTitle(R.string.title_daily_questions);

        setBorders();
        return v;
    }

    /**
     * @param requestCode code that keeps track of where it was called from
     * @param resultCode  constants. success or failure...
     * @param data        data to be unpacked.
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode != Activity.RESULT_OK) {
            return;
        }

        if (requestCode == REQUEST_DATE) {
            // Extra date is a public constant so we are pulling it out of the class.
            mScoreDate = (Date) data.getSerializableExtra(DatePickerFragment.EXTRA_DATE);

            // set date to morning.
            mScoreDate = new Date(mScoreDate.getTime() + 1000*2*60*60);

            Log.d(TAG, "date picker date: " + mScoreDate.toString());
            mDatePicker.setText(Score.timelessDate(mScoreDate));
            setBorders();
            mDailyAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().setTitle(R.string.title_daily_questions);
        setBorders();
        mDatePicker.setText(Score.timelessDate(mScoreDate));
        }

    private void setBorders() {
        if (ScoringLab.get(getActivity()).isScore(mScoreDate)) {
            // get score object to use its data
            Score tempScore = ScoringLab.get(getActivity()).getScore(mScoreDate);
            // set new score for this category

            // set border based on todays score. subtitle is update automatically based on background.
            mDailyData.get(0).setBackgroundID(Score.getBackgroundID(tempScore.getSleepScore()));
            mDailyData.get(1).setBackgroundID(Score.getBackgroundID(tempScore.getMovementScore()));
            mDailyData.get(2).setBackgroundID(Score.getBackgroundID(tempScore.getImaginationScore()));
            mDailyData.get(3).setBackgroundID(Score.getBackgroundID(tempScore.getLaughterScore()));
            mDailyData.get(4).setBackgroundID(Score.getBackgroundID(tempScore.getEatingScore()));
            mDailyData.get(5).setBackgroundID(Score.getBackgroundID(tempScore.getSpeakingScore()));
        }
    }

    public class DailyAdapter extends RecyclerView.Adapter<DailyAdapter.DailyViewHolder> {

        private List<DailyItem> mDailyListData;

        /**
         * Reference for views for each data item.
         * Set the image and text for the recycler view list here.
         * <p>
         * Optional: implementing on click listener and onClick method
         */
        public class DailyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
            public ImageView mIcon;
            public TextView mTitle;
            public TextView mSubtitle;

            public DailyViewHolder(View itemView) {
                super(itemView);
                mIcon = itemView.findViewById(R.id.list_item_daily_category);
                mTitle = itemView.findViewById(R.id.list_item_daily_title);
                mSubtitle = itemView.findViewById(R.id.list_item_daily_subtitle);
                itemView.setOnClickListener(this);
            }

            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick called");
                int position = getAdapterPosition(); // find out where this view is in the list
                // gets daily item at views position, then gets the fragment out of it and loads it
                // replaceFragment(mDailyData.get(position).getFragment());

                DailyPagerFragment tempFragment = new DailyPagerFragment();
                Bundle opBundle = new Bundle();
                opBundle.putInt(DAILY_RECYCLER_VIEW_INDEX, position);
                opBundle.putLong(DAILY_DATE, mScoreDate.getTime());

                tempFragment.setArguments(opBundle);
                replaceFragment(tempFragment);
            }
        }

        /*
         * Passes the list data for use by the system
         */
        public DailyAdapter(List<DailyItem> dailyListData) {
            mDailyListData = dailyListData;
        }

        /**
         * Creates new views for each list item.
         * Specify what view layout is used here.
         */

        public DailyAdapter.DailyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {              // ----- what is the role of viewType here?

            // create one new view
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.list_item_daily, parent, false);

            DailyViewHolder vh = new DailyViewHolder(v);
            return vh;
        }


        /**
         * Called by the system
         * Sets the contents of a view that has already been created.
         *
         * @param holder   - the individual list objects
         * @param position - index location in the list of data
         */
        @Override
        public void onBindViewHolder(@NonNull DailyViewHolder holder, int position) {
            holder.mIcon.setImageResource(mDailyListData.get(position).getIconID());
            holder.mIcon.setBackground(getResources().getDrawable(mDailyListData.get(position).getBackgroundID()));
            holder.mTitle.setText(mDailyListData.get(position).getTitle());
            holder.mSubtitle.setText(mDailyListData.get(position).getSubtitleID());
        }

        /**
         * Required by recycler view
         * * returns size of dataset
         */
        @Override
        public int getItemCount() {
            return mDailyListData.size();
        }
    }

    /**
     * replaceFragment - performs fragment transactions.
     *
     * @param newFragment
     */
    private void replaceFragment(Fragment newFragment) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, newFragment);
        // the back button on phones will return to this item. name is optional
        transaction.addToBackStack("dailyList");
        transaction.commit();
        Log.d(TAG, "replacing fragment");
    }
}

