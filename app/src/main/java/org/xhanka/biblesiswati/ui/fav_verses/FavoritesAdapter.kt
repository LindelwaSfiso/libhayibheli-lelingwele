package org.xhanka.biblesiswati.ui.fav_verses

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.navigation.NavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import org.xhanka.biblesiswati.R
import org.xhanka.biblesiswati.common.BibleVersion.NIV
import org.xhanka.biblesiswati.ui.main.models.Verse
import org.xhanka.biblesiswati.ui.main.room.BibleViewModel

class FavoritesAdapter(
    private val model: BibleViewModel,
    private val navController: NavController
) : ListAdapter<Verse, FavoritesAdapter.FavoritesVH>(VERSE_COMPARATOR) {
    var bundle = Bundle()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoritesVH {
        return FavoritesVH(
            LayoutInflater.from(parent.context).inflate(
                R.layout._fav_verses_list_item, parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: FavoritesVH, position: Int) {
        val verse = getItem(position)

        val title: String
        val verseText: String

        if (model.getVersion() == NIV) {
            title = verse.nivBook
            verseText = verse.nivVerse
        } else {
            title = verse.siswatiBook
            verseText = verse.siswatiVerse
        }

        holder.title.text = verse.getTitle(title)
        holder.dateAdded.text = verse.getFormattedDate()
        holder.verseText.text = verseText

        holder.parentContainer.setOnClickListener {
            bundle.putString("book_name", title)
            bundle.putInt("chapter_num", verse.chapter)
            navController.navigate(R.id.nav_verse_details, bundle)
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
        var parentContainer: LinearLayout = itemView.findViewById(R.id.parentContainer)
        var title: TextView = itemView.findViewById(R.id.bookName)
        var dateAdded: TextView = itemView.findViewById(R.id.dateAdded)
        var verseText: TextView = itemView.findViewById(R.id.verseText)
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