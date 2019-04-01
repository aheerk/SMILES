package macewan_dust.smiles;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.IntentCompat;
import android.support.v7.app.AppCompatActivity;

/**
 * This activity exists to hold a fragment.
 */
public class NewUserActivity extends AppCompatActivity  {

    private static final String TAG = "NewUserActivity";

    /**
     * new instance constructor
     * @return InputSleepFragment
     */
    public static NewUserActivity newInstance() {
        return new NewUserActivity();
    }


    // The first fragment launched is specified here
    protected Fragment createFragment() {
        return NewUserFragment.newInstance();
    }

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_user_fragment);

        FragmentManager fm = getSupportFragmentManager();
        Fragment tempFrag = fm.findFragmentById(R.id.fragment_new_user_container);

       // Fragment tempFrag = new NewUserFragment();


        // the op bundle could be removed if i found a way to check if a bundle exists in the fragment.
        // this first_index prevents the null object crash. the value of interest comes fomr color legend fragment.
        if (tempFrag == null) {
            tempFrag = createFragment();
            Bundle opBundle = new Bundle();
            opBundle.putInt(NewUserFragment.INDEX, NewUserFragment.FIRST_INDEX);
            tempFrag.setArguments(opBundle);
            fm.beginTransaction()
                    .add(R.id.fragment_new_user_container, tempFrag)
                    .commit();
        }
    }

    /**
     * disabled on back to prevent skipping the introduction
     */
    @Override
    public void onBackPressed() {
        finishAffinity(); // finishes all activities
            //finish();
    }

}
