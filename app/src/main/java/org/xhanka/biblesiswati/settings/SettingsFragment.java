package org.xhanka.biblesiswati.settings;

import android.os.Bundle;

import androidx.preference.PreferenceFragmentCompat;

import org.xhanka.biblesiswati.R;

public class SettingsFragment extends PreferenceFragmentCompat {

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey);

        /*SwitchPreferenceCompat preference = findPreference("dark_mode");
        if (preference != null)
            preference.setOnPreferenceChangeListener((preference1, newValue) -> {
                if ((boolean) newValue)
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                else
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

                return true;
            });*/
    }
}