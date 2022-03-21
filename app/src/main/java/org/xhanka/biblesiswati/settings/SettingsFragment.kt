package org.xhanka.biblesiswati.settings

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import androidx.preference.ListPreference
import androidx.preference.PreferenceFragmentCompat
import org.xhanka.biblesiswati.R
import org.xhanka.biblesiswati.common.Constants
import org.xhanka.biblesiswati.common.Utils

class SettingsFragment : PreferenceFragmentCompat() {
    override fun onCreate(savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)
        super.onCreate(savedInstanceState)
    }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)

        val darkMode: ListPreference? = findPreference(Constants.DARK_MODE)
        darkMode?.let { it ->
            it.setOnPreferenceChangeListener { _, newValue ->
                Utils.setDarkMode(newValue as String)
                true
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        super.onCreateOptionsMenu(menu, inflater)
    }
}