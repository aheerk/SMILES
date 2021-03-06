package macewan_dust.smiles;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class IntroductionFragment extends Fragment {

    TextView mTitle;
    TextView mSubtitle;
    TextView mBodyText;
    ImageView mImageView;
    LinearLayout mSwipeLayout;

    int i;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true); // prevents instance of the fragment from being destroyed on rotation.

        ((AppCompatActivity)getActivity()).getSupportActionBar();
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_introduction, container, false);

        getActivity().setTitle(R.string.title_introduction);

        mTitle = v.findViewById(R.id.introduction_title);
        mBodyText = v.findViewById(R.id.introduction_explanation);
        mSubtitle = v.findViewById(R.id.introduction_subtitle);
        mImageView = v.findViewById(R.id.introduction_image);
        mSwipeLayout = v.findViewById(R.id.swipe_layout);
        updateScreen ();
        i = 0;

        // swipe code
        v.setOnTouchListener(new OnSwipeTouchListener(getContext()) {
            @Override
            public void onSwipeLeft() {
                if (i<5){
                    i++;
                }
                updateScreen();
            }

            @Override
            public void onSwipeRight() {
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
                mSwipeLayout.setVisibility(View.VISIBLE);
                mImageView.setImageResource(R.drawable.photo_intro1);
                mBodyText.setText(R.string.introduction_explanation_smiles);
                break;
            case 1:
                mTitle.setText(R.string.introduction_title_stress1);
                mImageView.setImageResource(R.drawable.photo_intro2);
                mBodyText.setText(R.string.introduction_explanation_stress1);
                mSubtitle.setVisibility(View.GONE);
                mSwipeLayout.setVisibility(View.GONE);
                break;
            case 2:
                mImageView.setImageResource(R.drawable.photo_intro3);
                mTitle.setText(R.string.introduction_title_stress1);
                mBodyText.setText(R.string.introduction_explanation_stress2);
                break;
            case 3:
                mTitle.setText(R.string.introduction_title_balance);
                mImageView.setImageResource(R.drawable.photo_intro4);
                mBodyText.setText(R.string.introduction_explanation_balance);
                break;
            case 4:
                mTitle.setText(R.string.introduction_title_goals);
                mImageView.setImageResource(R.drawable.photo_goals);
                mBodyText.setText(R.string.introduction_explanation_goals);
                break;
            case 5:
                mTitle.setText(R.string.introduction_title_goals);
                mBodyText.setText(R.string.introduction_explanation_goals);
                mImageView.setImageResource(R.drawable.photo_goals);
                break;
        }
    }
}
