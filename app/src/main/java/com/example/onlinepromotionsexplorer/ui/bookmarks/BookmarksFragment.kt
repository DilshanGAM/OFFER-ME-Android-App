package com.example.onlinepromotionsexplorer.ui.bookmarks

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.onlinepromotionsexplorer.Adapters.BookmarkAdapter
import com.example.onlinepromotionsexplorer.Adapters.NotificationRecycleAdapter
import com.example.onlinepromotionsexplorer.R
import com.example.onlinepromotionsexplorer.models.Notification
import com.example.onlinepromotionsexplorer.models.Offer
import com.example.onlinepromotionsexplorer.models.OfferModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class BookmarksFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root: View =  inflater.inflate(R.layout.fragment_bookmarks, container, false)
        recyclerView = root.findViewById<RecyclerView>(R.id.bookmarkRecycle)
        (activity as AppCompatActivity).setSupportActionBar(root.findViewById(R.id.bookmarkToolbar))


        updateBookmarkList()

        return root
    }
    fun updateBookmarkList(){
        val currentUser = FirebaseAuth.getInstance().currentUser
        if(currentUser == null){
            Toast.makeText(this.context, "Please login before view your bookmarks", Toast.LENGTH_SHORT).show()
        } else {
            val bookMarkedOfferList = mutableListOf<OfferModel>()
            FirebaseFirestore.getInstance().collection("Bookmarks").whereEqualTo("userId",currentUser?.uid).get().continueWith {
                val bookmarkedOfferIds = it.result?.documents?.map { it["offerId"] as String }
                bookmarkedOfferIds?.forEach { offerId ->
                    FirebaseFirestore.getInstance().collection("Offers").document(offerId).get().continueWith {
                        val offerModel = it.result?.toObject(OfferModel::class.java)
                        offerModel?.documentId = offerId
                        offerModel?.let { offer ->
                            bookMarkedOfferList.add(offer)

                        }
                    }.addOnCompleteListener {
                        recyclerView.layoutManager = LinearLayoutManager(this.context)
                        recyclerView.adapter = BookmarkAdapter(bookMarkedOfferList,this)
                    }
                }
            }










        }
        //val firestore = FirebaseFirestore.getInstance().collection("Bookmarks").whereEqualTo("userId",currentUser?.uid)

    }
    fun refresh(){
        findNavController().navigate(R.id.navigation_bookmarks)
    }


}