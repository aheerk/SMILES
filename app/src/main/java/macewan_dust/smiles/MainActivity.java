package macewan_dust.smiles;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
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


/**
 * This activity exists to hold a fragment.
 */
public class MainActivity extends SingleFragmentActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = "MainActivity";
    private int minBackstack; // this is the fix for the visual back button bug.
    private int mOperation;

    private static final int OPERATION_OVERFLOW_EXPORT_RESPONSES = 88;
    private static final int OPERATION_OVERFLOW_EXPORT_SCORES = 89;

    private static final int WRITE_RESPONSES_REQUEST_CODE = 42;
    private static final int WRITE_SCORES_REQUEST_CODE = 43;

    private static final int READ_REQUEST_CODE = 44;

    private static final int REQUEST_WRITE_EXTERNAL_STORAGE = 222;
    private static final int REQUEST_READ_EXTERNAL_STORAGE = 111;

    // The first fragment launched is specified here
    @Override
    protected Fragment createFragment() {
        return DashboardListFragment.newInstance();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);

        mOperation = 0;

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
            case R.id.overflow_export_responses: {
                mOperation = OPERATION_OVERFLOW_EXPORT_RESPONSES;
                checkPermissions("");
                createFile("responses" + CsvFileManager.getFilename(),
                        WRITE_RESPONSES_REQUEST_CODE);
                break;
            }

            case R.id.overflow_export_scores: {
                mOperation = OPERATION_OVERFLOW_EXPORT_SCORES;
                checkPermissions("");
                createFile("scores" + CsvFileManager.getFilename(),
                        WRITE_SCORES_REQUEST_CODE);
                break;
            }
            case R.id.overflow_import: {
                //heckPermissionsAndReadFiles();
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

    /*--- IMPORT & EXPORT -----------------------------------------------------------------------*/
    /**
     * createFile launches an intent that asks the user where to store a new file. The results
     * will be provided in the onActivityResult method
     * Adapted from: https://developer.android.com/guide/topics/providers/document-provider#create
     */
    public void createFile(String filename, int requestCode) {
        // Return if we don't have permissions
        if (ContextCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE)  != PackageManager.PERMISSION_GRANTED) {
            Log.d(TAG, "No permissions in createFile!");
            return;
        }

        Intent intent = new Intent(Intent.ACTION_CREATE_DOCUMENT);

        // Filter to only show results that can be "opened", such as
        // a file (as opposed to a list of contacts or timezones).
        intent.addCategory(Intent.CATEGORY_OPENABLE);

        // Create a csv file type.
        intent.setType("text/csv");
        intent.putExtra(Intent.EXTRA_TITLE, filename);
        startActivityForResult(intent, requestCode);
    }

    /**
     * readFiles launches an intent that asks the which file they'd like to import. The results
     * will be provided in the onActivityResult method
     * Source: https://developer.android.com/guide/topics/providers/document-provider#java
     */
    private void readFiles() {
        // ACTION_OPEN_DOCUMENT is the intent to choose a file via the system's file
        // browser.
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);

        // Filter to only show results that can be "opened", such as a
        // file (as opposed to a list of contacts or timezones)
        intent.addCategory(Intent.CATEGORY_OPENABLE);

        // Filter to show only CSV files
        intent.setType("text/csv");
        startActivityForResult(intent, READ_REQUEST_CODE);
    }

    /**
     * onActivityResult is called by Android after an intent is created such as the one initiated
     * in createScoreFile.
     * SOURCE: https://developer.android.com/guide/topics/providers/document-provider#create
     * @param requestCode
     * @param resultCode
     * @param resultData
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode,
                                 Intent resultData) {
        if (resultCode != Activity.RESULT_OK || resultData == null) {
            return;
        }

        Uri uri = null;
        uri = resultData.getData();
        Log.i(TAG, "Write Uri: " + uri.toString());

        switch(requestCode) {
            case WRITE_SCORES_REQUEST_CODE : {
                CsvFileManager.writeScoresFile(MainActivity.this, getApplicationContext(), uri);
                break;
            }
            case WRITE_RESPONSES_REQUEST_CODE: {
                CsvFileManager.writeResponsesFile(MainActivity.this, getApplicationContext(), uri);
                break;
            }
            case READ_REQUEST_CODE: {
                CsvFileManager.importCSVFile(MainActivity.this, getApplicationContext(), uri);
                break;
            }
        }
    }

    /*--------- PERMISSIONs ----------------------------------------------------------------*/

    /**
     * checkWriteExternalPermissions checks if the app has permissions to write to external storage
     * If they already have permission, the user is prompted to create a new csv file.
     *
     * @param rationale
     * @return
     */
    private void checkPermissions(String rationale)  {
        // Check if we have permission to export
        if (ContextCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE)  != PackageManager.PERMISSION_GRANTED) {
            Log.d(TAG, "No permission to write to external storage");

            // Show the dialog box to request permissions
            if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)){
                Log.d(TAG, "Show the rationale!");

                // Show explanation?

            } else {
                Log.d(TAG, "Need to ask for permissions to write external storage.");
                String[] permissions = new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE};
                ActivityCompat.requestPermissions(MainActivity.this, permissions,
                        REQUEST_WRITE_EXTERNAL_STORAGE);
            }
        } else {
            Log.d(TAG, "Write permissions granted from previous use.");
        }
    }

    /**
     * Determines whether permissions for external storage use
     * have been given and disables corresponding functionality accordingly
     * Android calls this after requesting permissions
     *
     * @param requestCode Unique code used to identify a permission request
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {

            case REQUEST_WRITE_EXTERNAL_STORAGE: {

                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.d(TAG, "Write External Storage permission granted.");

                    performWrite();

                } else {
                    Log.d(TAG, "No permissions given to write external storage.");
                    Toast.makeText(getApplicationContext(), R.string.csv_no_write_permission, Toast.LENGTH_SHORT).show();
                }
                return;
            }
            case REQUEST_READ_EXTERNAL_STORAGE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.d(TAG, "Read External Storage permission granted.");
                } else {
                    Log.d(TAG, "No permissions given to read external storage.");
                    Toast.makeText(getApplicationContext(), R.string.csv_no_read_permission, Toast.LENGTH_SHORT).show();
                }
            }
        }

    }

    /**
     * performWrite opens up the create file dialog for the user according to the
     * current operation code
     */
    public void performWrite() {
        switch(mOperation) {
            case OPERATION_OVERFLOW_EXPORT_RESPONSES: {
                createFile("responses" + CsvFileManager.getFilename(),
                        WRITE_RESPONSES_REQUEST_CODE);
                break;
            }
            case OPERATION_OVERFLOW_EXPORT_SCORES: {
                createFile("scores" + CsvFileManager.getFilename(),
                        WRITE_SCORES_REQUEST_CODE);
                break;
            }
        }
    }

}
