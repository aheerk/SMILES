package macewan_dust.smiles;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class NewUserFragment extends Fragment {

    public static final String INDEX = "index number";
    public static final int LAST_INDEX = 11;
    public static final int FIRST_INDEX = 0;
    public static final String NEW_USER = "new_user";
    private static final String TAG = "NewUserFragment";

    TextView mTitle;
    TextView mSubtitle;
    ImageView mImageView;
    TextView mBodyText;
    int i; // screen index

    public static NewUserFragment newInstance() {
        return new NewUserFragment();
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true); // prevents instance of the fragment from being destroyed on rotation.
        i = 0;
        if (this.getArguments().getInt(INDEX) != 0){
            i = this.getArguments().getInt(INDEX) - 1; // -1 to move back one fragment
        }
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_introduction, container, false);

        mTitle = v.findViewById(R.id.introduction_title);
        mSubtitle = v.findViewById(R.id.introduction_subtitle);
        mImageView = v.findViewById(R.id.introduction_image);
        mBodyText = v.findViewById(R.id.introduction_explanation);
        updateScreen ();


        // swipe code
        v.setOnTouchListener(new OnSwipeTouchListener(getContext()) {
            @Override
            public void onSwipeLeft() {
                if (i<12){
                    i++;
                }
                updateScreen();
            }

            @Override
            public void onSwipeRight() {
                if (i>FIRST_INDEX){
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
            case FIRST_INDEX:
                mTitle.setText(R.string.introduction_title_smiles);
                mSubtitle.setText(R.string.introduction_subtitle_smiles);
                mSubtitle.setVisibility(View.VISIBLE);
                mImageView.setImageResource(R.drawable.photo_intro1);
                mBodyText.setText(R.string.introduction_explanation_smiles);
                break;
            case 1:
                mTitle.setText(R.string.introduction_title_stress1);
                mBodyText.setText(R.string.introduction_explanation_stress1);
                mImageView.setImageResource(R.drawable.photo_intro2);
                mSubtitle.setVisibility(View.GONE);
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
                mTitle.setText(R.string.title_sleep);
                mImageView.setImageResource(R.drawable.photo_sleep);
                mBodyText.setText(R.string.introduction_explanation_sleep);
                break;
            case 5:
                mTitle.setText(R.string.title_movement);
                mImageView.setImageResource(R.drawable.photo_movement);
                mBodyText.setText(R.string.introduction_explanation_movement);
                break;
            case 6:
                mTitle.setText(R.string.title_imagination);
                mImageView.setImageResource(R.drawable.photo_imagination);
                mBodyText.setText(R.string.introduction_explanation_imagination);
                break;
            case 7:
                mTitle.setText(R.string.title_laughter);
                mImageView.setImageResource(R.drawable.photo_laughter);
                mBodyText.setText(R.string.introduction_explanation_laughter);
                break;
            case 8:
                mTitle.setText(R.string.title_eating);
                mImageView.setImageResource(R.drawable.photo_eating);
                mBodyText.setText(R.string.introduction_explanation_eating);
                break;
            case 9:
                mTitle.setText(R.string.title_speaking);
                mImageView.setImageResource(R.drawable.photo_speaking);
                mBodyText.setText(R.string.introduction_explanation_speaking);
                break;
            case 10:
                mTitle.setText(R.string.introduction_title_goals);
                mBodyText.setText(R.string.introduction_explanation_goals);
                mImageView.setImageResource(R.drawable.photo_goals);
                mSubtitle.setVisibility(View.GONE); // must hide this again when creating the fragment from the end
                break;
            case LAST_INDEX:
                replaceFragment(new NewUserColorLegendFragment());
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
        transaction.replace(R.id.fragment_new_user_container, newFragment);
        transaction.addToBackStack("dashboard");
        transaction.commit();
        Log.d(TAG, "replacing fragment");
    }
}
