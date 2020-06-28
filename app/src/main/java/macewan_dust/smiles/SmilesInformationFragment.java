package macewan_dust.smiles;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class SmilesInformationFragment extends Fragment {

    private static final String TAG = "SmilesInformationFragment";

    int mAdapterIndex;
    ImageView mIconSmilesInfo;
    TextView mTextSmilesList;
    LinearLayout mSwipeLayout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true); // prevents instance of the fragment from being destroyed on rotation.

        // get index of recycler view selected
        mAdapterIndex = this.getArguments().getInt(SmilesListFragment.ARG_OPERATION_INDEX);
        Log.d("TAG", "index: " + mAdapterIndex);
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_smiles_descriptions, container, false);

        mIconSmilesInfo = v.findViewById(R.id.icon_smiles_info);
        mTextSmilesList = v.findViewById(R.id.text_smiles_info);
        mSwipeLayout = v.findViewById(R.id.swipe_layout);

        // swipe code
        v.setOnTouchListener(new OnSwipeTouchListener(getContext()) {
            @Override
            public void onSwipeLeft() {
                if (mAdapterIndex<6){
                    mAdapterIndex++;
                }
                updateScreen();
            }

            @Override
            public void onSwipeRight() {
                if (mAdapterIndex>0){
                    mAdapterIndex--;
                }
                updateScreen();
            }
        });

        updateScreen();
        v.invalidate();
        return v;
    }

    private void updateScreen() {


        switch(mAdapterIndex){
            case 0:
                getActivity().setTitle(R.string.title_sleep);
                mIconSmilesInfo.setImageResource(R.drawable.photo_sleep);
                mSwipeLayout.setVisibility(View.VISIBLE);
                mTextSmilesList.setText(R.string.introduction_explanation_sleep);
                break;
            case 1:
                getActivity().setTitle(R.string.title_movement);
                mIconSmilesInfo.setImageResource(R.drawable.photo_movement);
                mSwipeLayout.setVisibility(View.GONE);
                mTextSmilesList.setText(R.string.introduction_explanation_movement);
                break;
            case 2:
                getActivity().setTitle(R.string.title_imagination);
                mIconSmilesInfo.setImageResource(R.drawable.photo_imagination);
                mTextSmilesList.setText(R.string.introduction_explanation_imagination);
                mSwipeLayout.setVisibility(View.GONE);
                break;
            case 3:
                getActivity().setTitle(R.string.title_life_satisfaction);
                mIconSmilesInfo.setImageResource(R.drawable.photo_life_satisfaction);
                mTextSmilesList.setText(R.string.introduction_explanation_laughter);
                mSwipeLayout.setVisibility(View.GONE);
                break;
            case 4:
                getActivity().setTitle(R.string.title_eating);
                mIconSmilesInfo.setImageResource(R.drawable.photo_eating);
                mTextSmilesList.setText(R.string.introduction_explanation_eating);
                mSwipeLayout.setVisibility(View.GONE);
                break;
            case 5:
                getActivity().setTitle(R.string.title_speaking);
                mIconSmilesInfo.setImageResource(R.drawable.photo_speaking);
                mTextSmilesList.setText(R.string.introduction_explanation_speaking);
                mSwipeLayout.setVisibility(View.GONE);
                break;
            default:
                Log.d("TAG", "error");
        }
    }
}
