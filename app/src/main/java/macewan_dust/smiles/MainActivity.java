package macewan_dust.smiles;

import android.Manifest;
import android.app.ActionBar;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.Toast;


/**
 * This activity exists to hold a fragment.
 */
public class MainActivity extends SingleFragmentActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = "MainActivity";
    private int minBackstack; // this is the fix for the visual back button bug.
    private int mOperation;

    private static final int OPERATION_OVERFLOW_EXPORT = 88;
    private static final int OPERATION_OVERFLOW_IMPORT = 86;

    private static final int WRITE_REQUEST_CODE = 42;
    private static final int READ_REQUEST_CODE = 45;

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
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

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

        // Referenced https://stackoverflow.com/questions/11930587/change-action-bar-color-in-android
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        }


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
                loadFragment(new SettingsFragment());
                break;
            }
            case R.id.overflow_export: {
                Log.d(TAG, "Exporting....");
                mOperation = OPERATION_OVERFLOW_EXPORT;
                checkPermissions(REQUEST_WRITE_EXTERNAL_STORAGE);
                createFile();
                break;
            }
            case R.id.overflow_import: {
                mOperation = OPERATION_OVERFLOW_IMPORT;
                checkPermissions(REQUEST_READ_EXTERNAL_STORAGE);
                readFiles();
                break;
            }

            case R.id.overflow_color_legend: {
                loadFragment(new ColorLegendFragment());
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

    /**
     * openLink opens the provided link in a web browser
     * Note: This method was placed here to avoid duplicated code in areas
     * that use web links
     * @param uri
     */
    public void openLink(String uri) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
        startActivity(browserIntent);
    }

    /*--- IMPORT & EXPORT -----------------------------------------------------------------------*/
    /**
     * createFile launches an intent that asks the user where to store a new file. The results
     * will be provided in the onActivityResult method
     * Adapted from: https://developer.android.com/guide/topics/providers/document-provider#create
     */
    public void createFile() {
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
        intent.putExtra(Intent.EXTRA_TITLE, CsvFileManager.getFilename());
        startActivityForResult(intent, WRITE_REQUEST_CODE);
    }

    /**
     * readFiles launches an intent that asks the which file they'd like to import. The results
     * will be provided in the onActivityResult method
     * Source: https://developer.android.com/guide/topics/providers/document-provider#java
     */
    private void readFiles() {
        // Return if we don't have permissions
        if (ContextCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.READ_EXTERNAL_STORAGE)  != PackageManager.PERMISSION_GRANTED) {
            Log.d(TAG, "No permissions in readFiles!");
            return;
        }

        // ACTION_OPEN_DOCUMENT is the intent to choose a file via the system's file
        // browser.
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);

        // Filter to only show results that can be "opened", such as a
        // file (as opposed to a list of contacts or timezones)
        intent.addCategory(Intent.CATEGORY_OPENABLE);

        // Filter to show only CSV files
        intent.setType("text/*"); // need to fix this so it's text/csv
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
    public void onActivityResult(int requestCode, int resultCode, Intent resultData) {
        if (resultCode != Activity.RESULT_OK || resultData == null) {
            return;
        }

        Uri uri = resultData.getData();
        Log.i(TAG, "Write Uri: " + uri.toString());

        switch(requestCode) {
            case WRITE_REQUEST_CODE : {
                CsvFileManager.exportData(getApplicationContext(), uri);
                break;
            }
            case READ_REQUEST_CODE: {
                CsvFileManager.importData(getApplicationContext(), uri);
                break;
            }
        }
    }

    /**
     * performOperation opens up the create file dialog for the user according to the
     * current operation code
     */
    public void performOperation() {
        switch(mOperation) {
            case OPERATION_OVERFLOW_EXPORT: {
                createFile();
                break;
            }
            case OPERATION_OVERFLOW_IMPORT: {
                readFiles();
                break;
            }
        }
    }

    /*--------- PERMISSIONs ----------------------------------------------------------------*/

    /**
     * checkWriteExternalPermissions checks if the app has permissions to write to external storage
     * If they already have permission, the user is prompted to create a new csv file.
     *
     * @return
     */
    private void checkPermissions(int requestCode)  {
        // Determine the correct permission code based on the provided request code
        String permissionCode = Manifest.permission.READ_EXTERNAL_STORAGE;
        if (requestCode == REQUEST_WRITE_EXTERNAL_STORAGE) {
            permissionCode = Manifest.permission.WRITE_EXTERNAL_STORAGE;
        }

        // Check if we have permission to export
        if (ContextCompat.checkSelfPermission(getApplicationContext(),
                permissionCode)  != PackageManager.PERMISSION_GRANTED) {

            // Show the dialog box to request permissions if the user previously denied
            // permissions
            if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,
                    permissionCode)){

                // Display a rationale using a dialog. If the user hits yes on the dialog
                // they will adjust their permissions.
                DialogFragment dialog = RationaleDialog.newInstance(permissionCode, requestCode);
                dialog.show(getSupportFragmentManager(), "dialog");

            } else {
                initiatePermissions(permissionCode, requestCode);
            }

        } else {
            Log.d(TAG, "permissions granted from previous use.");
        }
    }

    /**
     * initiatePermissions starts up the permissions dialog
     * Note, the results of that dialog are taken care of in onRequestPermissionsResult
     * @param permissionCode
     * @param requestCode
     */
    public void initiatePermissions(String permissionCode, int requestCode) {
        String[] permissions = new String[] {permissionCode};
        ActivityCompat.requestPermissions(MainActivity.this, permissions,
                requestCode);
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
                    performOperation();
                } else {
                    Log.d(TAG, "No permissions given to write external storage.");
                    Toast.makeText(getApplicationContext(), R.string.csv_no_write_permission, Toast.LENGTH_SHORT).show();
                }
                return;
            }
            case REQUEST_READ_EXTERNAL_STORAGE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.d(TAG, "Read External Storage permission granted.");
                    performOperation();
                } else {
                    Log.d(TAG, "No permissions given to read external storage.");
                    Toast.makeText(getApplicationContext(), R.string.csv_no_read_permission, Toast.LENGTH_SHORT).show();
                }
            }
        }

    }
}
