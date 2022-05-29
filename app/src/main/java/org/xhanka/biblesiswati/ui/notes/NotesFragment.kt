package org.xhanka.biblesiswati.ui.notes

import android.os.Bundle
import android.view.*
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
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
    private var _binding: FragmentNotesBinding? = null
    private val binding get() = _binding!!

    private val notesViewModel by activityViewModels<NotesViewModel>()
    private var notesAdapter: NotesAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNotesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView: RecyclerView = binding.recyclerView
        val emptyView: ImageView = binding.notesImageView
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.addItemDecoration(
            DividerItemDecoration(
                recyclerView.context,
                DividerItemDecoration.VERTICAL
            )
        )

        recyclerView.setHasFixedSize(true)

        notesAdapter = NotesAdapter(notesViewModel, findNavController(view))
        recyclerView.adapter = notesAdapter

        notesAdapter?.registerAdapterDataObserver(object : RecyclerView.AdapterDataObserver() {
            override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                if (itemCount > 0)
                    recyclerView.scrollToPosition(positionStart)
            }
        })

        notesViewModel.allNotes.observe(viewLifecycleOwner) { notes ->
            notes.let {
                notesAdapter?.submitList(notes)
                if (it.isEmpty())
                    emptyView.visibility = View.VISIBLE
                else
                    emptyView.visibility = View.INVISIBLE
            }
        }

        binding.addNote.setOnClickListener {
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

        requireActivity().addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.menu_notes, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                if (menuItem.itemId == R.id.action_clear_notes) {
                    AlertDialog.Builder(requireContext())
                        .setTitle("Delete All Notes")
                        .setMessage("Are you sure? This action is not reversible.")
                        .setPositiveButton("Delete") { _, _ ->
                            notesViewModel.deleteAllNotes()
                        }.setNegativeButton("Cancel", null).show()
                    return true
                }
                return false
            }

        }, viewLifecycleOwner, Lifecycle.State.RESUMED)

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
        notesAdapter = null
    }
}