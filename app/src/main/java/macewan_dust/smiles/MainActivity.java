package macewan_dust.smiles;

import android.Manifest;
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
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.io.File;
import java.text.DateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * This activity exists to hold a fragment.
 */
public class MainActivity extends SingleFragmentActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = "MainActivity";
    private int minBackstack; // this is the fix for the visual back button bug.

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
        getSupportActionBar().setDisplayShowHomeEnabled(true);


      //  toggleUpButton();
/*
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });*/

        minBackstack = 0;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar, menu);
        return true;
    }

    /**
     * This method handles when the user selects an item on the overflow menu and the back button
     * @param item
     * @return
*/
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            // this home case replaces the onSupportNavigateUp() if implemented
            case android.R.id.home:
                onBackPressed();
                // Log.d(TAG, "up pressed. backstack: " + getSupportFragmentManager().getBackStackEntryCount() + " min backstack: " + minBackstack);
                break;
                
            case R.id.overflow_settings: {
                Log.d(TAG, "Clicked settings.");
                loadFragment(new SettingsListFragment());
                break;
            }
            case R.id.overflow_export: {
                CsvFileManager fileManager = new CsvFileManager(MainActivity.this,
                        getApplicationContext());
                fileManager.exportScores();
                break;
            }
            case R.id.overflow_import: {
                // Need to open up a page for import and show a list of files

                CsvFileManager fileManager = new CsvFileManager(MainActivity.this,
                        getApplicationContext());
                //importCsv(getApplicationContext());
                fileManager.readCSVFile();
                break;
            }
            case R.id.overflow_information: {
                loadFragment(new InformationListFragment());
                break;
            }
        }
        return true;
    }



    @Override
    public void onBackPressed() {
        // fragments are not removed from the backstack when navigation is used. popping them
        // from the backstack causes them to appear briefly before being removed. Instead, this
        // method updates the minimum backstack amount for a base page and exits the app if the
        // back button is pressed from one of these pages.
     //   Log.d(TAG, "back <- pressed. backstack: " +
   //             getSupportFragmentManager().getBackStackEntryCount() + " min backstack: " + minBackstack);




        if (getSupportFragmentManager().getBackStackEntryCount() < minBackstack) {
            //getSupportFragmentManager().popBackStackImmediate(0, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            finish();
        }
        else {
            super.onBackPressed();
        }
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

        minBackstack = getSupportFragmentManager().getBackStackEntryCount() + 1;

     //   Log.d(TAG, "minimum backstack set to: " + minBackstack);
        switch (item.getItemId()) {
            case R.id.navigation_dashboard:
                fragment = new DashboardListFragment();
                break;
            case R.id.navigation_weekly_graph:
                fragment = new WeeklyGraphFragment();
                break;
            case R.id.navigation_monthly_graph:
                fragment = new MonthlyGraphFragment();
                break;
        }
        return loadFragment(fragment);
    }



    /*private void importCsv(Context context) {

        // Check if we have permission to read documents for import
        if (ContextCompat.checkSelfPermission(context,
                Manifest.permission.READ_EXTERNAL_STORAGE)  != PackageManager.PERMISSION_GRANTED) {
            Log.d(TAG, "No permission to read external storage");

            // Show the dialog box to request permissions
            if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,
                    Manifest.permission.READ_EXTERNAL_STORAGE)){
                Log.d(TAG, "Show the rationale!");

                // Show explanation?

            } else {
                Log.d(TAG, "Need to ask for permissions to read external storage.");
                String[] permissions = new String[] {Manifest.permission.READ_EXTERNAL_STORAGE};
                ActivityCompat.requestPermissions(MainActivity.this, permissions, REQUEST_WRITE_EXTERNAL_STORAGE);
            }
        } else {
            Log.d(TAG, "Read permissions granted from previous use.");

            readDocuments();
        }
    }*/


}
