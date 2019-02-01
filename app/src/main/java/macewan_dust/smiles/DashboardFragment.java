package macewan_dust.smiles;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class DashboardFragment extends Fragment {

    FloatingActionButton mButtonStartDailyQuestions;
    private static final String TAG = "Dashboard";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_dashboard, null);


        mButtonStartDailyQuestions = v.findViewById(R.id.button_start_daily_questions);

        mButtonStartDailyQuestions.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v){



                DailyListActivity fragment = new DailyListActivity();
                replaceFragment(fragment);


           //     Intent startDaily = new Intent(getActivity(), DailyListActivity.class);
           //     startActivity(startDaily);



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
        transaction.replace(R.id.fragment_container, newFragment);
     //   transaction.addToBackStack(null);
        transaction.commit();
        Log.d(TAG, "replacing fragment");
    }


}
