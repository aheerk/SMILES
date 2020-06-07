package macewan_dust.smiles;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;

public class InputLifeSatisfactionFragment extends Fragment {

    private static final String TAG = "InputLifeFragment";
    private static final int NO_SELECTION = 104000;

    TextView mIcon1a;
    TextView mIcon1b;
    TextView mIcon1c;

    TextView mIcon2a;
    TextView mIcon2b;
    TextView mIcon2c;

    TextView mIcon3a;
    TextView mIcon3b;
    TextView mIcon3c;

    TextView mIcon4a;
    TextView mIcon4b;
    TextView mIcon4c;

    TextView mIcon5a;
    TextView mIcon5b;
    TextView mIcon5c;

    TextView mIcon6a;
    TextView mIcon6b;
    TextView mIcon6c;

    TextView mIcon7a;
    TextView mIcon7b;
    TextView mIcon7c;

    TextView mIcon8a;
    TextView mIcon8b;
    TextView mIcon8c;

    TextView mIcon9a;
    TextView mIcon9b;
    TextView mIcon9c;

    TextView mIcon10a;
    TextView mIcon10b;
    TextView mIcon10c;

    TextView mIcon11a;
    TextView mIcon11b;
    TextView mIcon11c;

//    TODO any web links?
//    ImageView mWebLink1;
//    ImageView mWebLink2;
//    ImageView mWebLink3;
//    ImageView mWebLink4;
//    WebLab mWebLab;

    Button mButton;
    TextView mResults;
    TextView mFeedback;

    int mQuestion_1_index;
    int mQuestion_2_index;
    int mQuestion_3_index;
    int mQuestion_4_index;
    int mQuestion_5_index;
    int mQuestion_6_index;
    int mQuestion_7_index;
    int mQuestion_8_index;
    int mQuestion_9_index;
    int mQuestion_10_index;
    int mQuestion_11_index;

    Score mScore;
    CountDownTimer mExitCountDownTimer;

    Date mScoreDate;
    ScoringLab mScoringLab;
    Raw mRaw;
    MainActivity mMainActivity;


    /**
     * new instance constructor
     *
     * @return InputSleepFragment
     */
    public static InputLifeSatisfactionFragment newInstance() {
        return new InputLifeSatisfactionFragment();
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
//        mWebLab = WebLab.getWebLab(getContext());
        mMainActivity = (MainActivity) getActivity();
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

        View v = inflater.inflate(R.layout.fragment_daily_life_satisfaction_questions, container, false);

//        mWebLink1 = v.findViewById(R.id.icon_imagination_weblink_1);
//        mWebLink1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mMainActivity.openLink(mWebLab.getOneLink(6).getUri());
//            }
//        });
//
//        mWebLink2 = v.findViewById(R.id.icon_imagination_weblink_2);
//        mWebLink2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mMainActivity.openLink(mWebLab.getOneLink(7).getUri());
//            }
//        });
//
//        mWebLink3 = v.findViewById(R.id.icon_imagination_weblink_3);
//        mWebLink3.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mMainActivity.openLink(mWebLab.getOneLink(8).getUri());
//            }
//        });
//
//        mWebLink4 = v.findViewById(R.id.icon_imagination_weblink_4);
//        mWebLink4.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mMainActivity.openLink(mWebLab.getOneLink(9).getUri());
//            }
//        });

        mButton = v.findViewById(R.id.score_button);

        mIcon1a = v.findViewById(R.id.icon_1a);
        mIcon1b = v.findViewById(R.id.icon_1b);
        mIcon1c = v.findViewById(R.id.icon_1c);

        mIcon2a = v.findViewById(R.id.icon_2a);
        mIcon2b = v.findViewById(R.id.icon_2b);
        mIcon2c = v.findViewById(R.id.icon_2c);

        mIcon3a = v.findViewById(R.id.icon_3a);
        mIcon3b = v.findViewById(R.id.icon_3b);
        mIcon3c = v.findViewById(R.id.icon_3c);

        mIcon4a = v.findViewById(R.id.icon_4a);
        mIcon4b = v.findViewById(R.id.icon_4b);
        mIcon4c = v.findViewById(R.id.icon_4c);

        mIcon5a = v.findViewById(R.id.icon_5a);
        mIcon5b = v.findViewById(R.id.icon_5b);
        mIcon5c = v.findViewById(R.id.icon_5c);

        mIcon6a = v.findViewById(R.id.icon_6a);
        mIcon6b = v.findViewById(R.id.icon_6b);
        mIcon6c = v.findViewById(R.id.icon_6c);

        mIcon7a = v.findViewById(R.id.icon_7a);
        mIcon7b = v.findViewById(R.id.icon_7b);
        mIcon7c = v.findViewById(R.id.icon_7c);

        mIcon8a = v.findViewById(R.id.icon_8a);
        mIcon8b = v.findViewById(R.id.icon_8b);
        mIcon8c = v.findViewById(R.id.icon_8c);

        mIcon9a = v.findViewById(R.id.icon_9a);
        mIcon9b = v.findViewById(R.id.icon_9b);
        mIcon9c = v.findViewById(R.id.icon_9c);

        mIcon10a = v.findViewById(R.id.icon_10a);
        mIcon10b = v.findViewById(R.id.icon_10b);
        mIcon10c = v.findViewById(R.id.icon_10c);

        mIcon11a = v.findViewById(R.id.icon_11a);
        mIcon11b = v.findViewById(R.id.icon_11b);
        mIcon11c = v.findViewById(R.id.icon_11c);

        mQuestion_1_index = NO_SELECTION;
        mQuestion_2_index = NO_SELECTION;
        mQuestion_3_index = NO_SELECTION;
        mQuestion_4_index = NO_SELECTION;
        mQuestion_5_index = NO_SELECTION;
        mQuestion_6_index = NO_SELECTION;
        mQuestion_7_index = NO_SELECTION;
        mQuestion_8_index = NO_SELECTION;
        mQuestion_9_index = NO_SELECTION;
        mQuestion_10_index = NO_SELECTION;
        mQuestion_11_index = NO_SELECTION;

        mFeedback = v.findViewById(R.id.text_life_satisfaction_feedback);

        //     mIconFeedback = v.findViewById(R.id.icon_feedback);


        // QUESTION 1 Listeners
        mIcon1a.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearSelected(mIcon1a, mIcon1b, mIcon1c);
                mQuestion_1_index = ScoringAlgorithms.INPUT_a;
                mIcon1a.setBackground(getResources().getDrawable(R.drawable.ic_wide_border_selected));
            }
        });

        mIcon1b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearSelected(mIcon1a, mIcon1b, mIcon1c);
                mQuestion_1_index = ScoringAlgorithms.INPUT_b;
                mIcon1b.setBackground(getResources().getDrawable(R.drawable.ic_wide_border_selected));
            }
        });

        mIcon1c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearSelected(mIcon1a, mIcon1b, mIcon1c);
                mQuestion_1_index = ScoringAlgorithms.INPUT_c;
                mIcon1c.setBackground(getResources().getDrawable(R.drawable.ic_wide_border_selected));
            }
        });

        // QUESTION 2 Listeners
        mIcon2a.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearSelected(mIcon2a, mIcon2b, mIcon2c);
                mQuestion_2_index = ScoringAlgorithms.INPUT_a;
                mIcon2a.setBackground(getResources().getDrawable(R.drawable.ic_wide_border_selected));
            }
        });
        mIcon2b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearSelected(mIcon2a, mIcon2b, mIcon2c);
                mQuestion_2_index = ScoringAlgorithms.INPUT_b;
                mIcon2b.setBackground(getResources().getDrawable(R.drawable.ic_wide_border_selected));
            }
        });
        mIcon2c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearSelected(mIcon2a, mIcon2b, mIcon2c);
                mQuestion_2_index = ScoringAlgorithms.INPUT_c;
                mIcon2c.setBackground(getResources().getDrawable(R.drawable.ic_wide_border_selected));
            }
        });

        // QUESTION 3 Listeners
        mIcon3a.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearSelected(mIcon3a, mIcon3b, mIcon3c);
                mQuestion_3_index = ScoringAlgorithms.INPUT_a;
                mIcon3a.setBackground(getResources().getDrawable(R.drawable.ic_wide_border_selected));
            }
        });

        mIcon3b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearSelected(mIcon3a, mIcon3b, mIcon3c);
                mQuestion_3_index = ScoringAlgorithms.INPUT_b;
                mIcon3b.setBackground(getResources().getDrawable(R.drawable.ic_wide_border_selected));
            }
        });

        mIcon3c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearSelected(mIcon3a, mIcon3b, mIcon3c);
                mQuestion_3_index = ScoringAlgorithms.INPUT_c;
                mIcon3c.setBackground(getResources().getDrawable(R.drawable.ic_wide_border_selected));
            }
        });

        // QUESTION 4 Listeners
        mIcon4a.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearSelected(mIcon4a, mIcon4b, mIcon4c);
                mQuestion_4_index = ScoringAlgorithms.INPUT_a;
                mIcon4a.setBackground(getResources().getDrawable(R.drawable.ic_wide_border_selected));
            }
        });

        mIcon4b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearSelected(mIcon4a, mIcon4b, mIcon4c);
                mQuestion_4_index = ScoringAlgorithms.INPUT_b;
                mIcon4b.setBackground(getResources().getDrawable(R.drawable.ic_wide_border_selected));
            }
        });

        mIcon4c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearSelected(mIcon4a, mIcon4b, mIcon4c);
                mQuestion_4_index = ScoringAlgorithms.INPUT_c;
                mIcon4c.setBackground(getResources().getDrawable(R.drawable.ic_wide_border_selected));
            }
        });

        // QUESTION 5 Listeners
        mIcon5a.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearSelected(mIcon5a, mIcon5b, mIcon5c);
                mQuestion_5_index = ScoringAlgorithms.INPUT_a;
                mIcon5a.setBackground(getResources().getDrawable(R.drawable.ic_wide_border_selected));
            }
        });
        mIcon5b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearSelected(mIcon5a, mIcon5b, mIcon5c);
                mQuestion_5_index = ScoringAlgorithms.INPUT_b;
                mIcon5b.setBackground(getResources().getDrawable(R.drawable.ic_wide_border_selected));
            }
        });
        mIcon5c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearSelected(mIcon5a, mIcon5b, mIcon5c);
                mQuestion_5_index = ScoringAlgorithms.INPUT_c;
                mIcon5c.setBackground(getResources().getDrawable(R.drawable.ic_wide_border_selected));
            }
        });

        // QUESTION 6 Listeners
        mIcon6a.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearSelected(mIcon6a, mIcon6b, mIcon6c);
                mQuestion_6_index = ScoringAlgorithms.INPUT_a;
                mIcon6a.setBackground(getResources().getDrawable(R.drawable.ic_wide_border_selected));
            }
        });

        mIcon6b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearSelected(mIcon6a, mIcon6b, mIcon6c);
                mQuestion_6_index = ScoringAlgorithms.INPUT_b;
                mIcon6b.setBackground(getResources().getDrawable(R.drawable.ic_wide_border_selected));
            }
        });

        mIcon6c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearSelected(mIcon6a, mIcon6b, mIcon6c);
                mQuestion_6_index = ScoringAlgorithms.INPUT_c;
                mIcon6c.setBackground(getResources().getDrawable(R.drawable.ic_wide_border_selected));
            }
        });

        // QUESTION 7 Listeners
        mIcon7a.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearSelected(mIcon7a, mIcon7b, mIcon7c);
                mQuestion_7_index = ScoringAlgorithms.INPUT_a;
                mIcon7a.setBackground(getResources().getDrawable(R.drawable.ic_wide_border_selected));
            }
        });

        mIcon7b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearSelected(mIcon7a, mIcon7b, mIcon7c);
                mQuestion_7_index = ScoringAlgorithms.INPUT_b;
                mIcon7b.setBackground(getResources().getDrawable(R.drawable.ic_wide_border_selected));
            }
        });

        mIcon7c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearSelected(mIcon7a, mIcon7b, mIcon7c);
                mQuestion_7_index = ScoringAlgorithms.INPUT_c;
                mIcon7c.setBackground(getResources().getDrawable(R.drawable.ic_wide_border_selected));
            }
        });

        // QUESTION 8 Listeners
        mIcon8a.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearSelected(mIcon8a, mIcon8b, mIcon8c);
                mQuestion_8_index = ScoringAlgorithms.INPUT_a;
                mIcon8a.setBackground(getResources().getDrawable(R.drawable.ic_wide_border_selected));
            }
        });
        mIcon8b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearSelected(mIcon8a, mIcon8b, mIcon8c);
                mQuestion_8_index = ScoringAlgorithms.INPUT_b;
                mIcon8b.setBackground(getResources().getDrawable(R.drawable.ic_wide_border_selected));
            }
        });
        mIcon8c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearSelected(mIcon8a, mIcon8b, mIcon8c);
                mQuestion_8_index = ScoringAlgorithms.INPUT_c;
                mIcon8c.setBackground(getResources().getDrawable(R.drawable.ic_wide_border_selected));
            }
        });

        // QUESTION 9 Listeners
        mIcon9a.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearSelected(mIcon9a, mIcon9b, mIcon9c);
                mQuestion_9_index = ScoringAlgorithms.INPUT_a;
                mIcon9a.setBackground(getResources().getDrawable(R.drawable.ic_wide_border_selected));
            }
        });

        mIcon9b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearSelected(mIcon9a, mIcon9b, mIcon9c);
                mQuestion_9_index = ScoringAlgorithms.INPUT_b;
                mIcon9b.setBackground(getResources().getDrawable(R.drawable.ic_wide_border_selected));
            }
        });

        mIcon9c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearSelected(mIcon9a, mIcon9b, mIcon9c);
                mQuestion_9_index = ScoringAlgorithms.INPUT_c;
                mIcon9c.setBackground(getResources().getDrawable(R.drawable.ic_wide_border_selected));
            }
        });

        // QUESTION 10 Listeners
        mIcon10a.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearSelected(mIcon10a, mIcon10b, mIcon10c);
                mQuestion_10_index = ScoringAlgorithms.INPUT_a;
                mIcon10a.setBackground(getResources().getDrawable(R.drawable.ic_wide_border_selected));
            }
        });

        mIcon10b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearSelected(mIcon10a, mIcon10b, mIcon10c);
                mQuestion_10_index = ScoringAlgorithms.INPUT_b;
                mIcon10b.setBackground(getResources().getDrawable(R.drawable.ic_wide_border_selected));
            }
        });

        mIcon10c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearSelected(mIcon10a, mIcon10b, mIcon10c);
                mQuestion_10_index = ScoringAlgorithms.INPUT_c;
                mIcon10c.setBackground(getResources().getDrawable(R.drawable.ic_wide_border_selected));
            }
        });

        // QUESTION 11 Listeners
        mIcon11a.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearSelected(mIcon11a, mIcon11b, mIcon11c);
                mQuestion_11_index = ScoringAlgorithms.INPUT_a;
                mIcon11a.setBackground(getResources().getDrawable(R.drawable.ic_wide_border_selected));
            }
        });
        mIcon11b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearSelected(mIcon11a, mIcon11b, mIcon11c);
                mQuestion_11_index = ScoringAlgorithms.INPUT_b;
                mIcon11b.setBackground(getResources().getDrawable(R.drawable.ic_wide_border_selected));
            }
        });
        mIcon11c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearSelected(mIcon11a, mIcon11b, mIcon11c);
                mQuestion_11_index = ScoringAlgorithms.INPUT_c;
                mIcon11c.setBackground(getResources().getDrawable(R.drawable.ic_wide_border_selected));
            }
        });

        // Score button
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "mQuestion_11_index is " + mQuestion_11_index);
                if (mQuestion_1_index == NO_SELECTION
                        || mQuestion_2_index == NO_SELECTION
                        || mQuestion_3_index == NO_SELECTION
                        || mQuestion_4_index == NO_SELECTION
                        || mQuestion_5_index == NO_SELECTION
                        || mQuestion_6_index == NO_SELECTION
                        || mQuestion_7_index == NO_SELECTION
                        || mQuestion_8_index == NO_SELECTION
                        || mQuestion_9_index == NO_SELECTION
                        || mQuestion_10_index == NO_SELECTION
                        || mQuestion_11_index == NO_SELECTION
                ) {
                    mResults.setText(getString(R.string.feedback_unselected));
                } else {
                    int[] partA = {mQuestion_1_index, mQuestion_2_index, mQuestion_3_index, mQuestion_4_index, mQuestion_5_index};
                    int[] partB = {mQuestion_6_index};
                    int[] partC = {mQuestion_7_index, mQuestion_8_index, mQuestion_9_index, mQuestion_10_index, mQuestion_11_index};

                    int score = ScoringAlgorithms.scoreLife(partA,
                            partB, partC);

                    int scoreStringID = 1000; // just here to initialize
                    switch (score) {
                        case ScoringAlgorithms.SCORE_UNDER:
                            scoreStringID = R.string.score_low;
                            break;
                        case ScoringAlgorithms.SCORE_BALANCED:
                            scoreStringID = R.string.score_balanced;
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
                    if (mScoringLab.isScore(mScoreDate)) {
                        // get score object to use its data
                        mScore = mScoringLab.getScore(mScoreDate);
                        // set new score for this category
                        mScore.setLifeSatisfactionScore(score);
                        // save score to database
                        mScoringLab.updateScore(mScore);
                        //
                    } else {
                        mScore = new Score(mScoreDate);
                        mScore.setLifeSatisfactionScore(score);
                        mScoringLab.addScore(mScore);
                    }

                    // update
                    if (mScoringLab.isRaw(mScoreDate)) {
                        mRaw = mScoringLab.getRaw(mScoreDate);
                        mRaw.setLifeSatisfaction(mQuestion_1_index,
                                mQuestion_2_index, mQuestion_3_index, mQuestion_4_index, mQuestion_5_index, mQuestion_6_index, mQuestion_7_index,
                                mQuestion_8_index, mQuestion_9_index, mQuestion_10_index, mQuestion_11_index);
                        mScoringLab.updateRaw(mRaw);

                    } else { // add
                        mRaw = new Raw(mScoreDate);
                        mRaw.setLifeSatisfaction(mQuestion_1_index,
                                mQuestion_2_index, mQuestion_3_index, mQuestion_4_index, mQuestion_5_index, mQuestion_6_index, mQuestion_7_index,
                                mQuestion_8_index, mQuestion_9_index, mQuestion_10_index, mQuestion_11_index);
                        mScoringLab.addRaw(mRaw);
                    }
                    Log.d(TAG, "raw object: " + mRaw);

                    if (score != ScoringAlgorithms.SCORE_BALANCED) {
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

        setButtonsFromDatabase();
        return v;
    }

    /**
     * if there is saved raw data for this date, sets the selected buttons to match
     */
    private void setButtonsFromDatabase() {
        if (mScoringLab.isRaw(mScoreDate)) {
            mRaw = mScoringLab.getRaw(mScoreDate);

            clearSelected(mIcon1a, mIcon1b, mIcon1c);
            clearSelected(mIcon2a, mIcon2b, mIcon2c);
            clearSelected(mIcon3a, mIcon3b, mIcon3c);
            clearSelected(mIcon4a, mIcon4b, mIcon4c);
            clearSelected(mIcon5a, mIcon5b, mIcon5c);
            clearSelected(mIcon6a, mIcon6b, mIcon6c);
            clearSelected(mIcon7a, mIcon7b, mIcon7c);
            clearSelected(mIcon8a, mIcon8b, mIcon8c);
            clearSelected(mIcon9a, mIcon9b, mIcon9c);
            clearSelected(mIcon10a, mIcon10b, mIcon10c);
            clearSelected(mIcon11a, mIcon11b, mIcon11c);

            if (mRaw.getLifeSatisfaction1() != 0) {
                mQuestion_1_index = mRaw.getLifeSatisfaction1();
                switch (mQuestion_1_index) {
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
            if (mRaw.getLifeSatisfaction2() != 0) {
                mQuestion_2_index = mRaw.getLifeSatisfaction2();
                switch (mQuestion_2_index) {
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
            if (mRaw.getLifeSatisfaction3() != 0) {
                mQuestion_3_index = mRaw.getLifeSatisfaction3();
                switch (mQuestion_3_index) {
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

            if (mRaw.getLifeSatisfaction4() != 0) {
                mQuestion_4_index = mRaw.getLifeSatisfaction4();
                switch (mQuestion_4_index) {
                    case ScoringAlgorithms.INPUT_a:
                        mIcon4a.setBackground(getResources().getDrawable(R.drawable.ic_wide_border_selected));
                        break;
                    case ScoringAlgorithms.INPUT_b:
                        mIcon4b.setBackground(getResources().getDrawable(R.drawable.ic_wide_border_selected));
                        break;
                    case ScoringAlgorithms.INPUT_c:
                        mIcon4c.setBackground(getResources().getDrawable(R.drawable.ic_wide_border_selected));
                        break;
                }
            }
            if (mRaw.getLifeSatisfaction5() != 0) {
                mQuestion_5_index = mRaw.getLifeSatisfaction5();
                switch (mQuestion_5_index) {
                    case ScoringAlgorithms.INPUT_a:
                        mIcon5a.setBackground(getResources().getDrawable(R.drawable.ic_wide_border_selected));
                        break;
                    case ScoringAlgorithms.INPUT_b:
                        mIcon5b.setBackground(getResources().getDrawable(R.drawable.ic_wide_border_selected));
                        break;
                    case ScoringAlgorithms.INPUT_c:
                        mIcon5c.setBackground(getResources().getDrawable(R.drawable.ic_wide_border_selected));
                        break;
                }
            }
            if (mRaw.getLifeSatisfaction6() != 0) {
                mQuestion_6_index = mRaw.getLifeSatisfaction6();
                switch (mQuestion_6_index) {
                    case ScoringAlgorithms.INPUT_a:
                        mIcon6a.setBackground(getResources().getDrawable(R.drawable.ic_wide_border_selected));
                        break;
                    case ScoringAlgorithms.INPUT_b:
                        mIcon6b.setBackground(getResources().getDrawable(R.drawable.ic_wide_border_selected));
                        break;
                    case ScoringAlgorithms.INPUT_c:
                        mIcon6c.setBackground(getResources().getDrawable(R.drawable.ic_wide_border_selected));
                        break;
                }
            }

            if (mRaw.getLifeSatisfaction7() != 0) {
                mQuestion_7_index = mRaw.getLifeSatisfaction7();
                switch (mQuestion_7_index) {
                    case ScoringAlgorithms.INPUT_a:
                        mIcon7a.setBackground(getResources().getDrawable(R.drawable.ic_wide_border_selected));
                        break;
                    case ScoringAlgorithms.INPUT_b:
                        mIcon7b.setBackground(getResources().getDrawable(R.drawable.ic_wide_border_selected));
                        break;
                    case ScoringAlgorithms.INPUT_c:
                        mIcon7c.setBackground(getResources().getDrawable(R.drawable.ic_wide_border_selected));
                        break;
                }
            }

            if (mRaw.getLifeSatisfaction8() != 0) {
                mQuestion_8_index = mRaw.getLifeSatisfaction8();
                switch (mQuestion_8_index) {
                    case ScoringAlgorithms.INPUT_a:
                        mIcon8a.setBackground(getResources().getDrawable(R.drawable.ic_wide_border_selected));
                        break;
                    case ScoringAlgorithms.INPUT_b:
                        mIcon8b.setBackground(getResources().getDrawable(R.drawable.ic_wide_border_selected));
                        break;
                    case ScoringAlgorithms.INPUT_c:
                        mIcon8c.setBackground(getResources().getDrawable(R.drawable.ic_wide_border_selected));
                        break;
                }
            }

            if (mRaw.getLifeSatisfaction9() != 0) {
                mQuestion_9_index = mRaw.getLifeSatisfaction9();
                switch (mQuestion_9_index) {
                    case ScoringAlgorithms.INPUT_a:
                        mIcon9a.setBackground(getResources().getDrawable(R.drawable.ic_wide_border_selected));
                        break;
                    case ScoringAlgorithms.INPUT_b:
                        mIcon9b.setBackground(getResources().getDrawable(R.drawable.ic_wide_border_selected));
                        break;
                    case ScoringAlgorithms.INPUT_c:
                        mIcon9c.setBackground(getResources().getDrawable(R.drawable.ic_wide_border_selected));
                        break;
                }
            }

            if (mRaw.getLifeSatisfaction10() != 0) {
                mQuestion_10_index = mRaw.getLifeSatisfaction10();
                switch (mQuestion_10_index) {
                    case ScoringAlgorithms.INPUT_a:
                        mIcon10a.setBackground(getResources().getDrawable(R.drawable.ic_wide_border_selected));
                        break;
                    case ScoringAlgorithms.INPUT_b:
                        mIcon10b.setBackground(getResources().getDrawable(R.drawable.ic_wide_border_selected));
                        break;
                    case ScoringAlgorithms.INPUT_c:
                        mIcon10c.setBackground(getResources().getDrawable(R.drawable.ic_wide_border_selected));
                        break;
                }
            }

            if (mRaw.getLifeSatisfaction11() != 0) {
                mQuestion_11_index = mRaw.getLifeSatisfaction11();
                switch (mQuestion_11_index) {
                    case ScoringAlgorithms.INPUT_a:
                        mIcon11a.setBackground(getResources().getDrawable(R.drawable.ic_wide_border_selected));
                        break;
                    case ScoringAlgorithms.INPUT_b:
                        mIcon11b.setBackground(getResources().getDrawable(R.drawable.ic_wide_border_selected));
                        break;
                    case ScoringAlgorithms.INPUT_c:
                        mIcon11c.setBackground(getResources().getDrawable(R.drawable.ic_wide_border_selected));
                        break;
                }
            }
        }
    }

    /**
     * exits out of the question fragment if all questions have been answered.
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
     * clears all selected buttons for a given question
     */
    private void clearSelected(TextView questionButton1, TextView questionButton2, TextView questionButton3) {
        questionButton1.setBackground(getResources().getDrawable(R.drawable.ic_wide_border));
        questionButton2.setBackground(getResources().getDrawable(R.drawable.ic_wide_border));
        questionButton3.setBackground(getResources().getDrawable(R.drawable.ic_wide_border));
    }
}
