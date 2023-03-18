package com.example.onlinepromotionsexplorer.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.onlinepromotionsexplorer.Adapters.HomeRecycleAdapter
import com.example.onlinepromotionsexplorer.R
import com.example.onlinepromotionsexplorer.databinding.FragmentHomeBinding
import com.example.onlinepromotionsexplorer.models.Offer

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


        var recyclerView = binding.homeRecycler


        recyclerView.adapter = HomeRecycleAdapter(arrayOf(
            Offer("Pizza",false,"sdaf","https://picsum.photos/300/300"),
            Offer("Pizza",true,"sdaf","https://picsum.photos/200/300"),
            Offer("Pizza",true,"sdaf","https://picsum.photos/200/300"),
            Offer("Pizza",true,"sdaf","https://picsum.photos/200/300"),
            Offer("Pizza",false,"sdaf","https://picsum.photos/300/300"),
            Offer("Pizza",true,"sdaf","https://picsum.photos/200/300"),
            Offer("Pizza",true,"sdaf","https://picsum.photos/200/300"),
            Offer("Pizza",true,"sdaf","https://picsum.photos/200/300"),
            Offer("Pizza",false,"sdaf","https://picsum.photos/300/300"),
            Offer("Pizza",true,"sdaf","https://picsum.photos/200/300"),
            Offer("Pizza",true,"sdaf","https://picsum.photos/200/300"),
            Offer("Pizza",true,"sdaf","https://picsum.photos/200/300")))
        val layoutManager = GridLayoutManager(this.context,2,GridLayoutManager.VERTICAL,false)

        recyclerView.layoutManager = GridLayoutManager(this.context,2,GridLayoutManager.VERTICAL,true)
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}