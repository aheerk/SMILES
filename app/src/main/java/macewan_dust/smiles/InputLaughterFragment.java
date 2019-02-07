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

public class InputLaughterFragment extends Fragment {

    private static final String TAG = "InputLaughterFragment";
    private static final int NO_SELECTION = 100000;

    ImageView mIcon1;
    ImageView mIcon2;
    ImageView mIcon3;
    ImageView mIcon4;
    ImageView mIcon5;
    Button mButton;
    ImageView mIconFeedback;
    TextView mResults;

    int mQuestion_A_index;


    /**
     * new instance constructor
     *
     * @return InputSleepFragment
     */
    public static InputLaughterFragment newInstance() {
        return new InputLaughterFragment();
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

        View v = inflater.inflate(R.layout.fragment_daily_laughter_questions, container, false);

        getActivity().setTitle(R.string.quest_laughter_title);

        mButton = v.findViewById(R.id.score_button);

        mIcon1 = v.findViewById(R.id.icon_1a);
        mIcon2 = v.findViewById(R.id.icon_1b);
        mIcon3 = v.findViewById(R.id.icon_1c);
        mIcon4 = v.findViewById(R.id.icon_1d);
        mIcon5 = v.findViewById(R.id.icon_1e);

        mQuestion_A_index = NO_SELECTION;

        mIconFeedback = v.findViewById(R.id.icon_feedback);

        mIcon1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearSelected();
                mQuestion_A_index = 1;
                mIcon1.setBackground(getResources().getDrawable(R.drawable.border_image_selected));
            }
        });

        mIcon2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearSelected();
                mQuestion_A_index = 2;
                mIcon2.setBackground(getResources().getDrawable(R.drawable.border_image_selected));
            }
        });

        mIcon3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearSelected();
                mQuestion_A_index = 3;
                mIcon3.setBackground(getResources().getDrawable(R.drawable.border_image_selected));
            }
        });

        mIcon4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearSelected();
                mQuestion_A_index = 4;
                mIcon4.setBackground(getResources().getDrawable(R.drawable.border_image_selected));
            }
        });

        mIcon5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearSelected();
                mQuestion_A_index = 5;
                mIcon5.setBackground(getResources().getDrawable(R.drawable.border_image_selected));
            }
        });

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mQuestion_A_index == NO_SELECTION) {
                    mResults.setText(getString(R.string.feedback_unselected));

                } else {

                    int score = ScoringAlgorithms.scoreLaughter(mQuestion_A_index);

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
        mIcon1.setBackground(getResources().getDrawable(R.drawable.border_image));
        mIcon2.setBackground(getResources().getDrawable(R.drawable.border_image));
        mIcon3.setBackground(getResources().getDrawable(R.drawable.border_image));
        mIcon4.setBackground(getResources().getDrawable(R.drawable.border_image));
        mIcon5.setBackground(getResources().getDrawable(R.drawable.border_image));
    }

};
