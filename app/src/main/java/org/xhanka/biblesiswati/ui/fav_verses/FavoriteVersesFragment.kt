package org.xhanka.biblesiswati.ui.fav_verses

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import org.xhanka.biblesiswati.R
import org.xhanka.biblesiswati.databinding.FragmentFavoriteVersesBinding
import org.xhanka.biblesiswati.ui.main.models.Verse
import org.xhanka.biblesiswati.ui.main.room.BibleViewModel

@AndroidEntryPoint
class FavoriteVersesFragment : Fragment() {
    private val bibleViewModel by activityViewModels<BibleViewModel>()
    private lateinit var binding: FragmentFavoriteVersesBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentFavoriteVersesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)

        val recyclerView: RecyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.addItemDecoration(
            DividerItemDecoration(
                view.context,
                DividerItemDecoration.VERTICAL
            )
        )

        /*bibleViewModel = Objects.requireNonNull(
            ViewModelProvider(
                this,
                ViewModelProvider.AndroidViewModelFactory.getInstance(
                    Objects.requireNonNull(activity).application
                )
            ).get(BibleViewModel::class.java)
        )*/

        val nav = findNavController(view)
        val adapter = FavoritesAdapter(
            bibleViewModel, nav
        )

        recyclerView.adapter = adapter

        bibleViewModel.getAllFavorites().observe(this, { verses: List<Verse?> ->
            adapter.submitList(verses)
            if (verses.isEmpty())
                binding.emptyView.visibility = View.VISIBLE
            else
                binding.emptyView.visibility = View.INVISIBLE
        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        inflater.inflate(R.menu.menu_favorites, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_clear_favorites) {
            bibleViewModel.clearAllFavorites()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}