package macewan_dust.smiles;

import android.support.v4.app.Fragment;

/**
 * This activity exists to hold a fragment.
 */
public class MainActivity extends SingleFragmentActivity {


    @Override
    protected Fragment createFragment() {
        return InputLaughterFragment.newInstance();
    }


}
