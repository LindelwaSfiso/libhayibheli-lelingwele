package org.xhanka.biblesiswati.ui.main.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.NavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import org.xhanka.biblesiswati.R
import org.xhanka.biblesiswati.common.setTextSizeSp
import org.xhanka.biblesiswati.ui.main.BooksFragmentDirections

class BooksAdapter(private val controller: NavController, val textSize: Int) :
    ListAdapter<String, BooksAdapter.ViewHolder>(STRINGS_COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout._list_item, parent, false
            ),
            textSize
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.textView.text = getItem(position)
        holder.textView.setOnClickListener {
            val action = BooksFragmentDirections.actionNavBooksToNavChapters(getItem(position))
            controller.navigate(action)
        }
    }

    class ViewHolder(itemView: View, textSize: Int) :
        RecyclerView.ViewHolder(itemView) {
        var textView: TextView = itemView.findViewById(R.id.textView)

        init {
            textView.setTextSizeSp(textSize.toFloat())
        }
    }

    companion object {
        val STRINGS_COMPARATOR = object : DiffUtil.ItemCallback<String>() {
            override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
                return oldItem == newItem
            }

        }
    }
}