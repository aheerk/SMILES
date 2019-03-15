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

public class InputSpeakingFragment extends Fragment {

    private static final String TAG = "InputSpeakingFragment";
    private static final int NO_SELECTION = 100000;

    // 5 Questions total

    ImageView mIcon1a;
    ImageView mIcon1b;
    ImageView mIcon1c;
    ImageView mIcon1d;
    ImageView mIcon1e;

    CheckBox mDebriefCheck;
    CheckBox mShareCheck;
    CheckBox mSocialMediaCheck;
    CheckBox mSocialBalanceCheck;

    // Score Button
    Button mButton;

   // ImageView mIconFeedback;
    TextView mResults;

    int mQuestion_A_index;

    ScrollView mScrollView;

    Score mScore;
    CountDownTimer mExitCountDownTimer;
    Date mScoreDate;

    /**
     * new instance constructor
     *
     * @return InputSpeakingFragment
     */
    public static InputSpeakingFragment newInstance() {
        return new InputSpeakingFragment();
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
     * @param inflater           - inflates the view (brings it into memory)
     * @param container          - parent view
     * @param savedInstanceState - holds data
     * @return view
     */
    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_daily_speaking_questions, container, false);

        mScrollView = v.findViewById(R.id.speaking_scroll_view);

        mButton = v.findViewById(R.id.score_button);

        mIcon1a = v.findViewById(R.id.icon_1a);
        mIcon1b = v.findViewById(R.id.icon_1b);
        mIcon1c = v.findViewById(R.id.icon_1c);
        mIcon1d = v.findViewById(R.id.icon_1d);
        mIcon1e = v.findViewById(R.id.icon_1e);

        mDebriefCheck = v.findViewById(R.id.assistance_checkBox);
        mShareCheck = v.findViewById(R.id.share_checkbox);
        mSocialMediaCheck = v.findViewById(R.id.social_media_checkbox);
        mSocialBalanceCheck = v.findViewById(R.id.social_balance_checkbox);

        mQuestion_A_index = NO_SELECTION;

        mIcon1a.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearSelected();
                mQuestion_A_index = 1;
                mIcon1a.setBackground(getResources().getDrawable(R.drawable.border_image_selected));
            }
        });

        mIcon1b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearSelected();
                mQuestion_A_index = 2;
                mIcon1b.setBackground(getResources().getDrawable(R.drawable.border_image_selected));
            }
        });

        mIcon1c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearSelected();
                mQuestion_A_index = 3;
                mIcon1c.setBackground(getResources().getDrawable(R.drawable.border_image_selected));
            }
        });

        mIcon1d.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearSelected();
                mQuestion_A_index = 4;
                mIcon1d.setBackground(getResources().getDrawable(R.drawable.border_image_selected));
            }
        });

        mIcon1e.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearSelected();
                mQuestion_A_index = 5;
                mIcon1e.setBackground(getResources().getDrawable(R.drawable.border_image_selected));
            }
        });


        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mQuestion_A_index == NO_SELECTION) {
                    mResults.setText(getString(R.string.feedback_unselected));

                } else {

                    int score = ScoringAlgorithms.scoreSpeaking(mQuestion_A_index,
                            mDebriefCheck.isChecked(),
                            mShareCheck.isChecked(),
                            mSocialMediaCheck.isChecked(),
                            mSocialBalanceCheck.isChecked());

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
                    if (ScoringLab.get(getActivity()).isScore(mScoreDate)){
                        // get score object to use its data
                        mScore = ScoringLab.get(getActivity()).getScore(mScoreDate);
                        // set new score for this category
                        mScore.setSpeakingScore(score);
                        // save score to database
                        ScoringLab.get(getActivity()).updateScore(mScore);
                        //
                    } else {
                        mScore = new Score(mScoreDate);
                        mScore.setSpeakingScore(score);
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
     *
     * Timer Ref: https://developer.android.com/reference/android/os/CountDownTimer
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
    private void clearSelected() {
        mIcon1a.setBackground(getResources().getDrawable(R.drawable.border_image));
        mIcon1b.setBackground(getResources().getDrawable(R.drawable.border_image));
        mIcon1c.setBackground(getResources().getDrawable(R.drawable.border_image));
        mIcon1d.setBackground(getResources().getDrawable(R.drawable.border_image));
        mIcon1e.setBackground(getResources().getDrawable(R.drawable.border_image));
    }

}
