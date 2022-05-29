package org.xhanka.biblesiswati.ui.fav_verses

import android.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.NavController
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import org.xhanka.biblesiswati.R
import org.xhanka.biblesiswati.common.Utils.Companion.VERSE_COMPARATOR
import org.xhanka.biblesiswati.common.getBookAndVerse
import org.xhanka.biblesiswati.ui.main.models.Verse
import org.xhanka.biblesiswati.ui.main.room.BibleViewModel

class FavoritesAdapter(
    private val model: BibleViewModel,
    private val navController: NavController
) : ListAdapter<Verse, FavoritesAdapter.FavoritesVH>(VERSE_COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoritesVH {
        return FavoritesVH(
            LayoutInflater.from(parent.context).inflate(
                R.layout._fav_verses_list_item, parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: FavoritesVH, position: Int) {
        val verse = getItem(position)

        var title = ""
        var verseText = ""

        verse.getBookAndVerse(model.getVersion()) { book, verseT ->
            title = book
            verseText = verseT
        }

        holder.title.text = verse.getTitle(title)
        holder.dateAdded.text = verse.getFormattedDate()
        holder.verseText.text = verseText

        holder.parentContainer.setOnClickListener {
            val action = FavoriteVersesFragmentDirections.actionNavFavVersesToNavVerseDetails(
                title,
                chapterNum = verse.chapter,
                verseNum = verse.verseNum
            )
            navController.navigate(action)
        }

        holder.parentContainer.setOnLongClickListener { l: View? ->
            AlertDialog.Builder(l?.context)
                .setTitle("Remove")
                .setMessage("Are you sure you want to remove this verse from favorites?")
                .setNegativeButton("Cancel", null)
                .setPositiveButton("Remove") { _, _ ->
                    model.removeFromFavorites(verseId = verse.id)
                    submitList(model.getAllFavorites().value)
                }.create().show()
            true
        }
    }

    class FavoritesVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var parentContainer: View = itemView.findViewById(R.id.parentContainer)
        var title: TextView = itemView.findViewById(R.id.bookName)
        var dateAdded: TextView = itemView.findViewById(R.id.dateAdded)
        var verseText: TextView = itemView.findViewById(R.id.verseText)
    }
}