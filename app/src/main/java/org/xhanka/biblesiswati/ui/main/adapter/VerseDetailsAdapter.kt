package org.xhanka.biblesiswati.ui.main.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import org.xhanka.biblesiswati.R
import org.xhanka.biblesiswati.common.Utils.Companion.VERSE_COMPARATOR
import org.xhanka.biblesiswati.common.VerseDetailsBottomFragment
import org.xhanka.biblesiswati.common.getVerse
import org.xhanka.biblesiswati.common.setFavVerseTextColor
import org.xhanka.biblesiswati.common.setTextSizeSp
import org.xhanka.biblesiswati.ui.main.models.Verse
import org.xhanka.biblesiswati.ui.main.room.BibleViewModel

class VerseDetailsAdapter(
    private var fragmentManager: FragmentManager,
    model: BibleViewModel
) : ListAdapter<Verse, VerseDetailsAdapter.VerseDetailsVH>(VERSE_COMPARATOR) {

    val textSize = model.getTextSizeValue()
    private val bibleVersion = model.getVersion()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VerseDetailsVH {
        return VerseDetailsVH(
            LayoutInflater.from(parent.context).inflate(
                R.layout._verse_details_list_item, parent, false
            ),
            textSize
        )
    }

    override fun onBindViewHolder(holder: VerseDetailsVH, position: Int) {
        val verse = getItem(position)

        val verseText = verse.getVerse(bibleVersion)
        holder.verseNum.text = String.format("${position + 1}")
        holder.verseText.text = verseText

        if (verse.isAddedToFavorites()) {
            holder.verseNum.setFavVerseTextColor(R.color.colorFavoriteVerse)
            holder.verseText.setFavVerseTextColor(R.color.colorFavoriteVerse)
        }

        holder.parentContainer.setOnClickListener {
            val details = VerseDetailsBottomFragment.getInstance(verse, bibleVersion)
            details.show(fragmentManager, "DETAIL")
        }
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    class VerseDetailsVH(itemView: View, textSize: Int) : RecyclerView.ViewHolder(itemView) {
        var verseText: TextView = itemView.findViewById(R.id.verseText)
        var verseNum: TextView = itemView.findViewById(R.id.verseNum)
        var parentContainer: LinearLayout = itemView.findViewById(R.id.parentContainer)

        init {
            verseText.setTextSizeSp(textSize.toFloat())
        }
    }
}