package org.xhanka.biblesiswati.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.transition.TransitionManager
import dagger.hilt.android.AndroidEntryPoint
import org.xhanka.biblesiswati.R
import org.xhanka.biblesiswati.common.Stagger
import org.xhanka.biblesiswati.ui.main.adapter.VerseDetailsAdapter
import org.xhanka.biblesiswati.ui.main.models.Verse
import org.xhanka.biblesiswati.ui.main.room.BibleViewModel

@AndroidEntryPoint
class VerseDetailsFragment : Fragment() {
    private val stagger = Stagger()
    private val args by navArgs<VerseDetailsFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_verse_details, container, false)
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
        val chapterNum = args.chapterNum

        val bibleViewModel by activityViewModels<BibleViewModel>()
        val adapter = VerseDetailsAdapter(childFragmentManager, bibleViewModel)

        recyclerView.adapter = adapter
        bibleViewModel.getBookVerses(bookName!!, chapterNum)
            .observe(this, { verses: List<Verse?> ->
                TransitionManager.beginDelayedTransition(recyclerView, stagger)
                adapter.submitList(verses)
            })
    }
}