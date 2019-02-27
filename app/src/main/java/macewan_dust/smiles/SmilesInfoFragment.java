package macewan_dust.smiles;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.LinkedList;
import java.util.List;

public class SmilesInfoFragment extends Fragment {

    private static final String TAG = "SmilesInfoFragment";

    int mAdapterIndex;

    ImageView mIconSmilesInfo;
    TextView mIconSmilesList;

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
        View v = inflater.inflate(R.layout.fragment_smiles_info, container, false);


        mIconSmilesInfo = v.findViewById(R.id.icon_smiles_info);
        mIconSmilesList = v.findViewById(R.id.icon_smiles_list);


        switch(mAdapterIndex){
            case 0:
                getActivity().setTitle("Sleep");
                mIconSmilesInfo.setImageResource(R.drawable.icon_sleep);
                mIconSmilesList.setText(R.string.introduction_explanation_sleep);
                break;
            case 1:
                getActivity().setTitle("Movement");
                mIconSmilesInfo.setImageResource(R.drawable.icon_movement);
                mIconSmilesList.setText(R.string.introduction_explanation_movement);
                break;
            case 2:
                getActivity().setTitle("Imagination");
                mIconSmilesInfo.setImageResource(R.drawable.icon_imagination);
                mIconSmilesList.setText(R.string.introduction_explanation_imagination);
                break;
            case 3:
                getActivity().setTitle("Laughter");
                mIconSmilesInfo.setImageResource(R.drawable.icon_laughter);
                mIconSmilesList.setText(R.string.introduction_explanation_laughter);
                break;
            case 4:
                getActivity().setTitle("Eating");
                mIconSmilesInfo.setImageResource(R.drawable.icon_eating);
                mIconSmilesList.setText(R.string.introduction_explanation_eating);
                break;
            case 5:
                getActivity().setTitle("Speaking");
                mIconSmilesInfo.setImageResource(R.drawable.icon_speaking);
                mIconSmilesList.setText(R.string.introduction_explanation_speaking);
                break;

        }


        return v;
    }

}
