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
        fun handleDarkMode(turnOnDarkMode: Boolean, context: Context?) {
            val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)

            if (turnOnDarkMode && AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
                sharedPreferences.edit().putBoolean(Constants.DARK_MODE, true).apply()
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                return
            }

            // IF CONTEXT IS NOT NULL, HANDLE DARK MODE
            context.let {
                if (turnOnDarkMode)
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                else
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

                sharedPreferences.edit().putBoolean(Constants.DARK_MODE, turnOnDarkMode).apply()
            }
        }

        fun getDarkMode(context: Context?): Boolean {
            context.let {
                val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
                return sharedPreferences.getBoolean(Constants.DARK_MODE, false)
            }
        }

        fun toggleDarkMode(context: Context?) {
            handleDarkMode(getDarkMode(context), context)
        }
    }
}
