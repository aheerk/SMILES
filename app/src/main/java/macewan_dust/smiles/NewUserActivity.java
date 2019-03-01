package macewan_dust.smiles;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
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
        Fragment fragment = fm.findFragmentById(R.id.fragment_new_user_container);

        if (fragment == null) {
            fragment = createFragment();
            fm.beginTransaction()
                    .add(R.id.fragment_new_user_container, fragment)
                    .commit();
        }
    }

}
