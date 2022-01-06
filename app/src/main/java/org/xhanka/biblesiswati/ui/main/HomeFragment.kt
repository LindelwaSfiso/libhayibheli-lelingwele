package org.xhanka.biblesiswati.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation.findNavController
import org.xhanka.biblesiswati.R

class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<View>(R.id.home_new_card_view).setOnClickListener {
            val action = HomeFragmentDirections.actionNavHomeToNavBooks(0)
            findNavController(view).navigate(action)
        }

        view.findViewById<View>(R.id.home_old_card_view).setOnClickListener {
            val action = HomeFragmentDirections.actionNavHomeToNavBooks(1)
            findNavController(view).navigate(action)
        }
    }
}