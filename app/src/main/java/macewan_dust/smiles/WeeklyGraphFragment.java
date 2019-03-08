package macewan_dust.smiles;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class WeeklyGraphFragment extends Fragment {

    private static final String TAG = "WeeklyGraph";

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mRecyclerViewAdapter;
    private RecyclerView.LayoutManager mRecyclerViewLayoutManager;
    private List<Score> mWeeklyData = new LinkedList<>();
    private ScoringLab mScoringLab;


    /**
     * new instance constructor
     * @return WeeklyGraphFragment
     */
    public static WeeklyGraphFragment newInstance() {
        return new WeeklyGraphFragment();
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        mScoringLab = ScoringLab.get(getContext());
        mWeeklyData  = weekDates(new Date());

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_weekly_graph, null);

        getActivity().setTitle(R.string.title_weekly_graph);

        mRecyclerView = v.findViewById(R.id.recycler_view_weekly_graph);
        mRecyclerViewLayoutManager = new LinearLayoutManager(this.getActivity());
        mRecyclerView.setLayoutManager(mRecyclerViewLayoutManager);
        mRecyclerViewAdapter = new WeeklyGraphFragment.Adapter(mWeeklyData);
        mRecyclerView.setAdapter(mRecyclerViewAdapter);
        mRecyclerView.setHasFixedSize(true);

        return v;
    }

    /**
     * weekDates - gets 7 days of score data with the paramater date as the last day
     * @param lastDay - last day of the week of interest
     * @return - 7 days of score data
     */
    private List<Score> weekDates(Date lastDay) {
        List<Score> weekList = new LinkedList<>();
        Date tempDate;

        for (int i = 0 ; i < 7 ; i++) {
            tempDate = new Date(lastDay.getTime() - i * 86400000); // date - a day
            // use database score if it exists
            if (mScoringLab.isScore(tempDate)) {
                weekList.add(mScoringLab.getScore(tempDate));
            }
            else {
                // add blank score for the day if no data exists
                weekList.add(new Score(tempDate));
            }
        }
   //     Log.i(TAG, "temp list: " + weekList);
        return weekList;
    }

    // ----------- recycler view methods
    public class Adapter extends RecyclerView.Adapter<WeeklyGraphFragment.Adapter.WeeklyViewHolder> {

        private List<Score> mAdapterData;

        /**
         * Reference for views for each data item.
         * Set the image and text for the recycler view list here.
         * <p>
         * Optional: implementing on click listener and onClick method
         */
        public class WeeklyViewHolder extends RecyclerView.ViewHolder {
            public TextView mTextDate;
            public ImageView mIconA;
            public ImageView mIconB;
            public ImageView mIconC;
            public ImageView mIconD;
            public ImageView mIconE;
            public ImageView mIconF;

            public WeeklyViewHolder(View itemView) {
                super(itemView);
                mTextDate = itemView.findViewById(R.id.graph_weekly_score_date);
                mIconA = itemView.findViewById(R.id.graph_weekly_score_a);
                mIconB = itemView.findViewById(R.id.graph_weekly_score_b);
                mIconC = itemView.findViewById(R.id.graph_weekly_score_c);
                mIconD = itemView.findViewById(R.id.graph_weekly_score_d);
                mIconE = itemView.findViewById(R.id.graph_weekly_score_e);
                mIconF = itemView.findViewById(R.id.graph_weekly_score_f);

                // keeps the icon from stretching as the screen size changes
                /* // not working for these drawables
                mIconA.setScaleType(ImageView.ScaleType.FIT_CENTER);
                mIconB.setScaleType(ImageView.ScaleType.FIT_CENTER);
                mIconC.setScaleType(ImageView.ScaleType.FIT_CENTER);
                mIconD.setScaleType(ImageView.ScaleType.FIT_CENTER);
                mIconE.setScaleType(ImageView.ScaleType.FIT_CENTER);
                mIconF.setScaleType(ImageView.ScaleType.FIT_CENTER);
                */





            }
        }




        /*
         * Passes the list data for use by the system
         */
        public Adapter(List<Score> weeklyListData) {
            mAdapterData = weeklyListData;
        }

        /**
         * Creates new views for each list item.
         * Specify what view layout is used here.
         */
        public WeeklyGraphFragment.Adapter.WeeklyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {              // ----- what is the role of viewType here?

            // create one new view
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.list_item_weekly, parent, false);

            WeeklyGraphFragment.Adapter.WeeklyViewHolder vh = new WeeklyGraphFragment.Adapter.WeeklyViewHolder(v);
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
        public void onBindViewHolder(@NonNull WeeklyGraphFragment.Adapter.WeeklyViewHolder holder, int position) {

            holder.mIconA.setImageDrawable(getResources().getDrawable(Score.getDotID(mAdapterData.get(position).getSleepScore())));
            holder.mIconB.setImageDrawable(getResources().getDrawable(Score.getDotID(mAdapterData.get(position).getMovementScore())));
            holder.mIconC.setImageDrawable(getResources().getDrawable(Score.getDotID(mAdapterData.get(position).getImaginationScore())));
            holder.mIconD.setImageDrawable(getResources().getDrawable(Score.getDotID(mAdapterData.get(position).getLaughterScore())));
            holder.mIconE.setImageDrawable(getResources().getDrawable(Score.getDotID(mAdapterData.get(position).getEatingScore())));
            holder.mIconF.setImageDrawable(getResources().getDrawable(Score.getDotID(mAdapterData.get(position).getSpeakingScore())));
            holder.mTextDate.setText(mAdapterData.get(position).getDateString());

        }

        /**
         * Required by recycler view
         * * returns size of dataset
         */
        @Override
        public int getItemCount() {
            return mWeeklyData.size();
        }
    }


}
