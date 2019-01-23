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

public class InputLaughterFragment extends Fragment {

    public static final String TAG = "InputLaughterFragment";

    TextView mTitle;
    TextView mQuestion_A;
    ImageView mIcon1;
    ImageView mIcon2;
    ImageView mIcon3;
    ImageView mIcon4;
    ImageView mIcon5;
    Button mButton;
    TextView mResults;

    int mQuestion_A_index;

    ScoreLab mScoreLab;


    // temp
    int rand;

    public static InputLaughterFragment newInstance() {
        return new InputLaughterFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true); // prevents instance of the fragment from being destroyed on rotation.

        mScoreLab = new ScoreLab();

    }


    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_input, container, false);

        mTitle = v.findViewById(R.id.title);    // links the java object to the layout object
        mTitle.setText(R.string.quest_laughter_title); // sets the text as that in the strings file

        mQuestion_A = v.findViewById(R.id.question_text_1);
        mQuestion_A.setText(R.string.quest_laughter_a);

        mButton = v.findViewById(R.id.score_button);
        //  mButton.setText(R.string.quest_button);       // done in layout

        mIcon1 = v.findViewById(R.id.icon_1a);
        mIcon2 = v.findViewById(R.id.icon_1b);
        mIcon3 = v.findViewById(R.id.icon_1c);
        mIcon4 = v.findViewById(R.id.icon_1d);
        mIcon5 = v.findViewById(R.id.icon_1e);

        mQuestion_A_index = 0;


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

                int score = mScoreLab.scoreLaughter(mQuestion_A_index);


                int scoreStringID = 1000; // just here to initialize
                Log.d(TAG, "Score: " + score);
                switch (score) {

                    case ScoreLab.SCORE_HIGH:
                        scoreStringID = R.string.score_high;
                        mResults.setTextColor(getResources().getColor(R.color.colorHigh));
                        break;
                    case ScoreLab.SCORE_LOW:
                        scoreStringID = R.string.score_low;
                        mResults.setTextColor(getResources().getColor(R.color.colorLow));

                        break;
                    case ScoreLab.SCORE_BALANCED:
                        scoreStringID = R.string.score_balanced;
                        mResults.setTextColor(getResources().getColor(R.color.colorBalanced));

                        break;
                    case ScoreLab.SCORE_OFF:
                        scoreStringID = R.string.score_unbalanced;
                        mResults.setTextColor(getResources().getColor(R.color.colorUnbalanced));

                        break;
                }

//                mResults.setVisibility(View.VISIBLE);
                mResults.setText(getString(R.string.quest_results, getString(scoreStringID)));
            }
        });

        mResults = v.findViewById(R.id.text_score); // note this object is invisible

        mResults.setVisibility(View.VISIBLE);


        return v;
    }


    private void clearSelected(){
        mIcon1.setBackground(getResources().getDrawable(R.drawable.border_image));
        mIcon2.setBackground(getResources().getDrawable(R.drawable.border_image));
        mIcon3.setBackground(getResources().getDrawable(R.drawable.border_image));
        mIcon4.setBackground(getResources().getDrawable(R.drawable.border_image));
        mIcon5.setBackground(getResources().getDrawable(R.drawable.border_image));

    }

};
