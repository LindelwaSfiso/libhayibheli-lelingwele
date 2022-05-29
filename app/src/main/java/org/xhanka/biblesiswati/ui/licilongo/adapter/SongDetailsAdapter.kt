package org.xhanka.biblesiswati.ui.licilongo.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.text.HtmlCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import org.xhanka.biblesiswati.R

class SongDetailsAdapter : ListAdapter<String, SongDetailsAdapter.SongDetailsVH>(STRING_COMP) {
    companion object {
        val STRING_COMP = object : DiffUtil.ItemCallback<String>() {
            override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
                return oldItem == newItem
            }

        }
    }

    class SongDetailsVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val songLineTextView: TextView = itemView.findViewById(R.id.textView)

        fun onBind(songLine: String) {
            songLineTextView.text = HtmlCompat.fromHtml(
                songLine,
                HtmlCompat.FROM_HTML_SEPARATOR_LINE_BREAK_DIV
            )
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongDetailsVH {
        return SongDetailsVH(
            LayoutInflater.from(parent.context).inflate(
                R.layout._song_details_line, parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: SongDetailsVH, position: Int) {
        holder.onBind(getItem(position))
    }
}