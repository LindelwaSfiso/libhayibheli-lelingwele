package org.xhanka.biblesiswati.common

import android.util.TypedValue
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.core.content.res.ResourcesCompat


fun TextView.setTextSizeSp(float: Float) {
    setTextSize(TypedValue.COMPLEX_UNIT_SP, float)
}

fun TextView.setTextColor2(@ColorRes color: Int) {
    setTextColor(ResourcesCompat.getColor(this.context.resources, color, null))
}
