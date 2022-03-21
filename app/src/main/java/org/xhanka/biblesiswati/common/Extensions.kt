package org.xhanka.biblesiswati.common

import android.content.Context
import android.util.TypedValue
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.res.ResourcesCompat
import androidx.preference.PreferenceManager


fun TextView.setTextSizeSp(float: Float) {
    setTextSize(TypedValue.COMPLEX_UNIT_SP, float)
}

fun TextView.setTextColor2(@ColorRes color: Int) {
    setTextColor(ResourcesCompat.getColor(this.context.resources, color, null))
}

class Utils {
    companion object {
        private fun getDarkMode(context: Context?): String {
            context?.let {
                val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(it)
                return sharedPreferences.getString(Constants.DARK_MODE, "0").toString()
            } ?: run {
                return "0"
            }
        }

        fun setDarkMode(darkMode: String) {
            when (darkMode) {
                "0" -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
                "1" -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                "2" -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            }
        }

        fun toggleDarkMode(context: Context?) {
            context?.let {
                setDarkMode(getDarkMode(it))
            }
        }
    }
}
