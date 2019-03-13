package macewan_dust.smiles;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.content.Context;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class NewUserFragment extends Fragment implements OnTouchListener {

    private static final String TAG = "NewUserFragment";

    TextView mTitle;
    TextView mSubtitle;
    ImageView mImageView;
    TextView mBodyText;
    Button mNextButton;
    Button mBackButton;
    int i; // screen index

    private GestureDetector gestureDetector;

    public static NewUserFragment newInstance() {
        return new NewUserFragment();
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        gestureDetector = new GestureDetector(getContext(), new GestureListener());
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

    /**
     * Reference:
     * https://stackoverflow.com/questions/4139288/android-how-to-handle-right-to-left-swipe-gestures
     */

    /**
     *
     * @param view
     * @param motionEvent
     * @return
     */
    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        return gestureDetector.onTouchEvent(motionEvent);
    }

    private final class GestureListener extends SimpleOnGestureListener {

        private static final int SWIPE_THRESHOLD = 100;
        private static final int SWIPE_VELOCITY_THRESHOLD = 100;

        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            boolean result = false;
            try {
                float diffY = e2.getY() - e1.getY();
                float diffX = e2.getX() - e1.getX();
                if (Math.abs(diffX) > Math.abs(diffY)) {
                    if (Math.abs(diffX) > SWIPE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
                        if (diffX > 0) {
                            onSwipeRight();
                        } else {
                            onSwipeLeft();
                        }
                        result = true;
                    }
                }
            } catch (Exception exception) {
                exception.printStackTrace();
            }
            return result;
        }
    }

    public void onSwipeRight() {
        if (i<12){
            i++;
        }
        updateScreen();
    }

    public void onSwipeLeft() {
        if (i>0){
            i--;
        }
        updateScreen();
    }
    
}
