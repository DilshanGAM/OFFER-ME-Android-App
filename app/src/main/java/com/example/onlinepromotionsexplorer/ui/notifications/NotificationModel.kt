package com.example.onlinepromotionsexplorer.ui.notifications

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class NotificationModel(var documentId : String , var offerId : String , var userId : String , var isRead : Boolean) {
    constructor() : this("","","",false)
    companion object{
        fun sendNotifications(offerName : String , category: String, price : Double){
            Log.e( "","gggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggg")

            GlobalScope.launch {
                Log.e( "",offerName +category+price.toString()+"xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx")
                FirebaseFirestore.getInstance().collection("Requests")
                    .whereEqualTo("name",offerName).
                    whereEqualTo("category",category).
                    whereGreaterThanOrEqualTo("price",price). get().addOnSuccessListener {
                            for (document in it.documents){
                                Log.e( "","kkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkk")
                                val notificationModel = NotificationModel()
                                notificationModel.documentId = ""
                                notificationModel.offerId = document.id
                                notificationModel.userId = document.get("userId").toString()
                                notificationModel.isRead = false
                                FirebaseFirestore.getInstance().collection("Notifications").add(notificationModel)
                            }
                    }


            }
        }
    }
}