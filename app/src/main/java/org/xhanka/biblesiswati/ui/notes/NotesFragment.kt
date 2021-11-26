package org.xhanka.biblesiswati.ui.notes

import android.os.Bundle
import android.view.*
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.xhanka.biblesiswati.R
import org.xhanka.biblesiswati.databinding.FragmentNotesBinding
import org.xhanka.biblesiswati.ui.notes.adapter.NotesAdapter
import org.xhanka.biblesiswati.ui.notes.room.NotesViewModel

class NotesFragment : Fragment() {
    private var fragmentNotesBinding: FragmentNotesBinding? = null
    private var notesViewModel: NotesViewModel? = null
    private var notesAdapter: NotesAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        fragmentNotesBinding = FragmentNotesBinding.inflate(inflater, container, false)

        notesViewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(
                activity!!.application
            )
        ).get(NotesViewModel::class.java)

        return fragmentNotesBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView: RecyclerView? = fragmentNotesBinding?.recyclerView
        val emptyView: ImageView? = fragmentNotesBinding?.notesImageView
        recyclerView?.layoutManager = LinearLayoutManager(context)
        recyclerView?.addItemDecoration(
            DividerItemDecoration(
                recyclerView.context,
                DividerItemDecoration.VERTICAL
            )
        )

        recyclerView?.setHasFixedSize(true)

        notesAdapter = NotesAdapter(notesViewModel, findNavController(view))
        recyclerView?.adapter = notesAdapter

        notesAdapter?.registerAdapterDataObserver(object : RecyclerView.AdapterDataObserver() {
            override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                if (itemCount > 0)
                    recyclerView?.scrollToPosition(positionStart)
            }
        })

        notesViewModel?.allNotes?.observe(viewLifecycleOwner) { notes ->
            notes.let {
                notesAdapter?.submitList(notes)
                if (it.isEmpty())
                    emptyView?.visibility = View.VISIBLE
                else
                    emptyView?.visibility = View.INVISIBLE
            }
        }

        fragmentNotesBinding?.addNote?.setOnClickListener {
            findNavController(view).navigate(R.id.action_nav_notes_to_nav_create_or_edit)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        fragmentNotesBinding = null
        notesAdapter = null
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        inflater.inflate(R.menu.menu_notes, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_clear_notes) {
            notesViewModel?.deleteAllNotes()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}