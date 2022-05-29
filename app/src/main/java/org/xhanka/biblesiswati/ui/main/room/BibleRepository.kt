package org.xhanka.biblesiswati.ui.main.room

import android.content.Context
import androidx.preference.PreferenceManager
import dagger.hilt.android.qualifiers.ApplicationContext
import org.xhanka.biblesiswati.common.Utils
import javax.inject.Inject


class BibleRepository @Inject constructor(
    @ApplicationContext context: Context,
) {
    private val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)

    private val defaultDb = sharedPreferences.getString(
        Utils.SETTINGS_BIBLE_VERSION, "siswati"
    )

    val defaultDatabase = Utils.mapDbVersions(defaultDb)
    val textSize = sharedPreferences.getString("text_size", "18")?.toInt()
}