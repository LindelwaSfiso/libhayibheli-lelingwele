package org.xhanka.biblesiswati.ui.siswati_reference

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.LinearLayout
import android.widget.TextView
import androidx.navigation.NavController
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import org.xhanka.biblesiswati.R
import org.xhanka.biblesiswati.common.Utils.Companion.REF_COMPARATOR
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
        holder.onBind(getItem(position), navController, model)
    }


    class RefVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val parentContainer: LinearLayout = itemView.findViewById(R.id.parentContainer)
        private val englishName: TextView = itemView.findViewById(R.id.book_english)
        private val siswatiName: TextView = itemView.findViewById(R.id.book_siswati)

        fun onBind(book: RefBook, navController: NavController, model: BibleViewModel) {
            val (english, siswati, zulu) = book

            englishName.text = english
            siswatiName.text = siswati

            parentContainer.setOnClickListener {
                val inputMethodManager = it.context.getSystemService(Context.INPUT_METHOD_SERVICE)
                        as InputMethodManager
                if (inputMethodManager.isAcceptingText)
                    inputMethodManager.hideSoftInputFromWindow(it.windowToken, 0)

                val action = SiswatiReferenceFragmentDirections.actionSiswatiRefToChapters(
                    model.getBookName(english, siswati, zulu)
                )
                navController.navigate(action)
            }
        }
    }
}

