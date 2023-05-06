package com.example.onlinepromotionsexplorer.models

import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions

data class BookmarkModel(var userId : String, var offerId : String) : java.io.Serializable{
    companion object{
        fun markOrUnmark(checkBox : android.widget.CheckBox, offerId : String , bookmarkCount : TextView){
            var currentUser = FirebaseAuth.getInstance().currentUser
            if(currentUser == null){
                checkBox.isChecked = false
                Toast.makeText(checkBox.context, "Please login before placing bookmarks", Toast.LENGTH_SHORT).show()
                return
            }
            var bookmarkModel : BookmarkModel = BookmarkModel(currentUser.uid,offerId)
            if(checkBox.isChecked){
                val bookmarksCollection = FirebaseFirestore.getInstance().collection("Bookmarks")
                bookmarksCollection.add(bookmarkModel).addOnSuccessListener {
                    val offer = FirebaseFirestore.getInstance().collection("Offers").document(offerId)
                    offer.update("bookmarks",FieldValue.increment(1)).addOnSuccessListener {
                        bookmarkCount.setText(Offer.getBookmarkString(++Offer.offerModelList.find { it.documentId == offerId }!!.bookmarks))
                    }
                }
            }else{
                val bookmarksCollection = FirebaseFirestore.getInstance().collection("Bookmarks")
                bookmarksCollection.whereEqualTo("userId",currentUser.uid).whereEqualTo("offerId",offerId).get().addOnSuccessListener {
                    for (document in it.documents){
                        bookmarksCollection.document(document.id).delete().addOnSuccessListener {
                            val offer = FirebaseFirestore.getInstance().collection("Offers").document(offerId)
                            offer.update("bookmarks",FieldValue.increment(-1)).addOnSuccessListener {
                                bookmarkCount.setText(Offer.getBookmarkString(--Offer.offerModelList.find { it.documentId == offerId }!!.bookmarks))
                            }
                        }
                    }
                }
            }
        }
        fun isBookmarked(checkBox : android.widget.CheckBox, offerId : String){
            var currentUser = FirebaseAuth.getInstance().currentUser
            if(currentUser == null){
                checkBox.isChecked = false
                return
            }
            val bookmarksCollection = FirebaseFirestore.getInstance().collection("Bookmarks")
            bookmarksCollection.whereEqualTo("userId",currentUser.uid).whereEqualTo("offerId",offerId).get().addOnSuccessListener {
                if(it.documents.size > 0){
                    checkBox.isChecked = true
                }else{
                    checkBox.isChecked = false
                }
            }
        }
    }
    constructor() : this("","")
}
