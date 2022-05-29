package org.xhanka.biblesiswati.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation.findNavController
import org.xhanka.biblesiswati.databinding.FragmentHomeBinding
import org.xhanka.biblesiswati.ui.main.room.BibleViewModel

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    val bibleViewModel by activityViewModels<BibleViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bibleViewModel.bibleVersion.observe(viewLifecycleOwner) {
            binding.currentVersion.text = String.format("Selected: ${it.versionName}")
        }

        binding.homeNewCardView.setOnClickListener {
            val action = HomeFragmentDirections.actionNavHomeToNavBooks(1)
            findNavController(view).navigate(action)
        }

        binding.homeOldCardView.setOnClickListener {
            val action = HomeFragmentDirections.actionNavHomeToNavBooks(0)
            findNavController(view).navigate(action)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}