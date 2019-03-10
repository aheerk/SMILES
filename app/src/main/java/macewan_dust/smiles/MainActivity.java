package macewan_dust.smiles;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

/**
 * This activity exists to hold a fragment.
 */
public class MainActivity extends SingleFragmentActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = "MainActivity";
    private static final int REQUEST_EXTERNAL_STORAGE_USE = 222;


    // The first fragment launched is specified here
    @Override
    protected Fragment createFragment() {
        return DashboardListFragment.newInstance();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);

        // bottom navigation
        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(this);

        // toolbar
        // reference: https://stackoverflow.com/questions/26651602/display-back-arrow-on-toolbar
        // https://developer.android.com/training/appbar/setting-up#java
        Toolbar toolbar = findViewById(R.id.toolbar);


        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
       // getSupportActionBar().setDisplayShowHomeEnabled(true);
/*
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });*/

        this.getPermissions(getApplicationContext());
    }

    /**
     * when navigation back button is pressed, do the same as pressing the phone back button.
     * @return
     */
    @Override
    public boolean onSupportNavigateUp() {
        // note: FragmentManager is not used in this program Instead, SupportFragmentManager is used.

        // back button only works within the app and does not close the base pages.
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {

            Log.d(TAG, "backstack count: " + getSupportFragmentManager().getBackStackEntryCount());
            onBackPressed();
        }
        return true;
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
     * Reference: https://www.simplifiedcoding.net/bottom-navigation-android-example/
     *
     * @param item - navigation item
     * @return - fragment to load
     */
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment = null;

        switch (item.getItemId()) {
            case R.id.navigation_dashboard:
                fragment = new DashboardListFragment();
                break;
            case R.id.navigation_graph:
                fragment = new WeeklyGraphFragment();
                break;
            case R.id.navigation_info:
                fragment = new InformationListFragment();
                break;
        }

        return loadFragment(fragment);
    }

    public void getPermissions(Context context) {
        if (ContextCompat.checkSelfPermission(context,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)  != PackageManager.PERMISSION_GRANTED) {
            Log.d(TAG, "No permission to access external storage");

            // Show the dialog box to request permissions
            if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)){
                Log.d(TAG, "Show the rationale!");
                // Show explanation?
            } else {
                Log.d(TAG, "Need to ask for permissions....");
                String[] permissions = new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE};
                ActivityCompat.requestPermissions(MainActivity.this, permissions, REQUEST_EXTERNAL_STORAGE_USE);
            }
        } else {
            Log.d(TAG, "Permissions granted from previous use.");
            ScoringLab lab = new ScoringLab(MainActivity.this);
            lab.writeCSVFile("scores.csv");
        }
    }

    /**
     * Determines whether permissions have been given and disables corresponding functionality
     * accordingly
     *
     * @param requestCode Unique code used to identify a permission request
     * @param permissions
     * @param grantResults
     */
    @Override
    public  void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {

            case REQUEST_EXTERNAL_STORAGE_USE: {

                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.d(TAG, "Permission granted.");
                    Toast.makeText(MainActivity.this, R.string.balanced, Toast.LENGTH_SHORT);

                    //ScoringLab lab = new ScoringLab(MainActivity.this);
                    //lab.writeCSVFile("COOKIES.txt");

                } else {
                    Log.d(TAG, "No permissions given.");
                    Toast.makeText(MainActivity.this, R.string.unbalanced, Toast.LENGTH_SHORT);
                }
                return;
            }
        }

    }
}
