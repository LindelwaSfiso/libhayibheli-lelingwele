package org.xhanka.biblesiswati.common

import android.annotation.SuppressLint
import android.app.Dialog
import android.os.Bundle
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import org.xhanka.biblesiswati.R

open class BaseBottomRoundView : BottomSheetDialogFragment() {
    override fun getTheme(): Int {
        return R.style.BottomSheetDialog
    }

    @SuppressLint("RestrictedApi", "VisibleForTests")
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val bottomSheetDialog = super.onCreateDialog(savedInstanceState) as BottomSheetDialog
        bottomSheetDialog.behavior.disableShapeAnimations()
        return bottomSheetDialog
    }
}