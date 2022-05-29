package org.xhanka.biblesiswati.ui.licilongo

import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.transition.TransitionManager
import dagger.hilt.android.AndroidEntryPoint
import org.xhanka.biblesiswati.R
import org.xhanka.biblesiswati.common.Stagger
import org.xhanka.biblesiswati.ui.licilongo.adapter.LicilongoAdapter
import org.xhanka.biblesiswati.ui.licilongo.room.Song
import org.xhanka.biblesiswati.ui.licilongo.room.SongViewModel

@AndroidEntryPoint
class ListSongsFragment : Fragment() {
    private val songViewModel by viewModels<SongViewModel>()
    private var searchMenu: MenuItem? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_list_songs, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView: RecyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.addItemDecoration(
            DividerItemDecoration(
                view.context, DividerItemDecoration.VERTICAL
            )
        )
        val adapter = LicilongoAdapter(findNavController())
        recyclerView.adapter = adapter

        val stagger = Stagger()
        songViewModel.songs.observe(viewLifecycleOwner) { songs: List<Song> ->
            TransitionManager.beginDelayedTransition(recyclerView, stagger)
            adapter.submitList(songs)
        }

        requireActivity().addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.menu_search, menu)
                searchMenu = menu.findItem(R.id.action_search_song)
                val searchView = menu.findItem(R.id.action_search_song).actionView as SearchView
                searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                    override fun onQueryTextSubmit(query: String?): Boolean {
                        query?.let {
                            recyclerView.scrollToPosition(0)
                            songViewModel.searchForSong("%$it%")
                        } ?: run {
                            recyclerView.scrollToPosition(0)
                            songViewModel.getAllSongs()
                        }
                        searchView.clearFocus()
                        return true
                    }

                    override fun onQueryTextChange(newText: String?): Boolean {
                        newText?.let {
                            recyclerView.scrollToPosition(0)
                            songViewModel.searchForSong("%$it%")
                        } ?: run {
                            recyclerView.scrollToPosition(0)
                            songViewModel.getAllSongs()
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
}