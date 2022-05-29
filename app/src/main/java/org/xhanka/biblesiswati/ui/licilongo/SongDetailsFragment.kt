package org.xhanka.biblesiswati.ui.licilongo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.xhanka.biblesiswati.R
import org.xhanka.biblesiswati.ui.licilongo.adapter.SongDetailsAdapter

/**
 * Fragment for displaying licilongo song as a recyclerView, lines are split into a list
 * @author Dlamini Lindelwa Sifiso [20-Nov-21, modified 18-May-22]
 */
class SongDetailsFragment : Fragment() {
    private val args by navArgs<SongDetailsFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_song_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val song = args.song
        // val spanned = HtmlCompat.fromHtml(song, HtmlCompat.FROM_HTML_SEPARATOR_LINE_BREAK_DIV)

        val songLines = song.split("\n\n")
        val adapter = SongDetailsAdapter()
        adapter.submitList(songLines)

        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(view.context)
        recyclerView.addItemDecoration(
            DividerItemDecoration(
                view.context, DividerItemDecoration.VERTICAL
            )
        )
        recyclerView.adapter = adapter
    }
}