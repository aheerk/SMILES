package macewan_dust.smiles;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;

public class SettingsFragment extends Fragment {

    private static final String TAG = "SettingsFragment";
    public static final String SETTINGS = "smilesSettings";
    public static final String PREF_DAILY_CHALLENGE = "dailyChallengeSettings";
    public static final String PREF_DAILY_WEB = "dailyWebSettings";


    private SharedPreferences mPref;
    private SharedPreferences.Editor mEditor;


    Switch mDailyChallengeSwitch;
    Switch mDailyWebSwitch;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // accessing shared preferences for the app.
        // mode 0 is private. data will not be shared outside of the application.
        mPref = getActivity().getApplicationContext().getSharedPreferences(SETTINGS, 0);
        mEditor = mPref.edit();
    }


    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_settings, container, false);
        getActivity().setTitle(R.string.title_settings);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        mDailyChallengeSwitch = v.findViewById(R.id.setting_switch_daily_challenge);
        mDailyWebSwitch = v.findViewById(R.id.setting_switch_daily_web);

        mDailyChallengeSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "switched");
                    mEditor.putBoolean(PREF_DAILY_CHALLENGE, mDailyChallengeSwitch.isChecked());
                    mEditor.commit();

            }
        });


        mDailyWebSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "switched");
                mEditor.putBoolean(PREF_DAILY_WEB, mDailyWebSwitch.isChecked());
                mEditor.commit();

            }
        });

    /*    mDailySwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                                           @Override
                                                           public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                                               mEditor.putBoolean(PREF_DAILY_CHALLENGE, mDailySwitch.isChecked());
                                                           }
                                                       }
        );*/

        // Ref: https://www.journaldev.com/9412/android-shared-preferences-example-tutorial




        loadPreferences(); // sets default settings in the view from saved data
        return v;
    }


    private void loadPreferences(){
        Log.d(TAG, "loading preferences");
        Boolean temp;
        if (mPref.contains(PREF_DAILY_CHALLENGE)) {
            temp = mPref.getBoolean(PREF_DAILY_CHALLENGE, true);
            mDailyChallengeSwitch.setChecked(temp);
        }

        if (mPref.contains(PREF_DAILY_WEB)) {
            temp = mPref.getBoolean(PREF_DAILY_WEB, true);
            mDailyWebSwitch.setChecked(temp);
        }


        }

}
