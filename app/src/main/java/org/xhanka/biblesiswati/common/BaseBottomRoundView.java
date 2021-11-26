package org.xhanka.biblesiswati.common;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import org.jetbrains.annotations.NotNull;
import org.xhanka.biblesiswati.R;

public class BaseBottomRoundView extends BottomSheetDialogFragment {
    @Override
    public int getTheme() {
        return R.style.BottomSheetDialog;
    }

    @NonNull
    @NotNull
    @Override
    @SuppressLint({"RestrictedApi", "VisibleForTests"})
    public Dialog onCreateDialog(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        BottomSheetDialog bottomSheetDialog = (BottomSheetDialog) super.onCreateDialog(savedInstanceState);
        bottomSheetDialog.getBehavior().disableShapeAnimations();
        return bottomSheetDialog;
    }
}
