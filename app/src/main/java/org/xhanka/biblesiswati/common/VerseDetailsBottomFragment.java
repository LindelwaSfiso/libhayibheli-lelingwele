package org.xhanka.biblesiswati.common;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.chip.Chip;

import org.jetbrains.annotations.NotNull;
import org.xhanka.biblesiswati.R;
import org.xhanka.biblesiswati.ui.main.room.BibleViewModel;

import java.util.Objects;

public class VerseDetailsBottomFragment extends BaseBottomRoundView {
    final static String KEY = "ID";
    BibleViewModel model;
    int verseId;

    OnClick onClick;

    public interface OnClick {
        void isClicked(boolean isClicked);
    }

    public static VerseDetailsBottomFragment getInstance(int verseId) {
        VerseDetailsBottomFragment instance = new VerseDetailsBottomFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(KEY, verseId);
        instance.setArguments(bundle);
        return instance;
    }

    public void setOnClick(OnClick onClick) {
        this.onClick = onClick;
    }

    @Override
    public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null)
            verseId = getArguments().getInt(KEY, 0);

        model = Objects.requireNonNull(new ViewModelProvider(this,
                ViewModelProvider.AndroidViewModelFactory.getInstance(Objects.requireNonNull(getActivity()).getApplication())
        ).get(BibleViewModel.class));
    }

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment__verse_details, container, false);
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView translationText = view.findViewById(R.id.translationText);

        model.getAltVerse(verseId).observe(this, translationText::setText);

        Chip favToggle = view.findViewById(R.id.favToggle);

        favToggle.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                favToggle.setChecked(true);
                favToggle.setText(R.string.remove_from_favorites);
                model.addToFavorites(verseId);
                if (onClick != null)
                    onClick.isClicked(true);
            } else {
                favToggle.setChecked(false);
                favToggle.setText(R.string.add_to_favorite);
                model.removeFromFavorites(verseId);
                if (onClick != null)
                    onClick.isClicked(false);
            }
        });

        model.isAddedToFavorites(verseId).observe(this, integer -> {
            if (integer != null && integer == 1) {
                favToggle.setChecked(true);
                favToggle.setText(R.string.remove_from_favorites);
            }
        });
    }
}
