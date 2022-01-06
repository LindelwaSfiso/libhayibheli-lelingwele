package org.xhanka.biblesiswati.ui.siswati_reference

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import org.xhanka.biblesiswati.databinding.FragmentSiswatiReferenceBinding
import org.xhanka.biblesiswati.ui.main.room.BibleViewModel

class SiswatiReferenceFragment : Fragment() {
    private lateinit var binding: FragmentSiswatiReferenceBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentSiswatiReferenceBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.addItemDecoration(
            DividerItemDecoration(
                view.context,
                DividerItemDecoration.VERTICAL
            )
        )

        val bibleViewModel by activityViewModels<BibleViewModel>()

        val adapter = RefAdapter(
            findNavController(view),
            bibleViewModel
        )
        recyclerView.adapter = adapter
        bibleViewModel.getRefBooks().observe(this, { list: List<RefBook?> ->
            adapter.submitList(list)
        })
    }
}