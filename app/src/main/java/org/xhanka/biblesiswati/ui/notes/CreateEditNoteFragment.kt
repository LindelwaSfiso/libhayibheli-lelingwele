package org.xhanka.biblesiswati.ui.notes

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation.findNavController
import org.xhanka.biblesiswati.databinding.FragmentCreateEditNoteBinding
import org.xhanka.biblesiswati.ui.notes.room.Note
import org.xhanka.biblesiswati.ui.notes.room.NotesViewModel
import java.text.SimpleDateFormat
import java.util.*

// TODO: THIS IS A MESS, ADD HILT SUPPORT

class CreateEditNoteFragment : Fragment() {
    private var noteViewModel: NotesViewModel? = null
    private lateinit var binding: FragmentCreateEditNoteBinding
    private var note: Note? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        setHasOptionsMenu(true)

        noteViewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(
                this.activity!!.application
            )
        ).get(NotesViewModel::class.java)

        binding = FragmentCreateEditNoteBinding.inflate(inflater, container, false)
        note = if (arguments != null) arguments!!.getParcelable("NOTE") else null

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val navController = findNavController(view)
        val title = binding.noteTitle
        val body = binding.noteBody
        val dateCreated = binding.dateCreated

        if (note != null) {
            title.setText(note!!.noteTitle)
            body.setText(note!!.noteText)
            dateCreated.text = note!!.dataAdded
            binding.button.setOnClickListener {

                val noteTitle = title.text.toString()
                // val noteTitle = if (title.text != null) title.text.toString() else null
                val noteBody = if (body.text != null) body.text.toString() else null
                if (noteBody != null && !noteTitle.isEmpty() && !noteBody.isEmpty()) {
                    note!!.noteText = noteBody
                    note!!.noteTitle = noteTitle
                    note!!.dataAdded = SimpleDateFormat("EEE d MMM yyyy", Locale.ENGLISH)
                        .format(Date())
                    noteViewModel!!.updateNote(note)
                    navController.navigateUp()
                }
            }
        } else {
            dateCreated.text = SimpleDateFormat(
                "EEE d MMM yyyy", Locale.ENGLISH
            ).format(
                Date()
            )
            binding.button.setOnClickListener { l: View? ->
                val noteTitle = if (title.text != null) title.text.toString() else null
                val noteBody = if (body.text != null) body.text.toString() else null
                if (noteBody != null && noteTitle != null && !noteTitle.isEmpty() && !noteBody.isEmpty()) {
                    noteViewModel!!.createNote(
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

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        super.onCreateOptionsMenu(menu, inflater)
    }
}