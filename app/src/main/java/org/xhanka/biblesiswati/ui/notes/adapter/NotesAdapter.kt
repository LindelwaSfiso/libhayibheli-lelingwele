package org.xhanka.biblesiswati.ui.notes.adapter

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.NavController
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import org.xhanka.biblesiswati.R
import org.xhanka.biblesiswati.common.Utils.Companion.NOTES_COMPARATOR
import org.xhanka.biblesiswati.ui.notes.NotesFragmentDirections
import org.xhanka.biblesiswati.ui.notes.room.Note
import org.xhanka.biblesiswati.ui.notes.room.NotesViewModel

class NotesAdapter(
    private val model: NotesViewModel?,
    private val navController: NavController
) : ListAdapter<Note, NotesAdapter.NotesVH>(NOTES_COMPARATOR) {
    val bundle = Bundle()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesVH {
        return NotesVH(
            LayoutInflater.from(parent.context).inflate(
                R.layout._note_list_item, parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: NotesVH, position: Int) {
        val note = getItem(position)

        holder.bind(note)
        holder.parentContainer.setOnClickListener {
            val action = NotesFragmentDirections.actionNavNotesToNavCreateOrEdit(getItem(position))
            navController.navigate(action)
        }

        holder.parentContainer.setOnLongClickListener { l: View? ->
            AlertDialog.Builder(l?.context)
                .setTitle("Delete")
                .setMessage("Are you sure you want to delete this note?")
                .setNegativeButton("Cancel", null)
                .setPositiveButton("Delete") { _, _ ->
                    model?.deleteNote(note)
                }.create().show()

            true
        }
    }

    class NotesVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val noteTitle = itemView.findViewById<TextView?>(R.id.noteTitle)
        private val noteBody = itemView.findViewById<TextView?>(R.id.noteBody)
        private val noteDate = itemView.findViewById<TextView?>(R.id.noteDate)
        val parentContainer: View = itemView.findViewById(R.id.parentContainer)

        fun bind(note: Note) {
            noteTitle.text = note.noteTitle
            noteBody.text = note.noteText
            noteDate.text = note.dataAdded
        }
    }
}