package com.example.submission5.notification;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.SwitchPreference;
import android.provider.Settings;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.submission5.R;

public class SettingPref extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getFragmentManager().beginTransaction().replace(android.R.id.content, new SettingsFragment()).commit();
    }


    public static class SettingsFragment extends PreferenceFragment implements Preference.OnPreferenceChangeListener, SharedPreferences.OnSharedPreferenceChangeListener {
        private boolean DEFAULT_VALUE = true;

        private SwitchPreference mSwitchToday;
        private SwitchPreference mSwitchRilis;
        private MovieDailyRemainder remainderDaily = new MovieDailyRemainder();

        @RequiresApi(api = Build.VERSION_CODES.M)
        @Override
        public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
            if (s.equals(getString(R.string.key_today_reminder))){
                mSwitchToday.setChecked(sharedPreferences.getBoolean(getString(R.string.key_today_reminder),DEFAULT_VALUE));
            }else if (s.equals(getString(R.string.key_release_reminder))){
                mSwitchRilis.setChecked(sharedPreferences.getBoolean(getString(R.string.key_release_reminder),DEFAULT_VALUE));

            }
            assert getContext() != null;
            if (s.equals(getString(R.string.key_today_reminder)) && sharedPreferences.getBoolean(getString(R.string.key_today_reminder), false)) {
                // set Alarm idon't know
                String message = getString(R.string.daily_message);
                remainderDaily.setAlarm(getActivity(), MovieDailyRemainder.TYPE_DAILY,message);
            } else if (s.equals(getString(R.string.key_today_reminder)) && !sharedPreferences.getBoolean(getString(R.string.key_today_reminder), false)) {
                //disable that
                remainderDaily.setCancel(getActivity(), MovieDailyRemainder.TYPE_DAILY);
            }
            if (s.equals(getString(R.string.key_release_reminder)) && sharedPreferences.getBoolean(getString(R.string.key_release_reminder), false)) {
                String message = getString(R.string.daily_none);
                remainderDaily.setAlarm(getContext(), MovieDailyRemainder.TYPE_RELEASE, message);

            } else if (s.equals(getString(R.string.key_release_reminder)) && !sharedPreferences.getBoolean(getString(R.string.key_release_reminder), false)) {
                remainderDaily.setCancel(getContext(), MovieDailyRemainder.TYPE_RELEASE);
            }

        }


        private void init(){
            mSwitchToday = (SwitchPreference) findPreference(getString(R.string.key_today_reminder));

            mSwitchRilis = (SwitchPreference) findPreference(getString(R.string.key_release_reminder));
        }
        private void setSummeries(){
            SharedPreferences sharedPreferences = getPreferenceManager().getSharedPreferences();
            mSwitchToday.setChecked(sharedPreferences.getBoolean(getString(R.string.key_today_reminder),DEFAULT_VALUE));
            mSwitchRilis.setChecked(sharedPreferences.getBoolean(getString(R.string.key_release_reminder),DEFAULT_VALUE));
        }
        @RequiresApi(api = Build.VERSION_CODES.M)
        private void setAutoCondition(){
            assert getContext() != null;
            if (mSwitchToday.isChecked()){
                String message = getString(R.string.daily_message);

                remainderDaily.setAlarm(getContext(), MovieDailyRemainder.TYPE_DAILY,message);
            }else {
                remainderDaily.setCancel(getContext(), MovieDailyRemainder.TYPE_DAILY);
            }
            if (mSwitchRilis.isChecked()){
                String message = getString(R.string.daily_none);
                remainderDaily.setAlarm(getContext(), MovieDailyRemainder.TYPE_RELEASE,message);
            }else {
                remainderDaily.setCancel(getContext(), MovieDailyRemainder.TYPE_RELEASE);
            }
        }

        @Override
        public void onResume() {
            super.onResume();
            getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
        }

        @Override
        public void onPause() {
            super.onPause();
            getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);

        }

        @Override
        public boolean onPreferenceChange(android.preference.Preference preference, Object newValue) {


            return false;
        }


        @RequiresApi(api = Build.VERSION_CODES.M)
        @Override
        public void onCreate(final Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.setting_pref);
            init();
            setSummeries();
            setAutoCondition();
            Preference myPref = findPreference(getString(R.string.key_lang));

            myPref.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                public boolean onPreferenceClick(Preference preference) {
                    startActivity(new Intent(Settings.ACTION_LOCALE_SETTINGS));
                    return true;
                }
            });
        }
    }
}