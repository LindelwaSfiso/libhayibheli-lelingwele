package org.xhanka.biblesiswati.ui.main.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.NavController
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import org.xhanka.biblesiswati.R
import org.xhanka.biblesiswati.common.Utils.Companion.STRINGS_COMPARATOR
import org.xhanka.biblesiswati.common.setTextSizeSp
import org.xhanka.biblesiswati.ui.main.ChaptersFragmentDirections

class ChaptersAdapter(
    private var navController: NavController,
    private var book_name: String,
    var textSize: Int
) : ListAdapter<String, ChaptersAdapter.ChaptersVH>(STRINGS_COMPARATOR) {

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
            val action = ChaptersFragmentDirections.actionNavChaptersToNavVerseDetails(
                bookName = book_name,
                chapterNum = position + 1
            )
            navController.navigate(action)
        }
    }

    class ChaptersVH(itemView: View, textSize: Int) : RecyclerView.ViewHolder(itemView) {
        var textView: TextView = itemView.findViewById(R.id.textView)

        init {
            textView.setTextSizeSp(textSize.toFloat())
        }
    }
}


