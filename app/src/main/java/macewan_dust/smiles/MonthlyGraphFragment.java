package macewan_dust.smiles;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class MonthlyGraphFragment extends Fragment {

    private static final String TAG = "MonthlyGraph";

    private ScoringLab mScoringLab;
    private Button mLastMonth;
    private Button mNextMonth;
    private Date mGraphDate;
    private CheckBox mSleepCheckbox;
    private CheckBox mMovementCheckbox;
    private CheckBox mImaginationCheckbox;
    private CheckBox mLaughterCheckbox;
    private CheckBox mEatingCheckbox;
    private CheckBox mSpeakingCheckbox;

    // aggragate data for the month
    private int mBalanced;
    private int mUnbalanced;
    private int mOver;
    private int mUnder;
   // private int mNoData;  // if no data is done, days with no score must be determined as well

    private List<Score> mMonthData;

    /**
     * new instance constructor
     *
     * @return WeeklyGraphFragment
     */
    public MonthlyGraphFragment newInstance() {
        return new MonthlyGraphFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        mScoringLab = ScoringLab.get(getContext());

        mBalanced = 0;
        mUnbalanced = 0;
        mOver = 0;
        mUnder = 0;
      //  mNoData = 0;
    }

    @Override
    public void onResume() {
        super.onResume();
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_monthly_graph, null);

        mGraphDate = new Date();
        //     mMonthData =

        getActivity().setTitle(R.string.title_monthly_graph);

        mSleepCheckbox = v.findViewById(R.id.monthly_sleep_checkbox);
        mMovementCheckbox = v.findViewById(R.id.monthly_movement_checkbox);
        mImaginationCheckbox = v.findViewById(R.id.monthly_imagination_checkbox);
        mLaughterCheckbox = v.findViewById(R.id.monthly_laughter_checkbox);
        mEatingCheckbox = v.findViewById(R.id.monthly_eating_checkbox);
        mSpeakingCheckbox = v.findViewById(R.id.monthly_speaking_checkbox);

        mLastMonth = v.findViewById(R.id.monthly_last_button);
        mNextMonth = v.findViewById(R.id.monthly_next_button);

        updateMonthlyGraph();


   //     Log.d(TAG, "March 2019 scores\n" + mMonthData);

Log.d(TAG, "Aggragate scores\nBalanced: " + mBalanced + "\nOver: " + mOver + "\nUnder: " + mUnder +
        "\nUnbalanced: "+ mUnbalanced);

        return v;
    }


    /**
     * refresh data in graph
     */
    private void updateMonthlyGraph() {
        mMonthData = mScoringLab.monthScores("Mar",  "2019");
        countScores();
    }


    private void countScores(){

        for (Score s : mMonthData){
            switch (s.getSleepScore()){
                case (ScoringAlgorithms.SCORE_BALANCED):
                    mBalanced ++;
                    break;
                case (ScoringAlgorithms.SCORE_OVER):
                    mOver ++;
                    break;
                case (ScoringAlgorithms.SCORE_UNDER):
                    mUnder ++;
                    break;
                case (ScoringAlgorithms.SCORE_UNBALANCED):
                    mUnbalanced ++;
                    break;
//                case (ScoringAlgorithms.SCORE_NO_DATA):
//                    mNoData ++;
//                    break;
            }
            switch (s.getMovementScore()){
                case (ScoringAlgorithms.SCORE_BALANCED):
                    mBalanced ++;
                    break;
                case (ScoringAlgorithms.SCORE_OVER):
                    mOver ++;
                    break;
                case (ScoringAlgorithms.SCORE_UNDER):
                    mUnder ++;
                    break;
                case (ScoringAlgorithms.SCORE_UNBALANCED):
                    mUnbalanced ++;
                    break;
//                case (ScoringAlgorithms.SCORE_NO_DATA):
//                    mNoData ++;
//                    break;
            }
            switch (s.getImaginationScore()){
                case (ScoringAlgorithms.SCORE_BALANCED):
                    mBalanced ++;
                    break;
                case (ScoringAlgorithms.SCORE_OVER):
                    mOver ++;
                    break;
                case (ScoringAlgorithms.SCORE_UNDER):
                    mUnder ++;
                    break;
                case (ScoringAlgorithms.SCORE_UNBALANCED):
                    mUnbalanced ++;
                    break;
//                case (ScoringAlgorithms.SCORE_NO_DATA):
//                    mNoData ++;
//                    break;
            }
            switch (s.getLaughterScore()){
                case (ScoringAlgorithms.SCORE_BALANCED):
                    mBalanced ++;
                    break;
                case (ScoringAlgorithms.SCORE_OVER):
                    mOver ++;
                    break;
                case (ScoringAlgorithms.SCORE_UNDER):
                    mUnder ++;
                    break;
                case (ScoringAlgorithms.SCORE_UNBALANCED):
                    mUnbalanced ++;
                    break;
//                case (ScoringAlgorithms.SCORE_NO_DATA):
//                    mNoData ++;
//                    break;
            }
            switch (s.getEatingScore()){
                case (ScoringAlgorithms.SCORE_BALANCED):
                    mBalanced ++;
                    break;
                case (ScoringAlgorithms.SCORE_OVER):
                    mOver ++;
                    break;
                case (ScoringAlgorithms.SCORE_UNDER):
                    mUnder ++;
                    break;
                case (ScoringAlgorithms.SCORE_UNBALANCED):
                    mUnbalanced ++;
                    break;
//                case (ScoringAlgorithms.SCORE_NO_DATA):
//                    mNoData ++;
//                    break;
            }
            switch (s.getSpeakingScore()){
                case (ScoringAlgorithms.SCORE_BALANCED):
                    mBalanced ++;
                    break;
                case (ScoringAlgorithms.SCORE_OVER):
                    mOver ++;
                    break;
                case (ScoringAlgorithms.SCORE_UNDER):
                    mUnder ++;
                    break;
                case (ScoringAlgorithms.SCORE_UNBALANCED):
                    mUnbalanced ++;
                    break;
//                case (ScoringAlgorithms.SCORE_NO_DATA):
//                    mNoData ++;
//                    break;
            }
        }


    }


}
