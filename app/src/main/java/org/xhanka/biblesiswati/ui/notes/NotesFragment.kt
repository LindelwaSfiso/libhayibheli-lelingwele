package org.xhanka.biblesiswati.ui.notes

import android.os.Bundle
import android.view.*
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import org.xhanka.biblesiswati.R
import org.xhanka.biblesiswati.databinding.FragmentNotesBinding
import org.xhanka.biblesiswati.ui.notes.adapter.NotesAdapter
import org.xhanka.biblesiswati.ui.notes.room.NotesViewModel

@AndroidEntryPoint
class NotesFragment : Fragment() {
    private var fragmentNotesBinding: FragmentNotesBinding? = null
    private val notesViewModel by activityViewModels<NotesViewModel>()
    private var notesAdapter: NotesAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        fragmentNotesBinding = FragmentNotesBinding.inflate(inflater, container, false)

/*        notesViewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(
                activity!!.application
            )
        ).get(NotesViewModel::class.java)*/

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

        notesViewModel.allNotes.observe(viewLifecycleOwner) { notes ->
            notes.let {
                notesAdapter?.submitList(notes)
                if (it.isEmpty())
                    emptyView?.visibility = View.VISIBLE
                else
                    emptyView?.visibility = View.INVISIBLE
            }
        }

        fragmentNotesBinding?.addNote?.setOnClickListener {
            val action = NotesFragmentDirections.actionNavNotesToNavCreateOrEdit(null)
            findNavController(view).navigate(action)
        }

        ItemTouchHelper(object : ItemTouchHelper.Callback() {
            override fun getMovementFlags(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder
            ): Int {
                return makeMovementFlags(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT)
            }

            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean = false

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                // notesViewModel?.deleteNote(viewHolder.)
            }
        }).attachToRecyclerView(recyclerView)

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
            notesViewModel.deleteAllNotes()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}