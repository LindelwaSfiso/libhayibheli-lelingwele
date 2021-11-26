package org.xhanka.biblesiswati.ui.main.adapter

import android.annotation.SuppressLint
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import org.xhanka.biblesiswati.R
import org.xhanka.biblesiswati.common.BibleVersion.NIV
import org.xhanka.biblesiswati.common.VerseDetailsBottomFragment
import org.xhanka.biblesiswati.ui.main.models.Verse
import org.xhanka.biblesiswati.ui.main.room.BibleViewModel
import java.util.*

class VerseDetailsAdapter(
    var fragmentManager: FragmentManager,
    model: BibleViewModel
) : ListAdapter<Verse, VerseDetailsAdapter.VerseDetailsVH>(VERSE_COMPARATOR) {

    val textSize = model.textSize.value!!
    private val bibleVersion = model.getVersion()

    init {
        setHasStableIds(true)
    }

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

        holder.verseNum.text = String.format(Locale.ENGLISH, "%d", position + 1)

        if (bibleVersion == NIV)
            holder.verseText.text = verse.nivVerse
        else
            holder.verseText.text = verse.siswatiVerse

        if (verse.isAddedToFavorites())
            holder.verseText.setTextColor2(R.color.colorFavoriteVerse)

        holder.parentContainer.setOnClickListener {
            val details = VerseDetailsBottomFragment.getInstance(verse.id)
            details.setOnClick { isClicked: Boolean ->
                verse.setFav(isClicked)
                if (isClicked) holder.verseText.setTextColor2(R.color.colorFavoriteVerse)
                else holder.verseText.setTextColor2(R.color.defaultTextColor)
            }
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

    companion object {
        val VERSE_COMPARATOR = object : DiffUtil.ItemCallback<Verse>() {
            override fun areItemsTheSame(oldItem: Verse, newItem: Verse): Boolean {
                return oldItem.id == newItem.id
            }

            @SuppressLint("DiffUtilEquals")
            override fun areContentsTheSame(oldItem: Verse, newItem: Verse): Boolean {
                return oldItem == newItem
            }

        }
    }
}


// moving this code to Extensions.kt raises errors :: Investigate
// 24-Nov-21
// todo: add relevant annotationProcessor via gradle file
private fun TextView.setTextColor2(@ColorRes color: Int) {
    setTextColor(ResourcesCompat.getColor(this.context.resources, color, null))
}


private fun TextView.setTextSizeSp(float: Float) {
    setTextSize(TypedValue.COMPLEX_UNIT_SP, float)
}