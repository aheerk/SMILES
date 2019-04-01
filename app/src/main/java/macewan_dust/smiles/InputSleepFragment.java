package macewan_dust.smiles;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;

public class InputSleepFragment extends Fragment {

    private static final String TAG = "InputSleepFragment";
    private static final int NO_SELECTION = 100000;

    TextView mIcon1a;
    TextView mIcon1b;
    TextView mIcon1c;
    TextView mIcon1d;
    TextView mIcon2a;
    TextView mIcon2b;
    TextView mIcon2c;
    TextView mIcon2d;

    ImageView mWeblink1;
    WebLab mWebLab;

    Button mButton;
    TextView mResults;
    TextView mFeedback;

    int mQuestion_A_index;
    int mQuestion_B_index;

    Score mScore;
    CountDownTimer mExitCountDownTimer;
    Date mScoreDate;
    ScoringLab mScoringLab;
    Raw mRaw;


    /**
     * new instance constructor
     *
     * @return InputSleepFragment
     */
    public static InputSleepFragment newInstance() {
        return new InputSleepFragment();
    }

    /**
     * Android will call this function when a view is created. If the view is not destroyed, this
     * remains in effect (not that rotating destroys a view.
     *
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true); // prevents instance of the fragment from being destroyed on rotation.
        mScoreDate = new Date(this.getArguments().getLong(DailyListFragment.DAILY_DATE));
        Log.d(TAG, "date passed in is: " + Score.timelessDate(mScoreDate));
        mScoringLab = ScoringLab.get(getContext());
        mWebLab = WebLab.getWebLab(getContext());
    }

    /**
     * Android calls this method whenever the view is created. So if the view was on the back
     * stack, this code will be called again when it becomes visible again and calls this code again.
     *
     * @param inflater           - infaltes the view (brings it into memory)
     * @param container          - parent view
     * @param savedInstanceState - holds data
     * @return view
     */
    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_daily_sleep_questions, container, false);

      //  getActivity().setTitle(R.string.title_quest_sleep);

        mButton = v.findViewById(R.id.score_button);

        mIcon1a = v.findViewById(R.id.icon_1a);
        mIcon1b = v.findViewById(R.id.icon_1b);
        mIcon1c = v.findViewById(R.id.icon_1c);
        mIcon1d = v.findViewById(R.id.icon_1d);
        mIcon2a = v.findViewById(R.id.icon_2a);
        mIcon2b = v.findViewById(R.id.icon_2b);
        mIcon2c = v.findViewById(R.id.icon_2c);
        mIcon2d = v.findViewById(R.id.icon_2d);

        mQuestion_A_index = NO_SELECTION;
        mQuestion_B_index = NO_SELECTION;

        mFeedback = v.findViewById(R.id.text_sleep_feedback);

        mIcon1a.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearSelectedA();
                mQuestion_A_index = ScoringAlgorithms.INPUT_a;
                mIcon1a.setBackground(getResources().getDrawable(R.drawable.ic_wide_border_selected));
            }
        });

        mIcon1b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearSelectedA();
                mQuestion_A_index = ScoringAlgorithms.INPUT_b;
                mIcon1b.setBackground(getResources().getDrawable(R.drawable.ic_wide_border_selected));
            }
        });

        mIcon1c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearSelectedA();
                mQuestion_A_index = ScoringAlgorithms.INPUT_c;;
                mIcon1c.setBackground(getResources().getDrawable(R.drawable.ic_wide_border_selected));
            }
        });

        mIcon1d.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearSelectedA();
                mQuestion_A_index = ScoringAlgorithms.INPUT_d;
                mIcon1d.setBackground(getResources().getDrawable(R.drawable.ic_wide_border_selected));
            }
        });

        mIcon2a.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearSelectedB();
                mQuestion_B_index = ScoringAlgorithms.INPUT_a;
                mIcon2a.setBackground(getResources().getDrawable(R.drawable.ic_wide_border_selected));
            }
        });

        mIcon2b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearSelectedB();
                mQuestion_B_index = ScoringAlgorithms.INPUT_b;
                mIcon2b.setBackground(getResources().getDrawable(R.drawable.ic_wide_border_selected));
            }
        });

        mIcon2c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearSelectedB();
                mQuestion_B_index = ScoringAlgorithms.INPUT_c;
                mIcon2c.setBackground(getResources().getDrawable(R.drawable.ic_wide_border_selected));
            }
        });

        mIcon2d.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearSelectedB();
                mQuestion_B_index = ScoringAlgorithms.INPUT_d;
                mIcon2d.setBackground(getResources().getDrawable(R.drawable.ic_wide_border_selected));
            }
        });

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mQuestion_A_index == NO_SELECTION || mQuestion_B_index == NO_SELECTION) {
                    mResults.setText(getString(R.string.feedback_unselected));

                } else {

                    int score = ScoringAlgorithms.scoreSleep(mQuestion_A_index, mQuestion_B_index);

                    int scoreStringID = 1000; // just here to initialize
                    Log.d(TAG, "Score: " + score);
                    switch (score) {

                        case ScoringAlgorithms.SCORE_OVER:
                            scoreStringID = R.string.score_high;
                            break;
                        case ScoringAlgorithms.SCORE_UNDER:
                            scoreStringID = R.string.score_low;
                            break;
                        case ScoringAlgorithms.SCORE_BALANCED:
                            scoreStringID = R.string.score_balanced;
                            break;
                        case ScoringAlgorithms.SCORE_UNBALANCED:
                            scoreStringID = R.string.score_unbalanced;
                            break;
                        case ScoringAlgorithms.SCORE_ERROR:
                            scoreStringID = R.string.score_error;
                            Toast t1 = new Toast(getContext());
                            t1.setText("Error returned from scoring algorithm");
                            t1.show();
                            break;
                        default:
                            scoreStringID = R.string.score_error;
                            Toast t2 = new Toast(getContext());
                            t2.setText("Error in scoring");
                            t2.show();
                    }

                    // if score exists, update it, else make a new one and save it
                    if (mScoringLab.isScore(mScoreDate)){
                        // get score object to use its data
                        mScore = mScoringLab.getScore(mScoreDate);
                        // set new score for this category
                        mScore.setSleepScore(score);
                        // save score to database
                        mScoringLab.updateScore(mScore);
                        //
                    } else {
                        mScore = new Score(mScoreDate);
                        mScore.setSleepScore(score);
                        mScoringLab.addScore(mScore);
                    }

                    // update raw
                    if (mScoringLab.isRaw(mScoreDate)){
                        mRaw = mScoringLab.getRaw(mScoreDate);
                        mRaw.setSleep(mQuestion_A_index, mQuestion_B_index);
                        mScoringLab.updateRaw(mRaw);

                    }else { // add raw
                        mRaw = new Raw(mScoreDate);
                        mRaw.setSleep(mQuestion_A_index, mQuestion_B_index);
                        mScoringLab.addRaw(mRaw);
                    }
                    Log.d(TAG, "raw object: " + mRaw);

                    if (score != ScoringAlgorithms.SCORE_BALANCED){
                        mFeedback.setVisibility(View.VISIBLE);
                    } else {
                        mFeedback.setVisibility(View.INVISIBLE);
                    }

                    mResults.setText(getString(R.string.quest_results, getString(scoreStringID)));
                    exitOnLastScore();
                }
            }
        });

        mResults = v.findViewById(R.id.text_score); // note this object is invisible
        mResults.setVisibility(View.VISIBLE);

        // Open weblink when someone clicks the link icon
        mWeblink1 = v.findViewById(R.id.icon_sleep_weblink_1);
        mWeblink1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity activity = (MainActivity) getActivity();
                activity.openLink(mWebLab.getOneLink(0).getUri());
            }
        });

        setButtonsFromDatabase();
        return v;
    }


    /**
     * if there is saved raw data for this date, sets the selected buttons to match
     */
    private void setButtonsFromDatabase(){
        if (mScoringLab.isRaw(mScoreDate)) {
            mRaw = mScoringLab.getRaw(mScoreDate);

            clearSelectedA();
            clearSelectedB();
            if (mRaw.getSleep1()!= 0) {
                mQuestion_A_index = mRaw.getSleep1();
                switch (mQuestion_A_index) {
                    case ScoringAlgorithms.INPUT_a:
                        mIcon1a.setBackground(getResources().getDrawable(R.drawable.ic_wide_border_selected));
                        break;
                    case ScoringAlgorithms.INPUT_b:
                        mIcon1b.setBackground(getResources().getDrawable(R.drawable.ic_wide_border_selected));
                        break;
                    case ScoringAlgorithms.INPUT_c:
                        mIcon1c.setBackground(getResources().getDrawable(R.drawable.ic_wide_border_selected));
                        break;
                    case ScoringAlgorithms.INPUT_d:
                        mIcon1d.setBackground(getResources().getDrawable(R.drawable.ic_wide_border_selected));
                        break;

                }
            }
            if (mRaw.getSleep2()!= 0) {
                mQuestion_B_index = mRaw.getSleep2();
                switch (mQuestion_B_index) {
                    case ScoringAlgorithms.INPUT_a:
                        mIcon2a.setBackground(getResources().getDrawable(R.drawable.ic_wide_border_selected));
                        break;
                    case ScoringAlgorithms.INPUT_b:
                        mIcon2b.setBackground(getResources().getDrawable(R.drawable.ic_wide_border_selected));
                        break;
                    case ScoringAlgorithms.INPUT_c:
                        mIcon2c.setBackground(getResources().getDrawable(R.drawable.ic_wide_border_selected));
                        break;
                    case ScoringAlgorithms.INPUT_d:
                        mIcon2d.setBackground(getResources().getDrawable(R.drawable.ic_wide_border_selected));
                        break;
                }
            }
        }
    }

    /**
     * exits out of the question fragment if all questions have been answered.
     */
    private void exitOnLastScore(){
        if (mScoringLab.getScore(mScoreDate).isAllScored() && Score.isToday(mScoreDate)){
            Log.d(TAG, "all questions answered. popping out");

            mExitCountDownTimer = new CountDownTimer(DailyPagerFragment.EXIT_TIMER_MILLISECONDS, DailyPagerFragment.EXIT_TIMER_MILLISECONDS) {

                @Override
                public void onTick(long millisUntilFinished) {
                }

                @Override
                public void onFinish() {
                    getActivity().getSupportFragmentManager().popBackStackImmediate();
                }
            }.start();
        }
    }

    /**
     * cancel timer if the fragment is exited
     */
    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "stopped");
        if (mExitCountDownTimer != null) {
            mExitCountDownTimer.cancel();
            Log.d(TAG, "cancelling exit timer");
        }
    }

    /**
     * ideally the icon being selected woudnt need to be cleared, if its id was passed in here              // refactor potential
     */
    private void clearSelectedA() {
        mIcon1a.setBackground(getResources().getDrawable(R.drawable.ic_wide_border));
        mIcon1b.setBackground(getResources().getDrawable(R.drawable.ic_wide_border));
        mIcon1c.setBackground(getResources().getDrawable(R.drawable.ic_wide_border));
        mIcon1d.setBackground(getResources().getDrawable(R.drawable.ic_wide_border));
    }

    private void clearSelectedB() {
        mIcon2a.setBackground(getResources().getDrawable(R.drawable.ic_wide_border));
        mIcon2b.setBackground(getResources().getDrawable(R.drawable.ic_wide_border));
        mIcon2c.setBackground(getResources().getDrawable(R.drawable.ic_wide_border));
        mIcon2d.setBackground(getResources().getDrawable(R.drawable.ic_wide_border));
    }

};
