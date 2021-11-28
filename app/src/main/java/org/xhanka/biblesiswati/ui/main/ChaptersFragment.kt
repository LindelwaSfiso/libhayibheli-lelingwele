package org.xhanka.biblesiswati.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.transition.TransitionManager
import org.xhanka.biblesiswati.R
import org.xhanka.biblesiswati.common.Stagger
import org.xhanka.biblesiswati.ui.main.adapter.ChaptersAdapter
import org.xhanka.biblesiswati.ui.main.room.BibleViewModel
import java.util.*

class ChaptersFragment : Fragment() {

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
        val bookName = if (arguments != null)
            arguments!!.getString("book_name", "Genesis")
        else "Genesis"

        val booksViewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(activity!!.application)
        ).get(BibleViewModel::class.java)

        val adapter =
            ChaptersAdapter(findNavController(view), bookName, booksViewModel.getTextSizeValue())
        recyclerView.adapter = adapter

        val stagger = Stagger()

        booksViewModel.getChapterCount(bookName).observe(this, {
            val chapters = ArrayList<String>()
            for (i in 0 until it) chapters.add("Chapter " + (i + 1))
            TransitionManager.beginDelayedTransition(recyclerView, stagger)
            adapter.submitList(chapters)
        })
    }
}