package org.xhanka.biblesiswati.ui.licilongo

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.transition.TransitionManager
import dagger.hilt.android.AndroidEntryPoint
import org.xhanka.biblesiswati.R
import org.xhanka.biblesiswati.common.Stagger
import org.xhanka.biblesiswati.ui.licilongo.room.Song
import org.xhanka.biblesiswati.ui.licilongo.room.SongViewModel

@AndroidEntryPoint
class ListSongsFragment : Fragment() {
    private val songViewModel by viewModels<SongViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        //setHasOptionsMenu(true);

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
        val adapter = LicilongoAdapter(findNavController(view))
        recyclerView.adapter = adapter

        val stagger = Stagger()
        songViewModel.songs.observe(this, { songs: List<Song?> ->
            TransitionManager.beginDelayedTransition(recyclerView, stagger)
            adapter.submitList(songs)
        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        // todo: implement searching
        // menu.clear();
        // inflater.inflate(R.menu.menu_licilongo, menu);
        super.onCreateOptionsMenu(menu, inflater)
    }
}