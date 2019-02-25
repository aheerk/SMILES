package macewan_dust.smiles;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class SmilesInfoFragment extends Fragment {

    int mIconID;
    int mTextID;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true); // prevents instance of the fragment from being destroyed on rotation.

       // mIconID = this.getArguments().getInt(SmilesListFragment.ARG_OPERATION_ID_ICON);
       // mTextID = this.getArguments().getInt(SmilesListFragment.ARG_OPERATION_ID_TEXT);
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_smiles, container, false);

      //  getActivity().setTitle(R.string.introduction_title_smiles);




        return v;
    }

}
