package macewan_dust.smiles;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class DashboardListFragment extends Fragment {

    FloatingActionButton mFloatingButtonStartDailyQuestions;
    private static final String TAG = "Dashboard";

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mDashboardRecyclerViewAdapter;
    private RecyclerView.LayoutManager mRecyclerViewLayoutManager;
    private List<Score> mDashboardData = new LinkedList<>();
    private Button mButtonStartQuestions;
    private Button mButtonNewUserTest;


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


        // scores from database
        mDashboardData = ScoringLab.get(getContext()).getScores();
/*
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
        */

    }

    @Override
    public void onResume() {                                                                 ////////// not working
        super.onResume();

        Log.d(TAG, "resuming dashboard");

        // redraw the screen when coming back from a question page
        mDashboardData = ScoringLab.get(getContext()).getScores(); // refresh list on resume


        Log.d(TAG, "data: " + mDashboardData);  // list is updating


        mDashboardRecyclerViewAdapter.notifyDataSetChanged(); // fails. supposed to update the views.
        //mRecyclerView.invalidate();
        //this.getView().invalidate();

/* // fails. infinite loops
        // All above recommended methods of refreshing the dashboard failed. The only method
        // that works is pressing the dashboard navigation button which creates the new fragment
        // again. This code does the same thing.
        Fragment newFragment = new DashboardListFragment();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, newFragment);
        //transaction.addToBackStack("dashboard");
        transaction.commit();
*/


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

        // setup for delete on swipe
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new SwipeToDeleteCallback((DashboardAdapter) mDashboardRecyclerViewAdapter));
        itemTouchHelper.attachToRecyclerView(mRecyclerView);


        /*
        mFloatingButtonStartDailyQuestions = v.findViewById(R.id.button_start_daily_questions);
        mFloatingButtonStartDailyQuestions.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v){
                DailyListFragment fragment = new DailyListFragment();
                replaceFragment(fragment);
            }
        });*/

        mButtonStartQuestions = v.findViewById(R.id.button_start_daily_questions);
        mButtonStartQuestions.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v){
                DailyListFragment fragment = new DailyListFragment();
                replaceFragment(fragment);
            }
        });

        getActivity().setTitle(R.string.title_dashboard);


        mButtonNewUserTest = v.findViewById(R.id.button_new_user_test);
        mButtonNewUserTest.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v){
                NewUserFragment fragment = new NewUserFragment();
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
        transaction.addToBackStack("dashboard");
        transaction.commit();
        Log.d(TAG, "replacing fragment");
    }

    // ----------- recycler view methods


    public class DashboardAdapter extends RecyclerView.Adapter<DashboardListFragment.DashboardAdapter.DashboardViewHolder> {

        private List<Score> mDashboardListData;

        // variables for swipe delete
        int mDeletedItemPosition;
        Score mDeletedItem;

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

            // format date to exclude time
            String tempDate = mDashboardListData.get(position).getDate();
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
         * @param position
         */
        public void deleteItem(int position) {
            mDeletedItem = mDashboardListData.get(position); // saved for undo
            mDeletedItemPosition = position;
            mDashboardListData.remove(position);
            ScoringLab.get(getContext()).deleteScore(mDeletedItem); // delete from database
            notifyItemRemoved(position);
            showUndoSnackbar();

        }

        public void showUndoSnackbar() {
            View view = getActivity().findViewById(R.id.dashboard_recycler_view);
            Snackbar snackbar = Snackbar.make(view, R.string.undo_text, Snackbar.LENGTH_LONG);

            snackbar.setAction(R.string.undo_confirm, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DashboardAdapter.this.undoDelete();
                }
            });
            snackbar.show();
        }

        private void undoDelete() {
            mDashboardListData.add(mDeletedItemPosition, mDeletedItem);
            notifyItemInserted(mDeletedItemPosition);
            ScoringLab.get(getContext()).addScore(mDeletedItem);
        }
    }

    /**
     * This class detects swipes on recycler view items and is customized for deleting items.
     * Reference: https://medium.com/@zackcosborn/step-by-step-recyclerview-swipe-to-delete-and-undo-7bbae1fce27e
     */
    public class SwipeToDeleteCallback extends ItemTouchHelper.SimpleCallback {

        private DashboardAdapter mAdapter;

     //   private Drawable icon;
    //    private final ColorDrawable background;

        /**
         * constructor
         * @param adapter - recycler view's adapter
         */
        public SwipeToDeleteCallback(DashboardAdapter adapter) {
            super(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT);
            mAdapter = adapter;

      //      icon = ContextCompat.getDrawable(mAdapter.get , R.drawable.ic_delete_white);
      //      background = new ColorDrawable(Color.RED);

        }

        /*
        @Override
        public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
            super.onChildDraw(c, recyclerView, viewHolder, dX,
                    dY, actionState, isCurrentlyActive);
            View itemView = viewHolder.itemView;
            int backgroundCornerOffset = 20;
        }
*/


        /**
         * required but not being used
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
