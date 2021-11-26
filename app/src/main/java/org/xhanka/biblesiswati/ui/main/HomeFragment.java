package org.xhanka.biblesiswati.ui.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import org.jetbrains.annotations.NotNull;
import org.xhanka.biblesiswati.R;

public class HomeFragment extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle bundle = new Bundle();
        view.findViewById(R.id.home_new_card_view).setOnClickListener(v -> {

            //bundle.putString("book_name", "Genesis");
            //bundle.putInt("chapter_num", 1);
            //Navigation.findNavController(view).navigate(R.id.nav_verse_details, bundle);
            bundle.putInt("testament", 0);
            Navigation.findNavController(view).navigate(R.id.action_nav_home_to_nav_books, bundle);
        });

        view.findViewById(R.id.home_old_card_view).setOnClickListener(v -> {
            bundle.putInt("testament", 1);
            Navigation.findNavController(view).navigate(R.id.action_nav_home_to_nav_books, bundle);
        });
    }
}