package com.example.onlinepromotionsexplorer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.onlinepromotionsexplorer.Adapters.BookmarkAdapter
import com.example.onlinepromotionsexplorer.Adapters.HomeRecycleAdapter
import com.example.onlinepromotionsexplorer.Adapters.NotificationRecycleAdapter
import com.example.onlinepromotionsexplorer.models.*
import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.Tasks
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class NotificationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notification)
        setSupportActionBar(findViewById(R.id.notificationActivityToolbar))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_ios_new_24)
        UIEffects.setValidStatusBar(this)
        val recyclerView = findViewById<RecyclerView>(R.id.notificationRecycle)
        val currentUser = FirebaseAuth.getInstance().currentUser
        val notificationList = mutableListOf<NotificationModel>()
        val offerList = mutableListOf<OfferModel>()

// Create a list of tasks to track the completion of all the continueWith calls
        val tasks = mutableListOf<Task<*>>()

        FirebaseFirestore.getInstance()
            .collection("Notifications")
            .whereEqualTo("userId", currentUser?.uid)
            .get()
            .addOnSuccessListener { querySnapshot ->
                for (document in querySnapshot.documents) {
                    val notificationModel = NotificationModel()
                    notificationModel.documentId = document.id
                    notificationModel.offerId = document.get("offerId").toString()
                    notificationModel.userId = document.get("userId").toString()
                    notificationModel.read = false
                    notificationModel.recieved = false
                    notificationList.add(notificationModel)
                }

                val notifiedOfferIds = querySnapshot.documents.map { it["offerId"] as String }

                notifiedOfferIds.forEach { offerId ->
                    val task = FirebaseFirestore.getInstance()
                        .collection("Offers")
                        .document(offerId)
                        .get()
                        .addOnSuccessListener {
                            Log.e("dsfdsafds",it.toString())
                            val offerModel = it.toObject<OfferModel>(OfferModel::class.java)
                            offerList.add(offerModel!!)
                        }
                    tasks.add(task)
                }

                // Wait for all tasks to complete before setting the adapter
                Tasks.whenAllComplete(tasks)
                    .addOnSuccessListener {
                        recyclerView.adapter = NotificationRecycleAdapter(notificationList, offerList)
                        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
                        recyclerView.layoutManager = layoutManager
                    }
            }





    }


}