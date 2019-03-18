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
import java.util.UUID;

public class InputImaginationFragment extends Fragment {

    private static final String TAG = "InputImagFragment";
    private static final int NO_SELECTION = 100000;

    ImageView mIcon1_a;
    ImageView mIcon1_b;
    ImageView mIcon1_c;

    ImageView mIcon2_a;
    ImageView mIcon2_b;
    ImageView mIcon2_c;

    ImageView mIcon3_a;
    ImageView mIcon3_b;
    ImageView mIcon3_c;

    Button mButton;
 //   ImageView mIconFeedback;
    TextView mResults;

    int mQuestion_A_index;
    int mQuestion_B_index;
    int mQuestion_C_index;

    Score mScore;
    CountDownTimer mExitCountDownTimer;

    Date mScoreDate;


    /**
     * new instance constructor
     * @return InputSleepFragment
     */
    public static InputImaginationFragment newInstance() {
        return new InputImaginationFragment();
    }

    /**
     * Android will call this function when a view is created. If the view is not destroyed, this
     * remains in effect (not that rotating destroys a view.
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
     * @param inflater - inflates the view (brings it into memory)
     * @param container - parent view
     * @param savedInstanceState - holds data
     * @return view
     */
    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_daily_imagination_questions, container, false);

   //     getActivity().setTitle(R.string.title_quest_imagination);


        mButton = v.findViewById(R.id.score_button);

        mIcon1_a = v.findViewById(R.id.icon_1a);
        mIcon1_b = v.findViewById(R.id.icon_1b);
        mIcon1_c = v.findViewById(R.id.icon_1c);

        mIcon2_a = v.findViewById(R.id.icon_2a);
        mIcon2_b = v.findViewById(R.id.icon_2b);
        mIcon2_c = v.findViewById(R.id.icon_2c);

        mIcon3_a = v.findViewById(R.id.icon_3a);
        mIcon3_b = v.findViewById(R.id.icon_3b);
        mIcon3_c = v.findViewById(R.id.icon_3c);

        mQuestion_A_index = NO_SELECTION;
        mQuestion_B_index = NO_SELECTION;
        mQuestion_C_index = NO_SELECTION;

   //     mIconFeedback = v.findViewById(R.id.icon_feedback);

        // QUESTION 1 Listeners
        mIcon1_a.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearQ1Selected();
                mQuestion_A_index = ScoringAlgorithms.INPUT_a;
                mIcon1_a.setBackground(getResources().getDrawable(R.drawable.border_image_selected));
            }
        });

        mIcon1_b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearQ1Selected();
                mQuestion_A_index = ScoringAlgorithms.INPUT_b;
                mIcon1_b.setBackground(getResources().getDrawable(R.drawable.border_image_selected));
            }
        });

        mIcon1_c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearQ1Selected();
                mQuestion_A_index = ScoringAlgorithms.INPUT_c;
                mIcon1_c.setBackground(getResources().getDrawable(R.drawable.border_image_selected));
            }
        });

        // QUESTION 2 Listeners
        mIcon2_a.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearQ2Selected();
                mQuestion_B_index = ScoringAlgorithms.INPUT_a;
                mIcon2_a.setBackground(getResources().getDrawable(R.drawable.border_image_selected));
            }
        });
        mIcon2_b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearQ2Selected();
                mQuestion_B_index = ScoringAlgorithms.INPUT_b;
                mIcon2_b.setBackground(getResources().getDrawable(R.drawable.border_image_selected));
            }
        });
        mIcon2_c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearQ2Selected();
                mQuestion_B_index = ScoringAlgorithms.INPUT_c;
                mIcon2_c.setBackground(getResources().getDrawable(R.drawable.border_image_selected));
            }
        });

        // QUESTION 3 Listeners
        mIcon3_a.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearQ3Selected();
                mQuestion_C_index = ScoringAlgorithms.INPUT_a;
                mIcon3_a.setBackground(getResources().getDrawable(R.drawable.border_image_selected));
            }
        });

        mIcon3_b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearQ3Selected();
                mQuestion_C_index = ScoringAlgorithms.INPUT_b;
                mIcon3_b.setBackground(getResources().getDrawable(R.drawable.border_image_selected));
            }
        });

        mIcon3_c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearQ3Selected();
                mQuestion_C_index = ScoringAlgorithms.INPUT_c;
                mIcon3_c.setBackground(getResources().getDrawable(R.drawable.border_image_selected));
            }
        });

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mQuestion_A_index == NO_SELECTION || mQuestion_B_index == NO_SELECTION
                        || mQuestion_C_index == NO_SELECTION) {

                    mResults.setText(getString(R.string.feedback_unselected));

                } else {

                    int score = ScoringAlgorithms.scoreImagination(mQuestion_A_index,
                            mQuestion_B_index, mQuestion_C_index);

                    int scoreStringID = 1000; // just here to initialize
                    Log.d(TAG, "Score: " + score);
                    switch (score) {

                        case ScoringAlgorithms.SCORE_OVER:
                            scoreStringID = R.string.score_high;
        //                    mIconFeedback.setBackground(getResources().getDrawable(R.drawable.border_image_high));
                            break;
                        case ScoringAlgorithms.SCORE_UNDER:
                            scoreStringID = R.string.score_low;
        //                    mIconFeedback.setBackground(getResources().getDrawable(R.drawable.border_image_low));
                            break;
                        case ScoringAlgorithms.SCORE_BALANCED:
                            scoreStringID = R.string.score_balanced;
         //                   mIconFeedback.setBackground(getResources().getDrawable(R.drawable.border_image_balanced));
                            break;
                        case ScoringAlgorithms.SCORE_UNBALANCED:
                            scoreStringID = R.string.score_unbalanced;
          //                  mIconFeedback.setBackground(getResources().getDrawable(R.drawable.border_image_unbalanced));
                            break;
                        case ScoringAlgorithms.SCORE_ERROR:
                            scoreStringID = R.string.score_error;

                            break;
                        default:
                            Toast t2 = new Toast(getContext());
                            t2.setText("Error in scoring");
                            t2.show();
                    }

                    // if score exists, update it, else make a new one and save it
                    if (ScoringLab.get(getActivity()).isScore(mScoreDate)){
                        // get score object to use its data
                        mScore = ScoringLab.get(getActivity()).getScore(mScoreDate);
                        // set new score for this category
                        mScore.setImaginationScore(score);
                        // save score to database
                        ScoringLab.get(getActivity()).updateScore(mScore);
                        //
                    } else {
                        mScore = new Score(mScoreDate);
                        mScore.setImaginationScore(score);
                        ScoringLab.get(getActivity()).addScore(mScore);
                    }

                    mResults.setText(getString(R.string.quest_results, getString(scoreStringID)));
                    exitOnLastScore();

                }

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
     * ideally the icon being selected wouldn't need to be cleared, if its id was passed in here              // refactor potential
     */
    private void clearQ1Selected(){
        mIcon1_a.setBackground(getResources().getDrawable(R.drawable.border_image));
        mIcon1_b.setBackground(getResources().getDrawable(R.drawable.border_image));
        mIcon1_c.setBackground(getResources().getDrawable(R.drawable.border_image));
    }

    private void clearQ2Selected(){
        mIcon2_a.setBackground(getResources().getDrawable(R.drawable.border_image));
        mIcon2_b.setBackground(getResources().getDrawable(R.drawable.border_image));
        mIcon2_c.setBackground(getResources().getDrawable(R.drawable.border_image));
    }

    private void clearQ3Selected(){
        mIcon3_a.setBackground(getResources().getDrawable(R.drawable.border_image));
        mIcon3_b.setBackground(getResources().getDrawable(R.drawable.border_image));
        mIcon3_c.setBackground(getResources().getDrawable(R.drawable.border_image));
    }

}
