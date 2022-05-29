package org.xhanka.biblesiswati.ui.siswati_reference

import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import org.xhanka.biblesiswati.R
import org.xhanka.biblesiswati.databinding.FragmentSiswatiReferenceBinding
import org.xhanka.biblesiswati.ui.main.room.BibleViewModel

class SiswatiReferenceFragment : Fragment() {
    private var _binding: FragmentSiswatiReferenceBinding? = null
    private val binding get() = _binding!!

    private val bibleViewModel by activityViewModels<BibleViewModel>()
    private var searchMenu: MenuItem? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSiswatiReferenceBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.addItemDecoration(
            DividerItemDecoration(
                view.context,
                DividerItemDecoration.VERTICAL
            )
        )

        val adapter = RefAdapter(findNavController(), bibleViewModel)
        recyclerView.adapter = adapter
        bibleViewModel.books.observe(viewLifecycleOwner) { list: List<RefBook?> ->
            adapter.submitList(list)
        }

        requireActivity().addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.menu_search, menu)
                val searchView = menu.findItem(R.id.action_search_song).actionView as SearchView
                searchMenu = menu.findItem(R.id.action_search_song)
                searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                    override fun onQueryTextSubmit(query: String?): Boolean {
                        query?.let {
                            recyclerView.scrollToPosition(0)
                            bibleViewModel.searchRefBooks("%$it%")
                        } ?: run {
                            recyclerView.scrollToPosition(0)
                            bibleViewModel.getRefBooks()
                        }
                        searchView.clearFocus()
                        return true
                    }

                    override fun onQueryTextChange(newText: String?): Boolean {
                        newText?.let {
                            recyclerView.scrollToPosition(0)
                            bibleViewModel.searchRefBooks("%$it%")
                        } ?: run {
                            recyclerView.scrollToPosition(0)
                            bibleViewModel.getRefBooks()
                        }
                        return true
                    }
                })
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return false
            }

        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    override fun onPause() {
        super.onPause()
        searchMenu?.collapseActionView()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}