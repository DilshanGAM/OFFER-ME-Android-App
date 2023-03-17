package com.example.onlinepromotionsexplorer.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.onlinepromotionsexplorer.R
import com.example.onlinepromotionsexplorer.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root


        (activity as AppCompatActivity?)!!.setSupportActionBar(binding.root.findViewById(R.id.toolbar))
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}