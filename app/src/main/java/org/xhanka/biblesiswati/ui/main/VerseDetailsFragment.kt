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

    val bibleViewModel by activityViewModels<BibleViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_verse_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView: RecyclerView = view.findViewById(R.id.recyclerView)
        val layoutManager = LinearLayoutManager(context)
        recyclerView.layoutManager = layoutManager
        recyclerView.addItemDecoration(
            DividerItemDecoration(
                view.context,
                DividerItemDecoration.VERTICAL
            )
        )
        val bookName = args.bookName
        val chapterNum = args.chapterNum

        val adapter = VerseDetailsAdapter(childFragmentManager, bibleViewModel)

        // use this variable to display staggering animation on recycler view once,
        // only when the recycler view displays data for the fist time
        var firstTime = true

        // use this variable to scroll to position if user is from favorites fragment
        var shouldScroll = (args.verseNum != -1)

        recyclerView.adapter = adapter
        bibleViewModel.getBookVerses(bookName, chapterNum)
            .observe(viewLifecycleOwner) { verses: List<Verse?> ->
                if (firstTime) {
                    TransitionManager.beginDelayedTransition(recyclerView, stagger)
                    firstTime = false
                }
                adapter.submitList(verses)
                if (shouldScroll) {
                    shouldScroll = false
                    layoutManager.scrollToPosition(args.verseNum - 1)
                }
            }
    }
}