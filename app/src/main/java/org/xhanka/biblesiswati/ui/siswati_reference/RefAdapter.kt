package org.xhanka.biblesiswati.ui.siswati_reference

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
import org.xhanka.biblesiswati.ui.main.room.BibleViewModel

class RefAdapter(
    private val navController: NavController,
    private val model: BibleViewModel,
) : ListAdapter<RefBook, RefAdapter.RefVH>(REF_COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RefVH {
        return RefVH(
            LayoutInflater.from(parent.context).inflate(
                R.layout._siswati_ref_list_item, parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: RefVH, position: Int) {
        val book = getItem(position)

        val english = book.NIV_BOOK
        val siswati = book.SISWATI_BOOK

        holder.englishName.text = english
        holder.siswatiName.text = siswati

//        if((position + 1) % 2 == 0)
//            holder.parentContainer.setBackgroundColor(ContextCompat.getColor(
//                holder.getContext(), R.color.backgroundTextColor)
//            )

        holder.parentContainer.setOnClickListener {
            val action = SiswatiReferenceFragmentDirections.actionSiswatiRefToChapters(
                model.getBookName(english, siswati)
            )
            navController.navigate(action)
        }
    }


    class RefVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val parentContainer: LinearLayout = itemView.findViewById(R.id.parentContainer)
        val englishName: TextView = itemView.findViewById(R.id.book_english)
        val siswatiName: TextView = itemView.findViewById(R.id.book_siswati)
    }

    companion object {
        val REF_COMPARATOR = object : DiffUtil.ItemCallback<RefBook>() {
            override fun areItemsTheSame(oldItem: RefBook, newItem: RefBook): Boolean {
                return oldItem.NIV_BOOK == newItem.NIV_BOOK
            }

            override fun areContentsTheSame(oldItem: RefBook, newItem: RefBook): Boolean {
                return oldItem.NIV_BOOK == newItem.NIV_BOOK
            }
        }
    }
}

