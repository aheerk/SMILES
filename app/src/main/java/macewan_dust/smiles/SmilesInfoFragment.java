package macewan_dust.smiles;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class SmilesInfoFragment extends Fragment {

    private static final String TAG = "SmilesInfoFragment";

    int mAdapterIndex;

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






        getActivity().setTitle(R.string.introduction_title_smiles); // UPDATE TITLE --------------------







        return v;
    }

}
