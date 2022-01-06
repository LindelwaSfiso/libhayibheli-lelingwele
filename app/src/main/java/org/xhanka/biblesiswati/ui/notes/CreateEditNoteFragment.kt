package org.xhanka.biblesiswati.ui.notes

import android.os.Bundle
import android.view.*
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.textfield.TextInputEditText
import dagger.hilt.android.AndroidEntryPoint
import org.xhanka.biblesiswati.databinding.FragmentCreateEditNoteBinding
import org.xhanka.biblesiswati.ui.notes.room.Note
import org.xhanka.biblesiswati.ui.notes.room.NotesViewModel
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class CreateEditNoteFragment : Fragment() {
    private val notesViewModel by activityViewModels<NotesViewModel>()
    private lateinit var binding: FragmentCreateEditNoteBinding
    private var note: Note? = null
    private val args by navArgs<CreateEditNoteFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        setHasOptionsMenu(true)

        /*noteViewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(
                this.activity!!.application
            )
        ).get(NotesViewModel::class.java)*/

        binding = FragmentCreateEditNoteBinding.inflate(inflater, container, false)
        note = args.note

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val navController = findNavController(view)
        val title = binding.noteTitle
        val body = binding.noteBody
        val dateCreated = binding.dateCreated

        args.note?.let {
            editNote(title, body, dateCreated, it, navController)
        } ?: run {
            createNewNote(title, body, dateCreated, navController)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        super.onCreateOptionsMenu(menu, inflater)
    }

    private fun editNote(
        title: TextInputEditText, body: TextInputEditText, dateCreated: TextView, note: Note,
        navController: NavController
    ) {
        title.setText(note.noteTitle)
        body.setText(note.noteText)
        dateCreated.text = note.dataAdded

        binding.button.setOnClickListener {

            val noteTitle = title.text.toString()
            val noteBody = body.text.toString()

            if (noteTitle.isNotEmpty() && noteBody.isNotEmpty()) {
                note.noteText = noteBody
                note.noteTitle = noteTitle
                note.dataAdded = SimpleDateFormat("EEE d MMM yyyy", Locale.ENGLISH)
                    .format(Date())
                notesViewModel.updateNote(note)
                navController.navigateUp()
            }
        }
    }

    private fun createNewNote(
        title: TextInputEditText, body: TextInputEditText, dateCreated: TextView,
        navController: NavController
    ) {
        dateCreated.text = SimpleDateFormat("EEE d MMM yyyy", Locale.ENGLISH).format(Date())

        binding.button.setOnClickListener {
            val noteTitle = title.text?.toString() ?: ""
            val noteBody = body.text?.toString() ?: ""

            if (noteTitle.isNotEmpty() && noteBody.isNotEmpty()) {
                notesViewModel.createNote(
                    Note(
                        noteTitle,
                        noteBody,
                        SimpleDateFormat(
                            "EEE d MMM yyyy",
                            Locale.ENGLISH
                        ).format(Date())
                    )
                )
                navController.navigateUp()
            }
        }
    }
}