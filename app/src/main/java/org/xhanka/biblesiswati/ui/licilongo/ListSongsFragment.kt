package org.xhanka.biblesiswati.ui.licilongo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;
import org.xhanka.biblesiswati.R;
import org.xhanka.biblesiswati.ui.licilongo.room.SongViewModel;

import java.util.Objects;


public class ListSongsFragment extends Fragment {

    SongViewModel model;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //setHasOptionsMenu(true);
        model = new ViewModelProvider(
                this, ViewModelProvider.AndroidViewModelFactory.getInstance(
                Objects.requireNonNull(getActivity()).getApplication()
        )).get(SongViewModel.class);

        return inflater.inflate(R.layout.fragment_list_songs, container, false);
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.addItemDecoration(new DividerItemDecoration(
                view.getContext(), DividerItemDecoration.VERTICAL));

        LicilongoAdapter adapter = new LicilongoAdapter(Navigation.findNavController(view));
        recyclerView.setAdapter(adapter);

        model.getSongs().observe(this, adapter::submitList);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull @NotNull Menu menu, @NonNull @NotNull MenuInflater inflater) {
        // todo: implement searching
        //menu.clear();
        //inflater.inflate(R.menu.menu_licilongo, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }
}