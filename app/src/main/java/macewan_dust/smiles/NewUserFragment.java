package macewan_dust.smiles;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class NewUserFragment extends Fragment {

    private static final String TAG = "NewUserFragment";

    TextView mTitle;
    TextView mSubtitle;
    ImageView mImageView;
    TextView mBodyText;
    Button mNextButton;
    Button mBackButton;
    int i; // screen index

    public static NewUserFragment newInstance() {
        return new NewUserFragment();
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true); // prevents instance of the fragment from being destroyed on rotation.
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_introduction, container, false);

        mTitle = v.findViewById(R.id.introduction_title);
        mSubtitle = v.findViewById(R.id.introduction_subtitle);
        mImageView = v.findViewById(R.id.introduction_image);
        mBodyText = v.findViewById(R.id.introduction_explanation);
        mNextButton = v.findViewById(R.id.button_introduction_next);
        mBackButton = v.findViewById(R.id.button_introduction_back);
        mBackButton.setVisibility(View.GONE);
        updateScreen ();

        i = 0;
        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (i<12){
                    i++;
                }
                updateScreen();
            }
        });

        mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (i>0){
                    i--;
                }
                updateScreen();
            }
        });

        v.invalidate();

        return v;
    }

    private void updateScreen (){
        switch(i){
            case 0:
                mTitle.setText(R.string.introduction_title_smiles);
                mSubtitle.setText(R.string.introduction_subtitle_smiles);
                mSubtitle.setVisibility(View.VISIBLE);
                mImageView.setImageResource(R.drawable.photo_intro1);
                mBodyText.setText(R.string.introduction_explanation_smiles);
                mBackButton.setVisibility(View.GONE);
                break;
            case 1:
                mTitle.setText(R.string.introduction_title_stress1);
                mBodyText.setText(R.string.introduction_explanation_stress1);
                mImageView.setImageResource(R.drawable.photo_intro2);
                mSubtitle.setVisibility(View.GONE);
                mBackButton.setVisibility(View.VISIBLE);

                break;
            case 2:
                mTitle.setText(R.string.introduction_title_stress1);
                mImageView.setImageResource(R.drawable.photo_intro3);
                mBodyText.setText(R.string.introduction_explanation_stress2);
                break;
            case 3:
                mTitle.setText(R.string.introduction_title_balance);
                mImageView.setImageResource(R.drawable.photo_intro4);
                mBodyText.setText(R.string.introduction_explanation_balance);
                break;
            case 4:
                mTitle.setText("Sleep");
                mImageView.setImageResource(R.drawable.icon_sleep);
                mBodyText.setText(R.string.introduction_explanation_sleep);
                break;
            case 5:
                mTitle.setText("Movement");
                mImageView.setImageResource(R.drawable.icon_movement);
                mBodyText.setText(R.string.introduction_explanation_movement);
                break;
            case 6:
                mTitle.setText("Imagination");
                mImageView.setImageResource(R.drawable.photo_imagination);
                mBodyText.setText(R.string.introduction_explanation_imagination);
                break;
            case 7:
                mTitle.setText("Laughter");
                mImageView.setImageResource(R.drawable.photo_laughter);
                mBodyText.setText(R.string.introduction_explanation_laughter);
                break;
            case 8:
                mTitle.setText("Eating");
                mImageView.setImageResource(R.drawable.photo_eating);
                mBodyText.setText(R.string.introduction_explanation_eating);
                break;
            case 9:
                mTitle.setText(getString(R.string.title_quest_speaking));
                mImageView.setImageResource(R.drawable.icon_speaking);
                mBodyText.setText(R.string.introduction_explanation_speaking);
                break;
            case 10:
                mTitle.setText(R.string.introduction_title_goals);
                mBodyText.setText(R.string.introduction_explanation_goals);
                mImageView.setImageResource(R.drawable.icon_plus);

                break;
            default:
                mTitle.setText(R.string.no_data);
                mBodyText.setText(R.string.no_data);

        }
    }



    /**
     * replaceFragment - performs fragment transactions.
     * @param newFragment
     */
    private void replaceFragment(Fragment newFragment) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, newFragment);
        transaction.addToBackStack("dashboard");
        transaction.commit();
        Log.d(TAG, "replacing fragment");
    }

}
