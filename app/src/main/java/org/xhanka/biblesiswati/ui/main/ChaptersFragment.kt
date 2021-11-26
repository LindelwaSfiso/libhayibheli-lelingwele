package org.xhanka.biblesiswati.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.xhanka.biblesiswati.R
import org.xhanka.biblesiswati.common.setTextSizeSp
import org.xhanka.biblesiswati.ui.main.room.BibleViewModel
import java.util.*

class ChaptersFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_chapters, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recyclerView: RecyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.addItemDecoration(
            DividerItemDecoration(
                view.context,
                DividerItemDecoration.VERTICAL
            )
        )
        val bookName = if (arguments != null)
            arguments!!.getString("book_name", "Genesis")
        else "Genesis"

        val booksViewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(activity!!.application)
        ).get(BibleViewModel::class.java)

        booksViewModel.getChapterCount(bookName).observe(this, {
            val chapters = ArrayList<String>()
            for (i in 0 until it) chapters.add("Chapter " + (i + 1))
            recyclerView.adapter = Adapter(
                chapters,
                findNavController(view),
                bookName,
                booksViewModel.getTextSizeValue()
            )
        })

        /*val no_chapters = model.getNumberOfChaptersForBook(book_name!!)!!

        val chapters = ArrayList<String>()
        for (i in 0 until no_chapters) chapters.add("Chapter " + (i + 1))
        recyclerView.adapter = Adapter(
            chapters,
            findNavController(view),
            book_name,
            model.getTextSize()
        )*/
    }

    internal class Adapter(
        private var chapters: ArrayList<String>,
        var navController: NavController,
        var book_name: String?,
        var textSize: Int
    ) : RecyclerView.Adapter<Adapter.ViewHolder>() {
        var bundle = Bundle()
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            return ViewHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout._list_item, parent, false
                ),
                textSize
            )
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.textView.text = chapters[position]
            holder.textView.setOnClickListener {
                bundle.putString("book_name", book_name)
                bundle.putInt("chapter_num", position + 1)
                navController.navigate(R.id.action_nav_chapters_to_nav_verse_details, bundle)
            }
        }

        override fun getItemCount(): Int {
            return chapters.size
        }

        internal class ViewHolder(itemView: View, textSize: Int) :
            RecyclerView.ViewHolder(itemView) {
            var textView: TextView = itemView.findViewById(R.id.textView)

            init {
                textView.setTextSizeSp(textSize.toFloat())
            }
        }
    }
}