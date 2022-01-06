package org.xhanka.biblesiswati.ui.siswati_reference;

import android.os.Bundle;
import android.view.LayoutInflater;
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
import org.xhanka.biblesiswati.databinding.FragmentSiswatiReferenceBinding;
import org.xhanka.biblesiswati.ui.main.room.BibleViewModel;

import java.util.Objects;

public class SiswatiReferenceFragment extends Fragment {

    private FragmentSiswatiReferenceBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentSiswatiReferenceBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView recyclerView = binding.recyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        BibleViewModel model = Objects.requireNonNull(new ViewModelProvider(this,
                ViewModelProvider.AndroidViewModelFactory.getInstance(Objects.requireNonNull(getActivity()).getApplication())
        ).get(BibleViewModel.class));

        recyclerView.addItemDecoration(new DividerItemDecoration(view.getContext(),
                DividerItemDecoration.VERTICAL)
        );

        RefAdapter adapter = new RefAdapter(
                Navigation.findNavController(view),
                model
        );
        recyclerView.setAdapter(adapter);

        model.getRefBooks().observe(this, adapter::submitList);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}