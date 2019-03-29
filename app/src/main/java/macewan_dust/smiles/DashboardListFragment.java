package macewan_dust.smiles;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.LinkedList;
import java.util.List;

public class DashboardListFragment extends Fragment {

    //   FloatingActionButton mFloatingButtonStartDailyQuestions;
    private static final String TAG = "Dashboard";

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mDashboardRecyclerViewAdapter;
    private RecyclerView.LayoutManager mRecyclerViewLayoutManager;
    private List<Score> mDashboardData = new LinkedList<>();
    private Button mButtonStartQuestions;
    private Context mContext;
    private ScoringLab mScoringLab;

    private LinearLayout mDailyChallengeLayout;
    private LinearLayout mDailyWebLayout;
    private SharedPreferences mPref;


    /**
     * new instance constructor
     *
     * @return InputSleepFragment
     */
    public static DashboardListFragment newInstance() {
        return new DashboardListFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getContext();

        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        mScoringLab = ScoringLab.get(getContext());
        // scores from database
        mDashboardData = mScoringLab.getScores();

        mPref = getActivity().getApplicationContext().getSharedPreferences(SettingsFragment.SETTINGS, 0);

        newUserStart();
/*
        // generating some items for testing
        Score temp = new Score();
        temp.setSpeakingScore(ScoringAlgorithms.SCORE_BALANCED);
        temp.setLaughterScore(ScoringAlgorithms.SCORE_BALANCED);
        mDashboardData.add(temp);

        Score temp2 = new Score();
        temp2.setSpeakingScore(ScoringAlgorithms.SCORE_UNDER);
        temp2.setLaughterScore(ScoringAlgorithms.SCORE_UNDER);
        temp2.setMovementScore(ScoringAlgorithms.SCORE_BALANCED);
        mDashboardData.add(temp2);
        */

    }

    @Override
    public void onResume() {
        super.onResume();

        Log.d(TAG, "resuming dashboard");

        // redraw the screen when coming back from a question page
        mDashboardData = mScoringLab.getScores(); // refresh list on resume

        ((DashboardAdapter) mDashboardRecyclerViewAdapter).setDashboardListData(mDashboardData);
        mDashboardRecyclerViewAdapter.notifyDataSetChanged();

        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);
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
        mRecyclerView.setHasFixedSize(false);

        mDailyChallengeLayout = v.findViewById(R.id.daily_challenge_layout);
        mDailyWebLayout = v.findViewById(R.id.daily_web_layout);


        dailyWeb();

        dailyChallenge();


        // setup for delete on swipe
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new SwipeToDeleteCallback((DashboardAdapter) mDashboardRecyclerViewAdapter));
        itemTouchHelper.attachToRecyclerView(mRecyclerView);



        mButtonStartQuestions = v.findViewById(R.id.button_start_daily_questions);
        mButtonStartQuestions.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                DailyListFragment fragment = new DailyListFragment();
                replaceFragment(fragment);
            }
        });

        getActivity().setTitle(R.string.title_dashboard);

        return v;
    }

    /**
     * starts the new user info once.
     */
    private void newUserStart(){
        if (mPref.getBoolean(NewUserFragment.NEW_USER, true)) {
            Intent newActivity = new Intent(getContext(), NewUserActivity.class);
            getContext().startActivity(newActivity);
        }
    }

    private void dailyChallenge() {


        if (mPref.getBoolean(SettingsFragment.PREF_DAILY_CHALLENGE, true)) {

            mDailyChallengeLayout.setVisibility(View.VISIBLE);


/*
implement challenge code
 */


        } else {
            mDailyChallengeLayout.setVisibility(View.GONE);
        }


    }

    private void dailyWeb() {
        if (mPref.getBoolean(SettingsFragment.PREF_DAILY_WEB, true)) {
            mDailyWebLayout.setVisibility(View.VISIBLE);


            /*
            implement web code
            */


        } else {
            mDailyWebLayout.setVisibility(View.GONE);
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
        transaction.addToBackStack("dashboard");
        transaction.commit();
        Log.d(TAG, "replacing fragment");
    }

    // ----------- recycler view methods
    public class DashboardAdapter extends RecyclerView.Adapter<DashboardListFragment.DashboardAdapter.DashboardViewHolder> {

        private List<Score> mDashboardListData;

        // variables for swipe delete
        int mDeletedItemPosition;
        Score mDeletedScore;
        Raw mDeletedRaw;

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
                mDate = itemView.findViewById(R.id.list_item_score_date);

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
         * used to update the data in the adapter. pass data with this method and then call
         * the notifyDataSetChanged method to have the adapter use the new data.
         *
         * @param dashboardListData
         */
        public void setDashboardListData(List<Score> dashboardListData) {
            mDashboardListData = dashboardListData;
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

            // keeps the icon from stretching as the screen size changes
            holder.mIconSleep.setScaleType(ImageView.ScaleType.FIT_CENTER);
            holder.mIconMovement.setScaleType(ImageView.ScaleType.FIT_CENTER);
            holder.mIconImagination.setScaleType(ImageView.ScaleType.FIT_CENTER);
            holder.mIconLaughter.setScaleType(ImageView.ScaleType.FIT_CENTER);
            holder.mIconEating.setScaleType(ImageView.ScaleType.FIT_CENTER);
            holder.mIconSpeaking.setScaleType(ImageView.ScaleType.FIT_CENTER);

            // format date to exclude time
            String tempDate = mDashboardListData.get(position).getDateString();
            // String tempStringDate = DateFormat.getDateInstance(DateFormat.MEDIUM).format(tempDate);
            holder.mDate.setText(tempDate);
        }

        /**
         * Required by recycler view
         * * returns size of dataset
         */
        @Override
        public int getItemCount() {
            return mDashboardListData.size();
        }

        /**
         * this method is for the swipe on delete feature
         * Reference: https://medium.com/@zackcosborn/step-by-step-recyclerview-swipe-to-delete-and-undo-7bbae1fce27e
         *
         * @param position
         */
        public void deleteItem(int position) {
            mDeletedScore = mDashboardListData.get(position); // saved for undo
            mDeletedItemPosition = position;
            mDashboardListData.remove(position);
            mScoringLab.deleteScore(mDeletedScore); // delete from database

            // if a raw object exists with the same date as the score
            if (mScoringLab.isRaw(mDeletedScore.getDate())) {
                // save that raw item here.
                mDeletedRaw = mScoringLab.getRaw(mDeletedScore.getDate());
                // then delete it from the database and list.
                mScoringLab.deleteRaw(mDeletedRaw);
            }
            notifyItemRemoved(position);
            showUndoSnackbar();

        }

        public void showUndoSnackbar() {
            View view = getActivity().findViewById(R.id.dashboard_recycler_view);
            Snackbar snackbar = Snackbar.make(view, R.string.undo_text, 5000); // 5 seconds

            snackbar.setAction(R.string.undo_confirm, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DashboardAdapter.this.undoDelete();
                }
            });
            snackbar.show();
        }

        private void undoDelete() {
            // add everything back to the database, list and view
            mScoringLab.addScore(mDeletedScore);
            mScoringLab.addRaw(mDeletedRaw);
            mDashboardListData = mScoringLab.getScores();
            //notifyItemInserted(mDeletedItemPosition);
            notifyDataSetChanged();
        }
    }

    /**
     * This class detects swipes on recycler view items and is customized for deleting items.
     * Reference: https://medium.com/@zackcosborn/step-by-step-recyclerview-swipe-to-delete-and-undo-7bbae1fce27e
     * Note the reference has a bug for right swipe. fix by checking right and left dimensions on icon
     */
    public class SwipeToDeleteCallback extends ItemTouchHelper.SimpleCallback {

        private DashboardAdapter mAdapter;

        private Drawable mDeleteIcon;
        private final ColorDrawable mDeleteBackground;

        //   private Drawable icon;
        //    private final ColorDrawable background;

        /**
         * constructor
         *
         * @param adapter - recycler view's adapter
         */
        public SwipeToDeleteCallback(DashboardAdapter adapter) {
            super(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT);
            mAdapter = adapter;

            mDeleteIcon = ContextCompat.getDrawable(mContext, R.drawable.ic_delete);
            mDeleteBackground = new ColorDrawable(getResources().getColor(R.color.colorDelete));
        }

        /**
         * Override to draw on recycler view
         *
         * @param c                 - canvas
         * @param recyclerView
         * @param viewHolder
         * @param dX
         * @param dY
         * @param actionState
         * @param isCurrentlyActive
         */
        @Override
        public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder,
                                float dX, float dY, int actionState, boolean isCurrentlyActive) {
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);

            View v = viewHolder.itemView;
            int backgroundCornerOffset = 0; // pushes background behind the parent view. looks better if its off

            // icon variables across both swipes
            // height of view = height of icon / 2 sides
            int iconMargin = (v.getHeight() - mDeleteIcon.getIntrinsicHeight()) / 2;
            int iconTop = v.getTop() + iconMargin; // top of view + margin (x y axis move down and to the right
            int iconBottom = iconTop + mDeleteIcon.getIntrinsicHeight();

            // Right swipe
            if (dX > 0) {
                // right and left are specific to which swipe is being done
                int iconRight = v.getLeft() + iconMargin + mDeleteIcon.getIntrinsicWidth();
                int iconLeft = v.getLeft() + iconMargin;
                mDeleteIcon.setBounds(iconLeft, iconTop, iconRight, iconBottom);
                mDeleteBackground.setBounds(v.getLeft(), v.getTop() + iconMargin / 4,
                        v.getLeft() + ((int) dX) + backgroundCornerOffset, v.getBottom() - iconMargin / 4);
            } else if (dX < 0) { // left swipe
                // right and left are specific to which swipe is being done
                int iconLeft = v.getRight() - iconMargin - mDeleteIcon.getIntrinsicWidth();
                int iconRight = v.getRight() - iconMargin;
                mDeleteIcon.setBounds(iconLeft, iconTop, iconRight, iconBottom);
                mDeleteBackground.setBounds(v.getRight() + ((int) dX) - backgroundCornerOffset,
                        v.getTop() + iconMargin / 4, v.getRight(), v.getBottom() - iconMargin / 4);
            } else { // no swipe
                mDeleteBackground.setBounds(0, 0, 0, 0);
            }
            mDeleteBackground.draw(c);
            mDeleteIcon.draw(c);
        }

        /**
         * required but not being used
         *
         * @param recyclerView
         * @param viewHolder
         * @param target
         * @return
         */
        @Override
        public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
            int position = viewHolder.getAdapterPosition();
            mAdapter.deleteItem(position);
        }
    }
}
