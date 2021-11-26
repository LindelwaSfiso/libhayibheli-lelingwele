package org.xhanka.biblesiswati.ui.fav_verses;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;
import org.xhanka.biblesiswati.R;
import org.xhanka.biblesiswati.ui.main.room.BibleViewModel;

import java.util.Objects;

public class FavoriteVersesFragment extends Fragment {

    BibleViewModel model;

    public FavoriteVersesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorite_verses, container, false);
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setHasOptionsMenu(true);

        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.addItemDecoration(new DividerItemDecoration(view.getContext(), DividerItemDecoration.VERTICAL));
        final NavController nav = Navigation.findNavController(view);

        model = Objects.requireNonNull(new ViewModelProvider(this,
                ViewModelProvider.AndroidViewModelFactory.getInstance(Objects.requireNonNull(getActivity()).getApplication())
        ).get(BibleViewModel.class));

        FavoritesAdapter adapter = new FavoritesAdapter(
                model, nav
        );

        recyclerView.setAdapter(adapter);

        model.getAllFavorites().observe(this, verses -> {
            adapter.submitList(verses);
            if (verses.isEmpty())
                view.findViewById(R.id.emptyView).setVisibility(View.VISIBLE);
            else
                view.findViewById(R.id.emptyView).setVisibility(View.INVISIBLE);
        });
    }

    @Override
    public void onCreateOptionsMenu(@NonNull @NotNull Menu menu, @NonNull @NotNull MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.menu_favorites, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull @NotNull MenuItem item) {
        if (item.getItemId() == R.id.action_clear_favorites) {
            model.clearAllFavorites();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}