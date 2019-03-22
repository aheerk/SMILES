package macewan_dust.smiles;


import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.github.mikephil.charting.charts.Chart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.formatter.PercentFormatter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class MonthlyGraphFragment extends Fragment {

    private static final String TAG = "MonthlyGraph";

    private ScoringLab mScoringLab;
    private Button mLastMonthButton;
    private Button mNextMonthButton;
    private TextView mCurrentMonthText;
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
    private String[] mMonthStrings;
    int monthIndex;

    private int mYear;

    //   BarChart mGraph;
    PieChart mPieChart;
    // ArrayList<BarEntry> mBarEntry ;
    //  ArrayList<String> mBarEntryLabels;
    // BarDataSet mBarDataSet;
    // BarData mBarData;
    // int[] mColorSet;


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
        mMonthStrings = getContext().getResources().getStringArray(R.array.month_array);

        // colors
        // ref: https://stackoverflow.com/questions/29888850/mpandroidchart-set-different-color-to-bar-in-a-bar-chart-based-on-y-axis-values
//        mColorSet = new int[]{ContextCompat.getColor(getContext(), R.color.colorBalanced),
//                ContextCompat.getColor(getContext(), R.color.colorUnbalanced),
//                ContextCompat.getColor(getContext(), R.color.colorOver),
//                ContextCompat.getColor(getContext(), R.color.colorUnder)
//        };

        List<Integer> temp = new LinkedList<>();

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

        getActivity().setTitle(R.string.title_monthly_graph);

        mSleepCheckbox = v.findViewById(R.id.monthly_sleep_checkbox);
        mMovementCheckbox = v.findViewById(R.id.monthly_movement_checkbox);
        mImaginationCheckbox = v.findViewById(R.id.monthly_imagination_checkbox);
        mLaughterCheckbox = v.findViewById(R.id.monthly_laughter_checkbox);
        mEatingCheckbox = v.findViewById(R.id.monthly_eating_checkbox);
        mSpeakingCheckbox = v.findViewById(R.id.monthly_speaking_checkbox);

        mLastMonthButton = v.findViewById(R.id.monthly_last_button);
        mCurrentMonthText = v.findViewById(R.id.monthly_current_month);
        mNextMonthButton = v.findViewById(R.id.monthly_next_button);

        // Gregorian Calandar reference:
        // https://developer.android.com/reference/java/util/GregorianCalendar
        mGraphDate = new Date(); // today
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(mGraphDate);
        // moves the month up by one in the calendar to have the correct starting month
        calendar.roll(Calendar.MONTH, true);
        mYear = calendar.get(Calendar.YEAR);
        monthIndex = calendar.get(Calendar.MONTH) - 1; // month - 1 to make an index
        //   Log.d(TAG, "Month Index: " + monthIndex);

        //   mGraph = v.findViewById(R.id.bar_graph);
        // mGraph.animateY(300);
        //   mGraph.setDescription("");


// Ref: https://www.studytutorial.in/android-pie-chart-using-mpandroid-library-tutorial
        mPieChart = v.findViewById(R.id.pie_graph);
        mPieChart.setNoDataText(getString(R.string.pie_chart_no_data));

        mPieChart.setUsePercentValues(true);
        mPieChart.setDescription("");
        mPieChart.setDrawSlicesUnderHole(true);
        mPieChart.setHoleRadius(20f);
        mPieChart.setTransparentCircleRadius(25f);
        mPieChart.getLegend().setEnabled(false); // disables useless color squares

        updateMonthlyGraph();


        mSleepCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                updateMonthlyGraph();
            }
        });
        mMovementCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                updateMonthlyGraph();
            }
        });
        mImaginationCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                updateMonthlyGraph();
            }
        });
        mLaughterCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                updateMonthlyGraph();
            }
        });
        mEatingCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                updateMonthlyGraph();
            }
        });
        mSpeakingCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                updateMonthlyGraph();
            }
        });

        // note that 11 is December. 0 is January
        mLastMonthButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (monthIndex > 0) {
                    monthIndex = (monthIndex - 1) % 12;
                } else {
                    monthIndex = 11;
                    mYear--;
                }
                updateMonthlyGraph();
            }
        });
        mNextMonthButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (monthIndex == 11) {
                    mYear++;
                }
                monthIndex = (monthIndex + 1) % 12;
                updateMonthlyGraph();
            }
        });

        return v;
    }

    /**
     * refresh data in graph
     */
    private void updateMonthlyGraph() {
        mCurrentMonthText.setText(mMonthStrings[monthIndex] + ", " + mYear);

        mMonthData = mScoringLab.monthScores(mMonthStrings[monthIndex], String.valueOf(mYear));
        Log.d(TAG, "month: " + mMonthStrings[monthIndex] + "\n year: " + String.valueOf(mYear));
        countScores();

        // reset values
//        mBarEntry = new ArrayList<>();
//        mBarEntryLabels = new ArrayList<String>();
//
//
//        mBarEntry.add(new BarEntry(mBalanced, 0));
//        mBarEntry.add(new BarEntry(mUnbalanced, 1));
//        mBarEntry.add(new BarEntry(mOver, 2));
//        mBarEntry.add(new BarEntry(mUnder, 3));
//
//
//        mBarDataSet = new BarDataSet(mBarEntry, "");
//        mBarDataSet.setColors(mColorSet);
//        AddValuesToBarEntryLabels();
//        mBarData = new BarData(mBarEntryLabels, mBarDataSet);
//
//
//        //  mGraph.notifyDataSetChanged();
//
//        if (mGraph.getBarData() != null) {
//            mGraph.clearValues(); // also invalidates
//        }
//        mGraph.setData(mBarData);

        /*



        mGraph.removeAllSeries();
        mGraph.addSeries(mGraphData);
        mGraph.onDataChanged(false, true);

        */

        // setting values only if they are not zero to avoid having 0.0% on the graph
        ArrayList<Entry> pieDataSlices = new ArrayList<>();
        if (mBalanced > 0)
            pieDataSlices.add(new Entry(mBalanced, 0));
        if (mUnbalanced > 0)
            pieDataSlices.add(new Entry(mUnbalanced, 1));
        if (mOver > 0)
            pieDataSlices.add(new Entry(mOver, 2));
        if (mUnder > 0)
            pieDataSlices.add(new Entry(mUnder, 3));

        PieDataSet dataSet = new PieDataSet(pieDataSlices, "");
        ArrayList<String> xVals = new ArrayList<String>();

        // data labels are not wanted but required. the list size must match the data size.
        if (mBalanced > 0)
            xVals.add("");
        if (mUnbalanced > 0)
            xVals.add("");
        if (mOver > 0)
            xVals.add("");
        if (mUnder > 0)
            xVals.add("");

        // must specify the colors for the included data types
        List<Integer> pieColors = new LinkedList<Integer>();
        if (mBalanced > 0)
            pieColors.add(getResources().getColor(R.color.colorBalanced));
        if (mUnbalanced > 0)
            pieColors.add(getResources().getColor(R.color.colorUnbalanced));
        if (mOver > 0)
            pieColors.add(getResources().getColor(R.color.colorOver));
        if (mUnder > 0)
            pieColors.add(getResources().getColor(R.color.colorUnder));

        dataSet.setColors(pieColors);

        PieData data = new PieData(xVals, dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(24f);

        if (mPieChart.getData() != null) {
            mPieChart.clearValues(); // also invalidates
        }

        mPieChart.setData(data);



        mPieChart.setNoDataText("No Data Available");
     //   val paint:Paint =  mPieChart.getPaint(Chart.PAINT_INFO);
      //  paint.textSize = 40f;
        mPieChart.invalidate();
    }

    private void countScores() {

        mBalanced = 0;
        mUnbalanced = 0;
        mOver = 0;
        mUnder = 0;

        for (Score s : mMonthData) {

            if (mSleepCheckbox.isChecked()) {
                switch (s.getSleepScore()) {
                    case (ScoringAlgorithms.SCORE_BALANCED):
                        mBalanced++;
                        break;
                    case (ScoringAlgorithms.SCORE_OVER):
                        mOver++;
                        break;
                    case (ScoringAlgorithms.SCORE_UNDER):
                        mUnder++;
                        break;
                    case (ScoringAlgorithms.SCORE_UNBALANCED):
                        mUnbalanced++;
                        break;
//                case (ScoringAlgorithms.SCORE_NO_DATA):
//                    mNoData ++;
//                    break;
                }
            }
            if (mMovementCheckbox.isChecked()) {
                switch (s.getMovementScore()) {
                    case (ScoringAlgorithms.SCORE_BALANCED):
                        mBalanced++;
                        break;
                    case (ScoringAlgorithms.SCORE_OVER):
                        mOver++;
                        break;
                    case (ScoringAlgorithms.SCORE_UNDER):
                        mUnder++;
                        break;
                    case (ScoringAlgorithms.SCORE_UNBALANCED):
                        mUnbalanced++;
                        break;
//                case (ScoringAlgorithms.SCORE_NO_DATA):
//                    mNoData ++;
//                    break;
                }
            }
            if (mImaginationCheckbox.isChecked()) {
                switch (s.getImaginationScore()) {
                    case (ScoringAlgorithms.SCORE_BALANCED):
                        mBalanced++;
                        break;
                    case (ScoringAlgorithms.SCORE_OVER):
                        mOver++;
                        break;
                    case (ScoringAlgorithms.SCORE_UNDER):
                        mUnder++;
                        break;
                    case (ScoringAlgorithms.SCORE_UNBALANCED):
                        mUnbalanced++;
                        break;
//                case (ScoringAlgorithms.SCORE_NO_DATA):
//                    mNoData ++;
//                    break;
                }
            }
            if (mLaughterCheckbox.isChecked()) {
                switch (s.getLaughterScore()) {
                    case (ScoringAlgorithms.SCORE_BALANCED):
                        mBalanced++;
                        break;
                    case (ScoringAlgorithms.SCORE_OVER):
                        mOver++;
                        break;
                    case (ScoringAlgorithms.SCORE_UNDER):
                        mUnder++;
                        break;
                    case (ScoringAlgorithms.SCORE_UNBALANCED):
                        mUnbalanced++;
                        break;
//                case (ScoringAlgorithms.SCORE_NO_DATA):
//                    mNoData ++;
//                    break;
                }
            }
            if (mEatingCheckbox.isChecked()) {
                switch (s.getEatingScore()) {
                    case (ScoringAlgorithms.SCORE_BALANCED):
                        mBalanced++;
                        break;
                    case (ScoringAlgorithms.SCORE_OVER):
                        mOver++;
                        break;
                    case (ScoringAlgorithms.SCORE_UNDER):
                        mUnder++;
                        break;
                    case (ScoringAlgorithms.SCORE_UNBALANCED):
                        mUnbalanced++;
                        break;
//                case (ScoringAlgorithms.SCORE_NO_DATA):
//                    mNoData ++;
//                    break;
                }
            }
            if (mSpeakingCheckbox.isChecked()) {
                switch (s.getSpeakingScore()) {
                    case (ScoringAlgorithms.SCORE_BALANCED):
                        mBalanced++;
                        break;
                    case (ScoringAlgorithms.SCORE_OVER):
                        mOver++;
                        break;
                    case (ScoringAlgorithms.SCORE_UNDER):
                        mUnder++;
                        break;
                    case (ScoringAlgorithms.SCORE_UNBALANCED):
                        mUnbalanced++;
                        break;
//                case (ScoringAlgorithms.SCORE_NO_DATA):
//                    mNoData ++;
//                    break;
                }
            }
        }
        Log.d(TAG, "Aggragate scores\nBalanced: " + mBalanced + "\nOver: " + mOver + "\nUnder: " + mUnder +
                "\nUnbalanced: " + mUnbalanced);
    }
}
