package com.example.onlinepromotionsexplorer.ui.dashboard

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.onlinepromotionsexplorer.Adapters.BookmarkAdapter
import com.example.onlinepromotionsexplorer.MyRequestList
import com.example.onlinepromotionsexplorer.OfferListActivity
import com.example.onlinepromotionsexplorer.databinding.FragmentDashboardBinding
import com.example.onlinepromotionsexplorer.models.Offer
import com.example.onlinepromotionsexplorer.models.OfferModel
import com.example.onlinepromotionsexplorer.models.RequestModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val dashboardViewModel =
            ViewModelProvider(this).get(DashboardViewModel::class.java)

        _binding = FragmentDashboardBinding.inflate(inflater, container, false)

        binding.btnViewOffer.setOnClickListener{
            val intent  = Intent(this.context, OfferListActivity::class.java)
            startActivity(intent)
        }

        binding.btnRequest.setOnClickListener {
            val intent= Intent(this.context, MyRequestList ::class.java)
            startActivity(intent)
        }
        FirebaseFirestore.getInstance().collection("Offers").whereEqualTo("userId" , FirebaseAuth.getInstance().currentUser!!.uid ).get().addOnSuccessListener {
            binding.totalOffers.text = it.size().toString()
            //loop[ through every document and create array of OfferModel objects
            val offers = it.documents.map { it.toObject(OfferModel::class.java) } as ArrayList<OfferModel>
            //filter out offers that are having future day as end
            val offersFiltered = offers.filter { it.end.time > System.currentTimeMillis() }



            //set text of total active offers
            binding.activeOffers.text = offersFiltered.size.toString()
        }
        val currentUser = FirebaseAuth.getInstance().currentUser
        val firestore = FirebaseFirestore.getInstance()
        val docRef = firestore.collection("Requests")
        docRef.whereEqualTo("userId",currentUser!!.uid).get().addOnSuccessListener { documents ->
            binding.totalRequest.text = documents.size().toString()
        }
        FirebaseFirestore.getInstance()
            .collection("Notifications")
            .whereEqualTo("userId", currentUser?.uid)
            .get()
            .addOnSuccessListener {
                binding.notifiCount.text = it.size().toString()
            }
        FirebaseFirestore.getInstance().collection("Bookmarks").whereEqualTo("userId",currentUser?.uid).get().addOnSuccessListener {
            binding.bookmarkNumber.text = it.size().toString()
        }
        //score calculating
        var score = 0
        FirebaseFirestore.getInstance().collection("Offers").whereEqualTo("userId",currentUser?.uid).get().continueWith {
            val bookmarkedOfferIds = it.result?.documents?.map { it.id as String }
            for (offerId in bookmarkedOfferIds!!) {
                Log.e("OfferId", offerId)

            }
            bookmarkedOfferIds?.forEach { offerId ->

                FirebaseFirestore.getInstance().collection("Bookmarks").whereEqualTo("offerId",offerId).get().continueWith {
                    score += it.result?.documents?.size!!
                }.addOnCompleteListener {
                    binding.scorePlace.text = (score * 5).toString()
                }
            }
        }


        val root: View = binding.root




        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}