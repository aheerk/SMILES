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

public class InputSpeakingFragment extends Fragment {

    private static final String TAG = "InputSpeakingFragment";
    private static final int NO_SELECTION = 100000;

    // 5 Questions total

    ImageView mIcon1a;
    ImageView mIcon1b;
    ImageView mIcon1c;
    ImageView mIcon1d;
    ImageView mIcon1e;

    ImageView mIcon2a;
    ImageView mIcon2b;

    ImageView mIcon3a;
    ImageView mIcon3b;

    ImageView mIcon4a;
    ImageView mIcon4b;

    ImageView mIcon5a;
    ImageView mIcon5b;

    // Score Button
    Button mButton;

    ImageView mIconFeedback;
    TextView mResults;

    int mQuestion_A_index;
    //The following are indices instead of booleans, as there is no way to indicate
    //that there is no selection with a boolean. Having these as ints allows for us to
    //use the NO_SELECTION constant.
    //The scoring algorithm takes the result of the expression mQuestion_X_index == 1
    int mQuestion_B_index;
    int mQuestion_C_index;
    int mQuestion_D_index;
    int mQuestion_E_index;

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

        getActivity().setTitle(R.string.quest_speaking_title);

        mButton = v.findViewById(R.id.score_button);

        mIcon1a = v.findViewById(R.id.icon_1a);
        mIcon1b = v.findViewById(R.id.icon_1b);
        mIcon1c = v.findViewById(R.id.icon_1c);
        mIcon1d = v.findViewById(R.id.icon_1d);
        mIcon1e = v.findViewById(R.id.icon_1e);

        mIcon2a = v.findViewById(R.id.icon_2a);
        mIcon2b = v.findViewById(R.id.icon_2b);

        mIcon3a = v.findViewById(R.id.icon_3a);
        mIcon3b = v.findViewById(R.id.icon_3b);

        mIcon4a = v.findViewById(R.id.icon_4a);
        mIcon4b = v.findViewById(R.id.icon_4b);

        mIcon5a = v.findViewById(R.id.icon_5a);
        mIcon5b = v.findViewById(R.id.icon_5b);

        mQuestion_A_index = NO_SELECTION;
        mQuestion_B_index = NO_SELECTION;
        mQuestion_C_index = NO_SELECTION;
        mQuestion_D_index = NO_SELECTION;
        mQuestion_E_index = NO_SELECTION;

        mIconFeedback = v.findViewById(R.id.icon_feedback);

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


        // Selected Yes for Q2
        mIcon2a.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mQuestion_B_index = 1;
                mIcon2a.setBackground(getResources().getDrawable(R.drawable.border_image_selected));
                // un-select no if applicable
                mIcon2b.setBackground(getResources().getDrawable(R.drawable.border_image));
            }
        });

        // Selected no for Q2
        mIcon2b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mQuestion_B_index = 2;
                mIcon2b.setBackground(getResources().getDrawable(R.drawable.border_image_selected));
                // un-select no if applicable
                mIcon2a.setBackground(getResources().getDrawable(R.drawable.border_image));
            }
        });


        // Selected Yes
        mIcon3a.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mQuestion_C_index = 1;
                mIcon3a.setBackground(getResources().getDrawable(R.drawable.border_image_selected));
                // un-select no if applicable
                mIcon3b.setBackground(getResources().getDrawable(R.drawable.border_image));
            }
        });

        // Selected No
        mIcon3b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mQuestion_C_index = 2;
                mIcon3b.setBackground(getResources().getDrawable(R.drawable.border_image_selected));
                // un-select no if applicable
                mIcon3a.setBackground(getResources().getDrawable(R.drawable.border_image));
            }
        });


        // Selected Yes
        mIcon4a.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mQuestion_D_index = 1;
                mIcon4a.setBackground(getResources().getDrawable(R.drawable.border_image_selected));
                // un-select no if applicable
                mIcon4b.setBackground(getResources().getDrawable(R.drawable.border_image));
            }
        });

        // Selected No
        mIcon4b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mQuestion_D_index = 2;
                mIcon4b.setBackground(getResources().getDrawable(R.drawable.border_image_selected));
                // un-select no if applicable
                mIcon4a.setBackground(getResources().getDrawable(R.drawable.border_image));
            }
        });


        // Selected Yes
        mIcon5a.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mQuestion_E_index = 1;
                mIcon5a.setBackground(getResources().getDrawable(R.drawable.border_image_selected));
                // un-select no if applicable
                mIcon5b.setBackground(getResources().getDrawable(R.drawable.border_image));
            }
        });

        // Selected No
        mIcon5b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mQuestion_E_index = 2;
                mIcon5b.setBackground(getResources().getDrawable(R.drawable.border_image_selected));
                // un-select no if applicable
                mIcon5a.setBackground(getResources().getDrawable(R.drawable.border_image));
            }
        });


        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mQuestion_A_index == NO_SELECTION || mQuestion_B_index == NO_SELECTION ||
                        mQuestion_C_index == NO_SELECTION || mQuestion_D_index == NO_SELECTION ||
                        mQuestion_E_index == NO_SELECTION) {
                    mResults.setText(getString(R.string.feedback_unselected));

                } else {

                    int score = ScoringAlgorithms.scoreSpeaking(mQuestion_A_index,
                            mQuestion_B_index == 1,
                            mQuestion_C_index == 1,
                            mQuestion_D_index == 1,
                            mQuestion_E_index == 1);

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
        mIcon1a.setBackground(getResources().getDrawable(R.drawable.border_image));
        mIcon1b.setBackground(getResources().getDrawable(R.drawable.border_image));
        mIcon1c.setBackground(getResources().getDrawable(R.drawable.border_image));
        mIcon1d.setBackground(getResources().getDrawable(R.drawable.border_image));
        mIcon1e.setBackground(getResources().getDrawable(R.drawable.border_image));
    }

}
