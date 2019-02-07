package macewan_dust.smiles;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.LinkedList;
import java.util.List;

/**
 * Reference pages:
 * https://guides.codepath.com/android/using-the-recyclerview
 * https://developer.android.com/guide/topics/ui/layout/recyclerview#java
 */
public class DailyListFragment extends Fragment {

    private static final String TAG = "DailyListFragment";

    private RecyclerView mDailyRecyclerView;
    private RecyclerView.Adapter mDailyAdapter;
    private RecyclerView.LayoutManager mDailyLayoutManager;
    private List<DailyItem> mDailyData = new LinkedList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

// generating some items for testing                                                                    /////-------- refactor this
        DailyItem temp1 = new DailyItem(R.drawable.icon_sleep, R.drawable.border_image,
                getString(R.string.title_quest_sleep), "Score your sleep", new InputSleepFragment());
        DailyItem temp2 = new DailyItem(R.drawable.icon_movement, R.drawable.border_image_balanced,
                getString(R.string.title_quest_movement), "Measure your movement", new InputMovementFragment());
        DailyItem temp3 = new DailyItem(R.drawable.icon_imagination, R.drawable.border_image_low,
                getString(R.string.title_quest_imagination), "Rate your imagination", new InputImaginationFragment());
        DailyItem temp4 = new DailyItem(R.drawable.icon_laughter, R.drawable.border_image_high,
                getString(R.string.title_quest_laughter), "Rank your laughter", new InputLaughterFragment());
        DailyItem temp5 = new DailyItem(R.drawable.icon_eating, R.drawable.border_image,
                getString(R.string.title_quest_eating), "Balance your eating", new InputEatingFragment());
        DailyItem temp6 = new DailyItem(R.drawable.icon_speaking, R.drawable.border_image_balanced,
                getString(R.string.title_quest_speaking), "Score your speaking", new InputSpeakingFragment());

        mDailyData.add(temp1);
        mDailyData.add(temp2);
        mDailyData.add(temp3);
        mDailyData.add(temp4);
        mDailyData.add(temp5);
        mDailyData.add(temp6);
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

        getActivity().setTitle(R.string.title_daily_questions);


        return v;

    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().setTitle(R.string.title_daily_questions);

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
                replaceFragment(mDailyData.get(position).getFragment());
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
            holder.mSubtitle.setText(mDailyListData.get(position).getSubtitle());
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

