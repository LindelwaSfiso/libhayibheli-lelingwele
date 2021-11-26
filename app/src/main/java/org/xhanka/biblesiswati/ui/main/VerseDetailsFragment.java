package org.xhanka.biblesiswati.ui.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;
import org.xhanka.biblesiswati.R;
import org.xhanka.biblesiswati.ui.main.adapter.VerseDetailsAdapter;
import org.xhanka.biblesiswati.ui.main.room.BibleViewModel;

import java.util.Objects;

public class VerseDetailsFragment extends Fragment {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setSharedElementEnterTransition(TransitionInflater.from(getContext()).inflateTransition(android.R.transition.move));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_verse_details, container, false);
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.addItemDecoration(new DividerItemDecoration(
                view.getContext(),
                DividerItemDecoration.VERTICAL)
        );


        String book_name = getArguments() != null ? getArguments().getString("book_name") : "genesis";
        int chapter_num = getArguments() != null ? getArguments().getInt("chapter_num") : 1;


        BibleViewModel bibleViewModel = new ViewModelProvider(
                this,
                ViewModelProvider.AndroidViewModelFactory.getInstance(
                        Objects.requireNonNull(getActivity()).getApplication())
        ).get(BibleViewModel.class);

        VerseDetailsAdapter adapter = new VerseDetailsAdapter(
                getChildFragmentManager(), bibleViewModel
        );

        recyclerView.setAdapter(adapter);
        bibleViewModel.getBookVerses(book_name, chapter_num).observe(this, adapter::submitList);

    }
}