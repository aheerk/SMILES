package macewan_dust.smiles;

import android.os.Bundle;
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

public class InputSleepFragment extends Fragment {

    private static final String TAG = "InputSleepFragment";
    private static final int NO_SELECTION = 100000;

    ImageView mIcon1a;
    ImageView mIcon1b;
    ImageView mIcon1c;
    ImageView mIcon1d;
    ImageView mIcon2a;
    ImageView mIcon2b;
    ImageView mIcon2c;
    ImageView mIcon2d;
    ImageView mIcon2e;

    Boolean m2aSelected;
    Boolean m2bSelected;
    Boolean m2cSelected;
    Boolean m2dSelected;
    Boolean m2eSelected;

    Button mButton;
    ImageView mIconFeedback;
    TextView mResults;

    int mQuestion_A_index;

    Score mScore;

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


        getActivity().setTitle(R.string.title_quest_sleep);

        mButton = v.findViewById(R.id.score_button);

        mIcon1a = v.findViewById(R.id.icon_1a);
        mIcon1b = v.findViewById(R.id.icon_1b);
        mIcon1c = v.findViewById(R.id.icon_1c);
        mIcon1d = v.findViewById(R.id.icon_1d);
        mIcon2a = v.findViewById(R.id.icon_2a);
        mIcon2b = v.findViewById(R.id.icon_2b);
        mIcon2c = v.findViewById(R.id.icon_2c);
        mIcon2d = v.findViewById(R.id.icon_2d);
        mIcon2e = v.findViewById(R.id.icon_2e);

        m2aSelected = false;
        m2bSelected = false;
        m2cSelected = false;
        m2dSelected = false;
        m2eSelected = false;

        mQuestion_A_index = NO_SELECTION;

        mIconFeedback = v.findViewById(R.id.icon_feedback);

        mIcon1a.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearSelected();
                mQuestion_A_index = ScoringAlgorithms.INPUT_a;
                mIcon1a.setBackground(getResources().getDrawable(R.drawable.border_image_selected));
            }
        });

        mIcon1b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearSelected();
                mQuestion_A_index = ScoringAlgorithms.INPUT_b;
                mIcon1b.setBackground(getResources().getDrawable(R.drawable.border_image_selected));
            }
        });

        mIcon1c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearSelected();
                mQuestion_A_index = ScoringAlgorithms.INPUT_c;;
                mIcon1c.setBackground(getResources().getDrawable(R.drawable.border_image_selected));
            }
        });

        mIcon1d.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearSelected();
                mQuestion_A_index = ScoringAlgorithms.INPUT_d;
                mIcon1d.setBackground(getResources().getDrawable(R.drawable.border_image_selected));
            }
        });

        mIcon2a.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                m2aSelected = !m2aSelected;
                if (m2aSelected)
                    mIcon2a.setBackground(getResources().getDrawable(R.drawable.border_image_selected));
                else
                    mIcon2a.setBackground(getResources().getDrawable(R.drawable.border_image));
            }
        });

        mIcon2b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                m2bSelected = !m2bSelected;
                if (m2bSelected)
                    mIcon2b.setBackground(getResources().getDrawable(R.drawable.border_image_selected));
                else
                    mIcon2b.setBackground(getResources().getDrawable(R.drawable.border_image));
            }
        });

        mIcon2c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                m2cSelected = !m2cSelected;
                if (m2cSelected)
                    mIcon2c.setBackground(getResources().getDrawable(R.drawable.border_image_selected));
                else
                    mIcon2c.setBackground(getResources().getDrawable(R.drawable.border_image));
            }
        });

        mIcon2d.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                m2dSelected = !m2dSelected;
                if (m2dSelected)
                    mIcon2d.setBackground(getResources().getDrawable(R.drawable.border_image_selected));
                else
                    mIcon2d.setBackground(getResources().getDrawable(R.drawable.border_image));
            }
        });

        mIcon2e.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                m2eSelected = !m2eSelected;
                if (m2eSelected)
                    mIcon2e.setBackground(getResources().getDrawable(R.drawable.border_image_selected));
                else
                    mIcon2e.setBackground(getResources().getDrawable(R.drawable.border_image));
            }
        });

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mQuestion_A_index == NO_SELECTION) {
                    mResults.setText(getString(R.string.feedback_unselected));

                } else {

                    int counter = 0;

                    if (m2aSelected)
                        counter ++;
                    if (m2bSelected)
                        counter ++;
                    if (m2cSelected)
                        counter ++;
                    if (m2dSelected)
                        counter ++;
                    if (m2eSelected)
                        counter ++;
                    Log.d(TAG, "Hinderance counter:" + counter );

                    int score = ScoringAlgorithms.scoreSleep(mQuestion_A_index, counter);

                    int scoreStringID = 1000; // just here to initialize
                    Log.d(TAG, "Score: " + score);
                    switch (score) {

                        case ScoringAlgorithms.SCORE_HIGH:
                            scoreStringID = R.string.score_high;
                            mIconFeedback.setBackground(getResources().getDrawable(R.drawable.border_image_high));
                            break;
                        case ScoringAlgorithms.SCORE_LOW:
                            scoreStringID = R.string.score_low;
                            mIconFeedback.setBackground(getResources().getDrawable(R.drawable.border_image_low));
                            break;
                        case ScoringAlgorithms.SCORE_BALANCED:
                            scoreStringID = R.string.score_balanced;
                            mIconFeedback.setBackground(getResources().getDrawable(R.drawable.border_image_balanced));
                            break;
                        case ScoringAlgorithms.SCORE_OFF:
                            scoreStringID = R.string.score_unbalanced;
                            mIconFeedback.setBackground(getResources().getDrawable(R.drawable.border_image_unbalanced));
                            break;
                        case ScoringAlgorithms.SCORE_ERROR:
                            Toast t1 = new Toast(getContext());
                            t1.setText("Error returned from scoring algorithm");
                            t1.show();
                            break;
                        default:
                            Toast t2 = new Toast(getContext());
                            t2.setText("Error in scoring");
                            t2.show();
                    }

                    // if score exists, update it, else make a new one and save it
                    if (ScoringLab.get(getActivity()).isScore(new Date())){
                        // get UUID of score with today's date
                        UUID tempID = ScoringLab.get(getActivity()).getScoreID(Score.timelessDate(new Date())); // ------ consider making a get score by id instead of two separate functions
                        // get score object to use its data
                        mScore = ScoringLab.get(getActivity()).getScore(tempID);
                        // set new score for this category
                        mScore.setSleepScore(score);
                        // save score to database
                        ScoringLab.get(getActivity()).updateScore(mScore);
                        //
                    } else {
                        mScore = new Score();
                        mScore.setSleepScore(score);
                        ScoringLab.get(getActivity()).addScore(mScore);
                    }

                    mResults.setText(getString(R.string.quest_results, getString(scoreStringID)));
                }
            }
        });

        mResults = v.findViewById(R.id.text_score); // note this object is invisible
        mResults.setVisibility(View.VISIBLE);

        return v;
    }

    /**
     * ideally the icon being selected woudnt need to be cleared, if its id was passed in here              // refactor potential
     */
    private void clearSelected() {
        mIcon1a.setBackground(getResources().getDrawable(R.drawable.border_image));
        mIcon1b.setBackground(getResources().getDrawable(R.drawable.border_image));
        mIcon1c.setBackground(getResources().getDrawable(R.drawable.border_image));
        mIcon1d.setBackground(getResources().getDrawable(R.drawable.border_image));
    }

};
