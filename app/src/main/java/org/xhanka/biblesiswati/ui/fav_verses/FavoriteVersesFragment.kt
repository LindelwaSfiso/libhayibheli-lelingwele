package org.xhanka.biblesiswati.ui.fav_verses

import android.os.Bundle
import android.view.*
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
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
    private var _binding: FragmentFavoriteVersesBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentFavoriteVersesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView: RecyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.addItemDecoration(
            DividerItemDecoration(
                view.context,
                DividerItemDecoration.VERTICAL
            )
        )

        val nav = findNavController(view)
        val adapter = FavoritesAdapter(bibleViewModel, nav)
        recyclerView.adapter = adapter

        bibleViewModel.getAllFavorites().observe(viewLifecycleOwner) { verses: List<Verse?> ->
            adapter.submitList(verses)
            if (verses.isEmpty())
                binding.emptyView.visibility = View.VISIBLE
            else
                binding.emptyView.visibility = View.INVISIBLE
        }

        requireActivity().addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.menu_favorites, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                if (menuItem.itemId == R.id.action_clear_favorites) {
                    bibleViewModel.clearAllFavorites()
                    return true
                }
                return false
            }

        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}