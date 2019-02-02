package macewan_dust.smiles;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.LinkedList;
import java.util.List;

public class DashboardListFragment extends Fragment {

    FloatingActionButton mButtonStartDailyQuestions;
    private static final String TAG = "Dashboard";

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mDashboardRecyclerViewAdapter;
    private RecyclerView.LayoutManager mRecyclerViewLayoutManager;
    private List<Score> mDashboardData = new LinkedList<>();

    /**
     * new instance constructor
     * @return InputSleepFragment
     */
    public static DashboardListFragment newInstance() {
        return new DashboardListFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

// generating some items for testing
        Score temp = new Score();
        temp.setSpeakingScore(ScoringAlgorithms.SCORE_BALANCED);
        temp.setLaughterScore(ScoringAlgorithms.SCORE_BALANCED);
        mDashboardData.add(temp);

        Score temp2 = new Score();
        temp2.setSpeakingScore(ScoringAlgorithms.SCORE_LOW);
        temp2.setLaughterScore(ScoringAlgorithms.SCORE_LOW);
        temp2.setMovementScore(ScoringAlgorithms.SCORE_BALANCED);
        mDashboardData.add(temp2);




    }



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_dashboard_list, null);

        mRecyclerView = v.findViewById(R.id.dashboard_recycler_view);
        mRecyclerViewLayoutManager = new LinearLayoutManager(this.getActivity());
        mRecyclerView.setLayoutManager(mRecyclerViewLayoutManager);
        mDashboardRecyclerViewAdapter = new DashboardAdapter(mDashboardData);
        mRecyclerView.setAdapter(mDashboardRecyclerViewAdapter);
        mRecyclerView.setHasFixedSize(true);

        mButtonStartDailyQuestions = v.findViewById(R.id.button_start_daily_questions);
        mButtonStartDailyQuestions.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v){
                DailyListFragment fragment = new DailyListFragment();
                replaceFragment(fragment);
            }
        });

        return v;
    }





    /**
     * replaceFragment - performs fragment transactions.
     * @param newFragment
     */
    private void replaceFragment(Fragment newFragment) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, newFragment);
     //   transaction.addToBackStack(null);
        transaction.commit();
        Log.d(TAG, "replacing fragment");
    }

    // ----------- recycler view methods


    public class DashboardAdapter extends RecyclerView.Adapter<DashboardListFragment.DashboardAdapter.DashboardViewHolder> {

        private List<Score> mDashboardListData;

        /**
         * Reference for views for each data item.
         * Set the image and text for the recycler view list here.
         * <p>
         * Optional: implementing on click listener and onClick method
         */
        public class DashboardViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
            public ImageView mIconSleep;
            public ImageView mIconMovement;
            public ImageView mIconImagination;
            public ImageView mIconLaughter;
            public ImageView mIconEating;
            public ImageView mIconSpeaking;

            public TextView mDate;

            public DashboardViewHolder(View itemView) {
                super(itemView);
                mIconSleep = itemView.findViewById(R.id.list_item_score_sleep);
                mIconMovement = itemView.findViewById(R.id.list_item_score_movement);
                mIconImagination = itemView.findViewById(R.id.list_item_score_imagination);
                mIconLaughter = itemView.findViewById(R.id.list_item_score_laughter);
                mIconEating = itemView.findViewById(R.id.list_item_score_eating);
                mIconSpeaking = itemView.findViewById(R.id.list_item_score_speaking);

                itemView.setOnClickListener(this);
            }

            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick called");
                int position = getAdapterPosition(); // find out where this view is in the list
                // gets dashboard item at views position, then gets the fragment out of it and loads it

                            // load graph?
            }
        }

        /*
         * Passes the list data for use by the system
         */
        public DashboardAdapter(List<Score> dashboardListData) {
            mDashboardListData = dashboardListData;
        }

        /**
         * Creates new views for each list item.
         * Specify what view layout is used here.
         */
        public DashboardListFragment.DashboardAdapter.DashboardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {              // ----- what is the role of viewType here?

            // create one new view
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.list_item_score, parent, false);

            DashboardListFragment.DashboardAdapter.DashboardViewHolder vh = new DashboardAdapter.DashboardViewHolder(v);
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
        public void onBindViewHolder(@NonNull DashboardListFragment.DashboardAdapter.DashboardViewHolder holder, int position) {







            // ------ implement set date                                                                -----

            holder.mIconSleep.setBackground(getResources().getDrawable(
                    Score.getBackgroundID(mDashboardListData.get(position).getSleepScore())));
            holder.mIconMovement.setBackground(getResources().getDrawable(
                    Score.getBackgroundID(mDashboardListData.get(position).getMovementScore())));
            holder.mIconImagination.setBackground(getResources().getDrawable(
                    Score.getBackgroundID(mDashboardListData.get(position).getImaginationScore())));
            holder.mIconLaughter.setBackground(getResources().getDrawable(
                    Score.getBackgroundID(mDashboardListData.get(position).getLaughterScore())));
            holder.mIconEating.setBackground(getResources().getDrawable(
                    Score.getBackgroundID(mDashboardListData.get(position).getEatingScore())));
            holder.mIconSpeaking.setBackground(getResources().getDrawable(
                    Score.getBackgroundID(mDashboardListData.get(position).getSpeakingScore())));


        }

        /**
         * Required by recycler view
         * * returns size of dataset
         */
        @Override
        public int getItemCount() {
            return mDashboardListData.size();
        }
    }
}
