package org.xhanka.biblesiswati.ui.licilongo

import android.annotation.SuppressLint
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
import org.xhanka.biblesiswati.ui.licilongo.room.Song

class LicilongoAdapter(private val navController: NavController) :
    ListAdapter<Song, LicilongoAdapter.LicilongoVH>(SONG_COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LicilongoVH {
        return LicilongoVH(
            LayoutInflater.from(parent.context).inflate(
                R.layout._licilongo_list_item, parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: LicilongoVH, position: Int) {
        val songTitle: String = getItem(position).title
        val song = getItem(position).song

        holder.songNum.text = (position + 1).toString()
        holder.textView.text = songTitle

        holder.parentContainer.setOnClickListener {
            val action = ListSongsFragmentDirections.actionListToDetails( // position + 1,
                songTitle = songTitle,
                song = song
            )
            navController.navigate(action)
        }
    }

    class LicilongoVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val parentContainer: LinearLayout = itemView.findViewById(R.id.parentContainer)
        val songNum: TextView = itemView.findViewById(R.id.songNum)
        val textView: TextView = itemView.findViewById(R.id.textView)
    }

    companion object {
        val SONG_COMPARATOR = object : DiffUtil.ItemCallback<Song>() {
            override fun areItemsTheSame(oldItem: Song, newItem: Song): Boolean {
                return oldItem.title == newItem.title
            }

            @SuppressLint("DiffUtilEquals")
            override fun areContentsTheSame(oldItem: Song, newItem: Song): Boolean {
                return oldItem == newItem
            }
        }
    }
}