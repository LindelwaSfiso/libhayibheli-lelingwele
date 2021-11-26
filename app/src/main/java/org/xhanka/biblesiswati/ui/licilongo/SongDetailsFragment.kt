package org.xhanka.biblesiswati.ui.licilongo

import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.text.HtmlCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import org.xhanka.biblesiswati.R
import org.xhanka.biblesiswati.ui.licilongo.room.SongViewModel

/**
 * Fragment for displaying licilongo song as a long TextView
 * @author Dlamini Lindelwa Sifiso [20-Nov-21]
 */
class SongDetailsFragment : Fragment() {
    var model: SongViewModel? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        model = ViewModelProvider(
            this, ViewModelProvider.AndroidViewModelFactory.getInstance(
                activity!!.application
            )
        ).get(SongViewModel::class.java)
        return inflater.inflate(R.layout.fragment_song_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val fullSong = view.findViewById<TextView>(R.id.fullSong)
        fullSong.movementMethod = ScrollingMovementMethod()

        val song = arguments?.getString("song")

        val spanned = song?.let {
            HtmlCompat.fromHtml(
                it,
                HtmlCompat.FROM_HTML_SEPARATOR_LINE_BREAK_DIV
            )
        }
        fullSong.text = spanned
    }
}