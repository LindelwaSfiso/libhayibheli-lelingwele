package org.xhanka.biblesiswati.ui.notes.adapter

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.navigation.NavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import org.xhanka.biblesiswati.R
import org.xhanka.biblesiswati.ui.notes.room.Note
import org.xhanka.biblesiswati.ui.notes.room.NotesViewModel

class NotesAdapter(
    private val model: NotesViewModel?,
    private val navController: NavController
) : ListAdapter<Note, NotesAdapter.ViewHolder>(NOTES_COMPARATOR) {
    val bundle = Bundle()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val note = getItem(position)

        holder.bind(note)
        holder.parentContainer.setOnClickListener {
            bundle.putParcelable("NOTE", getItem(position))
            navController.navigate(R.id.action_nav_notes_to_nav_create_or_edit, bundle)
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

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val noteTitle = itemView.findViewById<TextView?>(R.id.noteTitle)
        private val noteBody = itemView.findViewById<TextView?>(R.id.noteBody)
        private val noteDate = itemView.findViewById<TextView?>(R.id.noteDate)
        val parentContainer: LinearLayout = itemView.findViewById(R.id.parentContainer)

        fun bind(note: Note) {
            noteTitle.text = note.noteTitle
            noteBody.text = note.noteText
            noteDate.text = note.dataAdded
        }

        companion object {
            fun create(parent: ViewGroup): ViewHolder {
                val view: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout._note_list_item, parent, false)
                return ViewHolder(view)
            }
        }
    }

    companion object {
        private val NOTES_COMPARATOR = object : DiffUtil.ItemCallback<Note>() {
            override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean {
                return oldItem == newItem
            }
        }
    }
}