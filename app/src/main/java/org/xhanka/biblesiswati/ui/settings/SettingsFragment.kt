package org.xhanka.biblesiswati.ui.settings

import android.os.Bundle
import androidx.fragment.app.activityViewModels
import androidx.preference.ListPreference
import androidx.preference.PreferenceFragmentCompat
import org.xhanka.biblesiswati.R
import org.xhanka.biblesiswati.common.Utils
import org.xhanka.biblesiswati.ui.main.room.BibleViewModel

/**
 * @author Dlamini Lindelwa Sifiso [21-May-2022]
 */
class SettingsFragment : PreferenceFragmentCompat() {

    val bibleViewModel by activityViewModels<BibleViewModel>()

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)

        val darkMode: ListPreference? = findPreference(Utils.SETTINGS_DARK_MODE)
        darkMode?.let { it ->
            it.setOnPreferenceChangeListener { _, newValue ->
                Utils.setDarkMode(newValue as String)
                true
            }
        }

        val textSize: ListPreference? = findPreference(Utils.SETTINGS_TEXT_SIZE)
        textSize?.let {
            it.setOnPreferenceChangeListener { _, newValue ->
                bibleViewModel.setTextSizeValue(newValue as String)
                true
            }
        }

        bibleViewModel.installedVersions.let { installed ->
            val bibleVersion: ListPreference? = findPreference(Utils.SETTINGS_BIBLE_VERSION)
            bibleVersion?.let {
                it.entries = installed.map { x -> x.versionName }.toTypedArray()
                it.entryValues = installed.map { x -> x.versionEntryValue }.toTypedArray()

                it.setOnPreferenceChangeListener { _, newValue ->
                    bibleViewModel.setVersion(newValue as String)
                    true
                }
            }
        }
    }
}