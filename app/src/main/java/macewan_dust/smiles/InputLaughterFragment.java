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

import java.util.Random;

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




        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // 1) implement on click listeners for icons
                // 2) find the index of the last image clicked
                // 3) send that index to the scoring algorithm
                // 4) delete random below
                rand = new Random().nextInt(5) + 1; // random is default to 0-bound. so 1-5 requires adding 1
                Log.d(TAG, "random int: " + rand);
                int score = mScoreLab.scoreLaughter(rand);                 ////           currently hard wired to random int


                int scoreStringID = 1000; // just here to initialize

                switch (score){

                    case ScoreLab.SCORE_HIGH:
                        scoreStringID = R.string.score_high;
                        mResults.setTextColor(getResources().getColor(R.color.colorHigh));
                        break;
                    case ScoreLab.SCORE_LOW:
                        scoreStringID = R.string.score_high;
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
                mResults.setVisibility(View.VISIBLE);
                mResults.setText(getString(R.string.quest_results, getString(scoreStringID)));




            }
        });



        mResults = v.findViewById(R.id.text_score); // note this object is invisible




        mResults.setVisibility(View.VISIBLE);


        return v;
    }

};
