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
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;

public class InputSpeakingFragment extends Fragment {

    private static final String TAG = "InputSpeakingFragment";
    private static final int NO_SELECTION = 100000;

    ImageView mWebLink1;
    WebLab mWebLab;

    // 5 Questions total
    TextView mIcon1a;
    TextView mIcon1b;
    TextView mIcon1c;
    TextView mIcon1d;
    TextView mIcon1e;

    CheckBox mDebriefCheck;
    CheckBox mShareCheck;
    CheckBox mSocialMediaCheck;
    CheckBox mSocialBalanceCheck;

    // Score Button
    Button mButton;

    TextView mResults;
    TextView mFeedback;

    int mQuestion_A_index;

    ScrollView mScrollView;

    Score mScore;
    CountDownTimer mExitCountDownTimer;
    Date mScoreDate;
    ScoringLab mScoringLab;
    Raw mRaw;

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
        mScoringLab = ScoringLab.get(getContext());
        mWebLab = WebLab.getWebLab(getContext());
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

        mWebLink1 = v.findViewById(R.id.icon_speaking_weblink_1);
        mWebLink1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity activity = (MainActivity) getActivity();
                activity.openLink(mWebLab.getOneLink(16).getUri());
            }
        });

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

        mFeedback = v.findViewById(R.id.text_speaking_feedback);


        mShareCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                                   @Override
                                                   public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                                       mScrollView.smoothScrollTo(0, mScrollView.getMaxScrollAmount());
//                                                       if (mShareCheck.isChecked()){
//                                                           mShareCheck.setHighlightColor(getResources().getColor(R.color.colorBalanced));
//                                                       } else {
//                                                           mShareCheck.setBackgroundColor(getResources().getColor(R.color.colorUnder));
//                                                       }
                                                   }
                                               }
        );

        mSocialMediaCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                                   @Override
                                                   public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                                       mScrollView.smoothScrollTo(0, mScrollView.getMaxScrollAmount());
//                                                       if (mSocialMediaCheck.isChecked()){
//                                                           mSocialMediaCheck.setBackground(getResources().getDrawable(R.drawable.ic_box_over));
//                                                       } else {
//                                                           //mSocialMediaCheck.setBackgroundColor(getResources().getColor(R.color.colorBalanced));
//                                                       }
                                                   }
                                               }
        );

        mSocialBalanceCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                                         @Override
                                                         public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                                             mScrollView.smoothScrollTo(0, mScrollView.getMaxScrollAmount());
                                                         }
                                                     }
        );


        mIcon1a.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearSelected();
                mQuestion_A_index = 1;
                mIcon1a.setBackground(getResources().getDrawable(R.drawable.ic_single_border_selected));
            }
        });

        mIcon1b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearSelected();
                mQuestion_A_index = 2;
                mIcon1b.setBackground(getResources().getDrawable(R.drawable.ic_single_border_selected));
            }
        });

        mIcon1c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearSelected();
                mQuestion_A_index = 3;
                mIcon1c.setBackground(getResources().getDrawable(R.drawable.ic_single_border_selected));
            }
        });

        mIcon1d.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearSelected();
                mQuestion_A_index = 4;
                mIcon1d.setBackground(getResources().getDrawable(R.drawable.ic_single_border_selected));
            }
        });

        mIcon1e.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearSelected();
                mQuestion_A_index = 5;
                mIcon1e.setBackground(getResources().getDrawable(R.drawable.ic_single_border_selected));
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
                    if (mScoringLab.isScore(mScoreDate)) {
                        // get score object to use its data
                        mScore = mScoringLab.getScore(mScoreDate);
                        // set new score for this category
                        mScore.setSpeakingScore(score);
                        // save score to database
                        mScoringLab.updateScore(mScore);
                        //
                    } else {
                        mScore = new Score(mScoreDate);
                        mScore.setSpeakingScore(score);
                        mScoringLab.addScore(mScore);
                    }

                    // update
                    if (mScoringLab.isRaw(mScoreDate)) {
                        mRaw = mScoringLab.getRaw(mScoreDate);
                        mRaw.setSpeaking(mQuestion_A_index,
                                mDebriefCheck.isChecked(),
                                mShareCheck.isChecked(),
                                mSocialMediaCheck.isChecked(),
                                mSocialBalanceCheck.isChecked());

                    } else { // add
                        mRaw = new Raw(mScoreDate);
                        mRaw.setSpeaking(mQuestion_A_index,
                                mDebriefCheck.isChecked(),
                                mShareCheck.isChecked(),
                                mSocialMediaCheck.isChecked(),
                                mSocialBalanceCheck.isChecked());
                    }
                    Log.d(TAG, "raw object: " + mRaw);

                    mResults.setText(getString(R.string.quest_results, getString(scoreStringID)));
                    exitOnLastScore();

                    if (score != ScoringAlgorithms.SCORE_BALANCED){
                        mFeedback.setVisibility(View.VISIBLE);
                    } else {
                        mFeedback.setVisibility(View.INVISIBLE);

                    }
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
    private void setButtonsFromDatabase() {
        if (mScoringLab.isRaw(mScoreDate)) {
            mRaw = mScoringLab.getRaw(mScoreDate);

            clearSelected();

            if (mRaw.getSpeaking1() != 0) {
                mQuestion_A_index = mRaw.getSpeaking1();
                switch (mQuestion_A_index) {
                    case ScoringAlgorithms.INPUT_a:
                        mIcon1a.setBackground(getResources().getDrawable(R.drawable.ic_single_border_selected));
                        break;
                    case ScoringAlgorithms.INPUT_b:
                        mIcon1b.setBackground(getResources().getDrawable(R.drawable.ic_single_border_selected));
                        break;
                    case ScoringAlgorithms.INPUT_c:
                        mIcon1c.setBackground(getResources().getDrawable(R.drawable.ic_single_border_selected));
                        break;
                    case ScoringAlgorithms.INPUT_d:
                        mIcon1d.setBackground(getResources().getDrawable(R.drawable.ic_single_border_selected));
                        break;
                    case ScoringAlgorithms.INPUT_e:
                        mIcon1e.setBackground(getResources().getDrawable(R.drawable.ic_single_border_selected));
                        break;
                }
            }

            mDebriefCheck.setChecked(mRaw.isSpeaking2());
            mShareCheck.setChecked(mRaw.isSpeaking3());
            mSocialMediaCheck.setChecked(mRaw.isSpeaking4());
            mSocialBalanceCheck.setChecked(mRaw.isSpeaking5());
        }
    }

    /**
     * exits out of the question fragment if all questions have been answered.
     * <p>
     * Timer Ref: https://developer.android.com/reference/android/os/CountDownTimer
     */
    private void exitOnLastScore() {
        if (mScoringLab.getScore(mScoreDate).isAllScored() && Score.isToday(mScoreDate)) {
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
        mIcon1a.setBackground(getResources().getDrawable(R.drawable.ic_single_border));
        mIcon1b.setBackground(getResources().getDrawable(R.drawable.ic_single_border));
        mIcon1c.setBackground(getResources().getDrawable(R.drawable.ic_single_border));
        mIcon1d.setBackground(getResources().getDrawable(R.drawable.ic_single_border));
        mIcon1e.setBackground(getResources().getDrawable(R.drawable.ic_single_border));
    }

}
