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

    ImageView mIcon1a;
    ImageView mIcon1b;
    ImageView mIcon1c;

    ImageView mIcon2a;
    ImageView mIcon2b;
    ImageView mIcon2c;

    ImageView mIcon3a;
    ImageView mIcon3b;
    ImageView mIcon3c;

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

    ScrollView mScrollView;

    Score mScore;
    CountDownTimer mExitCountDownTimer;
    Date mScoreDate;

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

        mIcon1a.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearSelectedA();
                mQuestion_A_index = ScoringAlgorithms.INPUT_a;
                mIcon1a.setBackground(getResources().getDrawable(R.drawable.border_image_selected));
                mVegAmount = 0;
            }
        });

        mIcon1b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearSelectedA();
                mQuestion_A_index = ScoringAlgorithms.INPUT_b;
                mIcon1b.setBackground(getResources().getDrawable(R.drawable.border_image_selected));
                mVegAmount = 50;
            }
        });

        mIcon1c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearSelectedA();
                mQuestion_A_index = ScoringAlgorithms.INPUT_c;
                mIcon1c.setBackground(getResources().getDrawable(R.drawable.border_image_selected));
                mVegAmount = 51;
            }
        });

        mIcon2a.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearSelectedB();
                mQuestion_B_index = ScoringAlgorithms.INPUT_a;
                mIcon2a.setBackground(getResources().getDrawable(R.drawable.border_image_selected));
                mGraAmount = 0;
            }
        });

        mIcon2b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearSelectedB();
                mQuestion_B_index = ScoringAlgorithms.INPUT_b;
                mIcon2b.setBackground(getResources().getDrawable(R.drawable.border_image_selected));
                mGraAmount = 25;

            }
        });

        mIcon2c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearSelectedB();
                mQuestion_B_index = ScoringAlgorithms.INPUT_c;
                mIcon2c.setBackground(getResources().getDrawable(R.drawable.border_image_selected));
                mGraAmount = 26;

            }
        });

        mIcon3a.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearSelectedC();
                mQuestion_C_index = ScoringAlgorithms.INPUT_a;
                mIcon3a.setBackground(getResources().getDrawable(R.drawable.border_image_selected));
                mProAmount = 0;
            }
        });

        mIcon3b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearSelectedC();
                mQuestion_C_index = ScoringAlgorithms.INPUT_b;
                mIcon3b.setBackground(getResources().getDrawable(R.drawable.border_image_selected));
                mProAmount = 25;
            }
        });

        mIcon3c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearSelectedC();
                mQuestion_C_index = ScoringAlgorithms.INPUT_c;
                mIcon3c.setBackground(getResources().getDrawable(R.drawable.border_image_selected));
                mProAmount = 26;
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
                    if (ScoringLab.get(getActivity()).isScore(mScoreDate)){
                        // get score object to use its data
                        mScore = ScoringLab.get(getActivity()).getScore(mScoreDate);
                        // set new score for this category
                        mScore.setEatingScore(score);
                        // save score to database
                        ScoringLab.get(getActivity()).updateScore(mScore);
                        //
                    } else {
                        Log.d(TAG, "score NOT found by date: " + Score.timelessDate(mScoreDate));

                        mScore = new Score(mScoreDate);
                        mScore.setEatingScore(score);
                        ScoringLab.get(getActivity()).addScore(mScore);
                    }
                    mResults.setText(getString(R.string.quest_results, getString(scoreStringID)));
                    exitOnLastScore();
                }
                mScrollView.smoothScrollTo(0, mScrollView.getMaxScrollAmount());

            }
        });

        mResults = v.findViewById(R.id.text_score); // note this object is invisible
        mResults.setVisibility(View.VISIBLE);

        return v;
    }

    /**
     * exits out of the question fragment if all questions have been answered.
     */
    private void exitOnLastScore(){
        if (ScoringLab.get(getActivity()).getScore(mScoreDate).isAllScored() && Score.isToday(mScoreDate)){
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
        mIcon1a.setBackground(getResources().getDrawable(R.drawable.border_image));
        mIcon1b.setBackground(getResources().getDrawable(R.drawable.border_image));
        mIcon1c.setBackground(getResources().getDrawable(R.drawable.border_image));
    }

    public void clearSelectedB() {
        mIcon2a.setBackground(getResources().getDrawable(R.drawable.border_image));
        mIcon2b.setBackground(getResources().getDrawable(R.drawable.border_image));
        mIcon2c.setBackground(getResources().getDrawable(R.drawable.border_image));
    }

    public void clearSelectedC() {
        mIcon3a.setBackground(getResources().getDrawable(R.drawable.border_image));
        mIcon3b.setBackground(getResources().getDrawable(R.drawable.border_image));
        mIcon3c.setBackground(getResources().getDrawable(R.drawable.border_image));
    }

};
