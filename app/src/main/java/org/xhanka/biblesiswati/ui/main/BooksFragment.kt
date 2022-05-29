package org.xhanka.biblesiswati.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.transition.TransitionManager
import dagger.hilt.android.AndroidEntryPoint
import org.xhanka.biblesiswati.R
import org.xhanka.biblesiswati.common.Stagger
import org.xhanka.biblesiswati.ui.main.adapter.BooksAdapter
import org.xhanka.biblesiswati.ui.main.room.BibleViewModel

@AndroidEntryPoint
class BooksFragment : Fragment() {

    private val bibleViewModel by activityViewModels<BibleViewModel>()
    private val args by navArgs<BooksFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_books, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mode = args.testament

        val recyclerView: RecyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.addItemDecoration(
            DividerItemDecoration(
                view.context,
                DividerItemDecoration.VERTICAL
            )
        )

        val adapter = BooksAdapter(
            findNavController(view),
            bibleViewModel.getTextSizeValue()
        )

//        recyclerView.itemAnimator = object : DefaultItemAnimator() {
//            override fun animateAdd(holder: RecyclerView.NotesVH): Boolean {
//                dispatchAddFinished(holder)
//                dispatchAddStarting(holder)
//                return false
//            }
//        }

        val stagger = Stagger()
        bibleViewModel.getBooksByMode(mode).observe(viewLifecycleOwner) { strings: List<String> ->
            TransitionManager.beginDelayedTransition(recyclerView, stagger)
            adapter.submitList(strings)
        }

        recyclerView.adapter = adapter
    }
}