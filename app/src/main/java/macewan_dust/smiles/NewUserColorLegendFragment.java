package macewan_dust.smiles;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class NewUserColorLegendFragment extends Fragment {

    private static final String TAG = "ColorLegendFragment";

    TextView mTitle;

    private SharedPreferences mPref;
    private SharedPreferences.Editor mEditor;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true); // prevents instance of the fragment from being destroyed on rotation.
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_colors_legend, container, false);

        // swipe code
        v.setOnTouchListener(new OnSwipeTouchListener(getContext()) {
            @Override
            public void onSwipeLeft() {
                // saving value. user is no longer a new user
                mPref = getActivity().getApplicationContext().getSharedPreferences(SettingsFragment.SETTINGS, 0);
                mEditor = mPref.edit();
                mEditor.putBoolean(NewUserFragment.NEW_USER, false);
                mEditor.commit();

                  getActivity().finish();
             //   Intent newActivity = new Intent(getContext(), MainActivity.class);
              //  getContext().startActivity(newActivity);
            }

            @Override
            public void onSwipeRight() {
                Log.d(TAG, "color legend swiping right to new user fragment");
                Fragment tempFrag = new NewUserFragment();
                Bundle opBundle = new Bundle();
                opBundle.putInt(NewUserFragment.INDEX, NewUserFragment.LAST_INDEX);
                tempFrag.setArguments(opBundle);
                replaceFragment(tempFrag);
            }
        });

        return v;
    }

    /**
     * replaceFragment - performs fragment transactions.
     * @param newFragment
     */
    private void replaceFragment(Fragment newFragment) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_new_user_container, newFragment);
      //  transaction.addToBackStack("dashboard");
        transaction.commit();
        Log.d(TAG, "replacing fragment");
    }
}
