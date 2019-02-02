package macewan_dust.smiles;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.view.MenuItem;

/**
 * This activity exists to hold a fragment.
 */
public class MainActivity extends SingleFragmentActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    // The first fragment launched is specified here
    @Override
    protected Fragment createFragment() {
        return DashboardFragment.newInstance();
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);

        // load default fragment
        //   loadFragment(new DashboardFragment());                                // this is already done in the createFragment function above.
        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(this);
    }

    /**
     * Credit:
     * https://www.simplifiedcoding.net/bottom-navigation-android-example/
     *
     * @param fragment
     * @return
     */
    public boolean loadFragment(Fragment fragment) {
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit();
            return true;
        }
        return false;
    }

    /**
     * Loads fragments when navigation icons are selected
     * <p>
     * Credit:
     * https://www.simplifiedcoding.net/bottom-navigation-android-example/
     *
     * @param item - navigation item
     * @return - fragment to load
     */
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment = null;

        switch (item.getItemId()) {
            case R.id.navigation_dashboard:
                fragment = new DashboardFragment();
                break;
            case R.id.navigation_graph:
                fragment = new GraphFragment();
                break;
            case R.id.navigation_info:
                fragment = new InfoListFragment();
                break;
        }

        return loadFragment(fragment);
    }
}
