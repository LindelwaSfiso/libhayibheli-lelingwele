package org.xhanka.biblesiswati.ui.main.adapter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.NavController
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import org.xhanka.biblesiswati.R
import org.xhanka.biblesiswati.common.setTextSizeSp
import org.xhanka.biblesiswati.ui.main.adapter.BooksAdapter.Companion.STRINGS_COMPARATOR

class ChaptersAdapter(
    var navController: NavController,
    var book_name: String?,
    var textSize: Int
) : ListAdapter<String, ChaptersAdapter.ChaptersVH>(STRINGS_COMPARATOR) {

    val bundle = Bundle()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChaptersVH {
        return ChaptersVH(
            LayoutInflater.from(parent.context).inflate(
                R.layout._list_item, parent, false
            ),
            textSize
        )
    }

    override fun onBindViewHolder(holder: ChaptersVH, position: Int) {
        holder.textView.text = getItem(position)
        holder.textView.setOnClickListener {
            bundle.putString("book_name", book_name)
            bundle.putInt("chapter_num", position + 1)
            navController.navigate(R.id.action_nav_chapters_to_nav_verse_details, bundle)
        }
    }

    class ChaptersVH(itemView: View, textSize: Int) : RecyclerView.ViewHolder(itemView) {
        var textView: TextView = itemView.findViewById(R.id.textView)

        init {
            textView.setTextSizeSp(textSize.toFloat())
        }
    }
}


