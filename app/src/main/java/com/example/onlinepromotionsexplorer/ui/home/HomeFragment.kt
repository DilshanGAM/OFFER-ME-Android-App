package com.example.onlinepromotionsexplorer.ui.home


import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.onlinepromotionsexplorer.Adapters.HomeRecycleAdapter
import com.example.onlinepromotionsexplorer.Adapters.MyOfferListAdapter
import com.example.onlinepromotionsexplorer.LoginActivity
import com.example.onlinepromotionsexplorer.NotificationActivity
import com.example.onlinepromotionsexplorer.R
import com.example.onlinepromotionsexplorer.Services.NotificationService

import com.example.onlinepromotionsexplorer.databinding.FragmentHomeBinding
import com.example.onlinepromotionsexplorer.models.Offer
import com.example.onlinepromotionsexplorer.models.OfferModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

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

        binding.root.findViewById<FrameLayout>(R.id.viewNotificationButton).setOnClickListener{
            startActivity(Intent(activity,NotificationActivity::class.java))

        }

        var recyclerView = binding.homeRecycler
        val firestore = FirebaseFirestore.getInstance()
        firestore.collection("Offers").get().addOnSuccessListener {
            Offer.offerModelList.clear()
            for (document in it.documents){
                val offerModel  = (document.toObject(OfferModel::class.java)!!)
                offerModel?.documentId = document.id
                Offer.offerModelList.add(offerModel)
            }
            recyclerView.adapter?.notifyDataSetChanged()
            recyclerView.adapter = HomeRecycleAdapter(Offer.offerModelList)
            val layoutManager = GridLayoutManager(this.context,2,GridLayoutManager.VERTICAL,false)
            recyclerView.layoutManager = StaggeredGridLayoutManager(2 ,StaggeredGridLayoutManager.VERTICAL)

        }


//        val layoutManager = GridLayoutManager(this.context,2,GridLayoutManager.VERTICAL,false)
//        recyclerView.layoutManager = GridLayoutManager(this.context,2,GridLayoutManager.VERTICAL,true)
//        var notificationButton = activity?.findViewById<View>(R.id.notificationButton)
//        notificationButton?.setOnClickListener {
//
//        }
        this.context?.startService(Intent(this.context, NotificationService::class.java))

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}