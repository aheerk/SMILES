package macewan_dust.smiles;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;
import java.util.UUID;

public class InputEatingFragment extends Fragment {

    private static final String TAG = "InputEatingFragment";
    private static final int NO_SELECTION = 100000;

    ImageView mWebLink1;
    WebLab mWebLab;

    TextView mIcon1a;
    TextView mIcon1b;
    TextView mIcon1c;

    TextView mIcon2a;
    TextView mIcon2b;
    TextView mIcon2c;

    TextView mIcon3a;
    TextView mIcon3b;
    TextView mIcon3c;

    CheckBox mSodiumCheck;
    CheckBox mSugarCheck;
    CheckBox mFatCheck;
    CheckBox mWaterCheck;

    Button mButton;

    int mQuestion_A_index;
    int mQuestion_B_index;
    int mQuestion_C_index;

    int mVegAmount;
    int mProAmount;
    int mGraAmount;

    TextView mResults;
    TextView mFeedback;

    ScrollView mScrollView;

    Score mScore;
    CountDownTimer mExitCountDownTimer;
    Date mScoreDate;
    ScoringLab mScoringLab;
    Raw mRaw;

    /**
     * new instance constructor
     *
     * @return InputEatingFragment
     */
    public static InputEatingFragment newInstance() {
        return new InputEatingFragment();
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

        View v = inflater.inflate(R.layout.fragment_daily_eating_questions, container, false);

        mWebLink1 = v.findViewById(R.id.icon_eating_weblink_1);
        mWebLink1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity activity = (MainActivity) getActivity();
                activity.openLink(mWebLab.getOneLink(15).getUri());
            }
        });

        mButton = v.findViewById(R.id.score_button);

        mScrollView = v.findViewById(R.id.eating_scroll_view);

        mIcon1a = v.findViewById(R.id.icon_1a);
        mIcon1b = v.findViewById(R.id.icon_1b);
        mIcon1c = v.findViewById(R.id.icon_1c);

        mIcon2a = v.findViewById(R.id.icon_2a);
        mIcon2b = v.findViewById(R.id.icon_2b);
        mIcon2c = v.findViewById(R.id.icon_2c);

        mIcon3a = v.findViewById(R.id.icon_3a);
        mIcon3b = v.findViewById(R.id.icon_3b);
        mIcon3c = v.findViewById(R.id.icon_3c);

        mSodiumCheck = v.findViewById(R.id.eating_checkBox_1);
        mSugarCheck = v.findViewById(R.id.eating_checkBox_2);
        mFatCheck = v.findViewById(R.id.eating_checkBox_3);
        mWaterCheck = v.findViewById(R.id.eating_checkBox_4);

        mQuestion_A_index = NO_SELECTION;
        mQuestion_B_index = NO_SELECTION;
        mQuestion_C_index = NO_SELECTION;

        mVegAmount = 0;
        mProAmount = 0;;
        mGraAmount = 0;

        mFeedback = v.findViewById(R.id.text_eating_feedback);


        mIcon1a.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearSelectedA();
                mQuestion_A_index = ScoringAlgorithms.INPUT_a;
                mIcon1a.setBackground(getResources().getDrawable(R.drawable.ic_wide_border_selected));
                mVegAmount = 0;
            }
        });

        mIcon1b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearSelectedA();
                mQuestion_A_index = ScoringAlgorithms.INPUT_b;
                mIcon1b.setBackground(getResources().getDrawable(R.drawable.ic_wide_border_selected));
                mVegAmount = 50;
            }
        });

        mIcon1c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearSelectedA();
                mQuestion_A_index = ScoringAlgorithms.INPUT_c;
                mIcon1c.setBackground(getResources().getDrawable(R.drawable.ic_wide_border_selected));
                mVegAmount = 51;
            }
        });

        mIcon2a.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearSelectedB();
                mQuestion_B_index = ScoringAlgorithms.INPUT_a;
                mIcon2a.setBackground(getResources().getDrawable(R.drawable.ic_wide_border_selected));
                mGraAmount = 0;
            }
        });

        mIcon2b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearSelectedB();
                mQuestion_B_index = ScoringAlgorithms.INPUT_b;
                mIcon2b.setBackground(getResources().getDrawable(R.drawable.ic_wide_border_selected));
                mGraAmount = 25;

            }
        });

        mIcon2c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearSelectedB();
                mQuestion_B_index = ScoringAlgorithms.INPUT_c;
                mIcon2c.setBackground(getResources().getDrawable(R.drawable.ic_wide_border_selected));
                mGraAmount = 26;

            }
        });

        mIcon3a.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearSelectedC();
                mQuestion_C_index = ScoringAlgorithms.INPUT_a;
                mIcon3a.setBackground(getResources().getDrawable(R.drawable.ic_wide_border_selected));
                mProAmount = 0;
                mScrollView.smoothScrollTo(0, mScrollView.getMaxScrollAmount());

            }
        });

        mIcon3b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearSelectedC();
                mQuestion_C_index = ScoringAlgorithms.INPUT_b;
                mIcon3b.setBackground(getResources().getDrawable(R.drawable.ic_wide_border_selected));
                mProAmount = 25;
                mScrollView.smoothScrollTo(0, mScrollView.getMaxScrollAmount());

            }
        });

        mIcon3c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearSelectedC();
                mQuestion_C_index = ScoringAlgorithms.INPUT_c;
                mIcon3c.setBackground(getResources().getDrawable(R.drawable.ic_wide_border_selected));
                mProAmount = 26;
                mScrollView.smoothScrollTo(0, mScrollView.getMaxScrollAmount());

            }
        });

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mQuestion_A_index == NO_SELECTION ||
                    mQuestion_B_index == NO_SELECTION ||
                    mQuestion_C_index == NO_SELECTION)
                {
                    mResults.setText(getString(R.string.feedback_unselected));

                } else if (mProAmount + mGraAmount + mVegAmount > 100) {
                    mResults.setText(getString(R.string.feedback_over_100_percent));
                }
                else {

                    int score = ScoringAlgorithms.scoreEating(mQuestion_A_index,
                            mQuestion_B_index, mQuestion_C_index,
                            mSodiumCheck.isChecked(), mSugarCheck.isChecked(),
                            mFatCheck.isChecked(), mWaterCheck.isChecked());

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
                            Toast t1 = new Toast(getContext());
                            scoreStringID = R.string.score_error;
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
                        mScore.setEatingScore(score);
                        // save score to database
                        mScoringLab.updateScore(mScore);
                        //
                    } else {
                        Log.d(TAG, "score NOT found by date: " + Score.timelessDate(mScoreDate));

                        mScore = new Score(mScoreDate);
                        mScore.setEatingScore(score);
                        mScoringLab.addScore(mScore);
                    }

                    // update
                    if (mScoringLab.isRaw(mScoreDate)){
                        mRaw = mScoringLab.getRaw(mScoreDate);
                        mRaw.setEating(mQuestion_A_index,
                                mQuestion_B_index, mQuestion_C_index,
                                mSodiumCheck.isChecked(), mSugarCheck.isChecked(),
                                mFatCheck.isChecked(), mWaterCheck.isChecked());
                        mScoringLab.updateRaw(mRaw);

                    }else { // add
                        mRaw = new Raw(mScoreDate);
                        mRaw.setEating(mQuestion_A_index,
                                mQuestion_B_index, mQuestion_C_index,
                                mSodiumCheck.isChecked(), mSugarCheck.isChecked(),
                                mFatCheck.isChecked(), mWaterCheck.isChecked());
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
                mScrollView.smoothScrollTo(0, mScrollView.getMaxScrollAmount());

            }
        });

        mResults = v.findViewById(R.id.text_score); // note this object is invisible
        mResults.setVisibility(View.VISIBLE);

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
            clearSelectedC();
            if (mRaw.getEating1() != 0) {
                mQuestion_A_index = mRaw.getEating1();
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
                }
            }
            if (mRaw.getEating2() != 0) {
                mQuestion_B_index = mRaw.getEating2();
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
                }
            }
            if (mRaw.getEating3() != 0) {
                mQuestion_C_index = mRaw.getEating3();
                switch (mQuestion_C_index) {
                    case ScoringAlgorithms.INPUT_a:
                        mIcon3a.setBackground(getResources().getDrawable(R.drawable.ic_wide_border_selected));
                        break;
                    case ScoringAlgorithms.INPUT_b:
                        mIcon3b.setBackground(getResources().getDrawable(R.drawable.ic_wide_border_selected));
                        break;
                    case ScoringAlgorithms.INPUT_c:
                        mIcon3c.setBackground(getResources().getDrawable(R.drawable.ic_wide_border_selected));
                        break;
                }
            }

            mSodiumCheck.setChecked(mRaw.isEating4());
            mSugarCheck.setChecked(mRaw.isEating5());
            mFatCheck.setChecked(mRaw.isEating6());
            mWaterCheck.setChecked(mRaw.isEating7());
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
    }

    public void clearSelectedB() {
        mIcon2a.setBackground(getResources().getDrawable(R.drawable.ic_wide_border));
        mIcon2b.setBackground(getResources().getDrawable(R.drawable.ic_wide_border));
        mIcon2c.setBackground(getResources().getDrawable(R.drawable.ic_wide_border));
    }

    public void clearSelectedC() {
        mIcon3a.setBackground(getResources().getDrawable(R.drawable.ic_wide_border));
        mIcon3b.setBackground(getResources().getDrawable(R.drawable.ic_wide_border));
        mIcon3c.setBackground(getResources().getDrawable(R.drawable.ic_wide_border));
    }

};
