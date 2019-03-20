package macewan_dust.smiles;


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


import java.util.ArrayList;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.Calendar;
import java.util.Date;
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
    private String[] mMonths;
    int monthIndex;

    private int mYear;

    BarChart mGraph ;
    ArrayList<BarEntry> mBarEntry ;
    ArrayList<String> mBarEntryLabels;
    BarDataSet mBarDataSet;
    BarData mBarData;

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

        mMonths = getContext().getResources().getStringArray(R.array.month_array);






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



        mGraph = v.findViewById(R.id.bar_graph);

        mBarEntry = new ArrayList<>();

        mBarEntryLabels = new ArrayList<String>();

        AddValuesToBARENTRY();

        AddValuesToBarEntryLabels();

        mBarDataSet = new BarDataSet(mBarEntry, "Projects");

        mBarData = new BarData(mBarEntryLabels, mBarDataSet);

        mBarDataSet.setColors(ColorTemplate.COLORFUL_COLORS);

        mGraph.setData(mBarData);

        mGraph.animateY(3000);


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
                }
                else {
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
                    mYear ++;
                }
                monthIndex = (monthIndex + 1) % 12;
                updateMonthlyGraph();
            }
        });





        return v;
    }

    public void AddValuesToBARENTRY(){

        mBarEntry.add(new BarEntry(2f, 0));
        mBarEntry.add(new BarEntry(4f, 1));
        mBarEntry.add(new BarEntry(6f, 2));
        mBarEntry.add(new BarEntry(8f, 3));
        mBarEntry.add(new BarEntry(7f, 4));
        mBarEntry.add(new BarEntry(3f, 5));

    }

    public void AddValuesToBarEntryLabels(){

        mBarEntryLabels.add("January");
        mBarEntryLabels.add("February");
        mBarEntryLabels.add("March");
        mBarEntryLabels.add("April");
        mBarEntryLabels.add("May");
        mBarEntryLabels.add("June");

    }



    /**
     * refresh data in graph
     */

    private void updateMonthlyGraph() {
        /*
        mCurrentMonthText.setText(mMonths[monthIndex] + ", " + mYear);
        Log.d(TAG, "month: " + mMonths[monthIndex] + "\n year: " + String.valueOf(mYear));
        mMonthData = mScoringLab.monthScores(mMonths[monthIndex], String.valueOf(mYear));
        countScores();

        //Setting data to the bar graph
        BarGraphSeries<DataPoint> mGraphData = new BarGraphSeries<>(new DataPoint[] {
                new DataPoint(0, 0 ),
                new DataPoint(1, mBalanced),
                new DataPoint(2, mUnbalanced),
                new DataPoint(3, mOver),
                new DataPoint(4, mUnder),
                new DataPoint(5, 0 ),
        });

        Paint colors = new Paint();
        colors.setColor(getResources().getColor(R.color.colorLow));
        colors.setColor(getResources().getColor(R.color.colorHigh));
        mGraphData.setCustomPaint(colors);

        mGraphData.setDataWidth(0.7);
        mGraph.removeAllSeries();
        mGraph.addSeries(mGraphData);


        mGraph.onDataChanged(false, true);

*/
    }

    private void countScores(){

        mBalanced = 0;
        mUnbalanced = 0;
        mOver = 0;
        mUnder = 0;

        for (Score s : mMonthData){

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
                "\nUnbalanced: "+ mUnbalanced);

    }


}
