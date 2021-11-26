package org.xhanka.biblesiswati.ui.notes;

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
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textview.MaterialTextView;

import org.jetbrains.annotations.NotNull;
import org.xhanka.biblesiswati.databinding.FragmentCreateEditNoteBinding;
import org.xhanka.biblesiswati.ui.notes.room.Note;
import org.xhanka.biblesiswati.ui.notes.room.NotesViewModel;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

public class CreateEditNoteFragment extends Fragment {
    private NotesViewModel noteViewModel;
    private FragmentCreateEditNoteBinding binding;
    private Note note;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        setHasOptionsMenu(true);
        noteViewModel = new ViewModelProvider(
                this,
                ViewModelProvider.AndroidViewModelFactory.getInstance(Objects.requireNonNull(this.getActivity()).getApplication())
        ).get(NotesViewModel.class);

        binding = FragmentCreateEditNoteBinding.inflate(inflater, container, false);

        note = getArguments() != null ? getArguments().getParcelable("NOTE") : null;

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        NavController navController = Navigation.findNavController(view);

        TextInputEditText title = binding.noteTitle, body = binding.noteBody;
        MaterialTextView dateCreated = binding.dateCreated;

        if (note != null) {
            title.setText(note.getNoteTitle());
            body.setText(note.getNoteText());
            dateCreated.setText(note.getDataAdded());

            binding.button.setOnClickListener(l -> {
                String noteTitle = title.getText() != null ? title.getText().toString() : null;
                String noteBody = body.getText() != null ? body.getText().toString() : null;

                if (noteBody != null && noteTitle != null && !noteTitle.isEmpty() && !noteBody.isEmpty()) {
                    note.setNoteText(noteBody);
                    note.setNoteTitle(noteTitle);
                    note.setDataAdded(new SimpleDateFormat("EEE d MMM yyyy", Locale.ENGLISH)
                            .format(new Date())
                    );
                    noteViewModel.updateNote(note);
                    navController.navigateUp();
                }
            });

        } else {
            dateCreated.setText(new SimpleDateFormat("EEE d MMM yyyy", Locale.ENGLISH
            ).format(new Date()
            ));

            binding.button.setOnClickListener(l -> {
                String noteTitle = title.getText() != null ? title.getText().toString() : null;
                String noteBody = body.getText() != null ? body.getText().toString() : null;

                if (noteBody != null && noteTitle != null && !noteTitle.isEmpty() && !noteBody.isEmpty()) {
                    noteViewModel.createNote(new Note(
                            noteTitle,
                            noteBody,
                            new SimpleDateFormat("EEE d MMM yyyy",
                                    Locale.ENGLISH
                            ).format(new Date())
                    ));

                    navController.navigateUp();
                }
            });
        }
    }

    @Override
    public void onCreateOptionsMenu(@NonNull @NotNull Menu menu, @NonNull @NotNull MenuInflater inflater) {
        menu.clear();
        super.onCreateOptionsMenu(menu, inflater);
    }
}