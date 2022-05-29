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
import org.xhanka.biblesiswati.ui.main.adapter.ChaptersAdapter
import org.xhanka.biblesiswati.ui.main.room.BibleViewModel

@AndroidEntryPoint
class ChaptersFragment : Fragment() {

    private val args by navArgs<ChaptersFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_chapters, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView: RecyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.addItemDecoration(
            DividerItemDecoration(
                view.context,
                DividerItemDecoration.VERTICAL
            )
        )
        val bookName = args.bookName

        val booksViewModel by activityViewModels<BibleViewModel>()

        val adapter = ChaptersAdapter(
            findNavController(view),
            bookName,
            booksViewModel.getTextSizeValue()
        )

        recyclerView.adapter = adapter

        val stagger = Stagger()
        booksViewModel.getChapterCount(bookName).observe(viewLifecycleOwner) {
            val chapters = ArrayList<String>()
            for (i in 0 until it) chapters.add("Chapter " + (i + 1))
            TransitionManager.beginDelayedTransition(recyclerView, stagger)
            adapter.submitList(chapters)
        }
    }
}