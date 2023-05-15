package com.example.onlinepromotionsexplorer

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.example.onlinepromotionsexplorer.Adapters.RequestAdapter
import com.example.onlinepromotionsexplorer.models.RequestModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class MyRequestList : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_request_list)



        updateRequestList()
        //Cliked floating button then go to request page
        val btnfab=findViewById<com.google.android.material.floatingactionbutton.FloatingActionButton>(R.id.btnfab)
        btnfab.setOnClickListener {
            Toast.makeText(this,"You clicked this", Toast.LENGTH_SHORT).show()
            val intent1=Intent(this,Request_Form::class.java)
            startActivity(intent1)
        }






    }
    // load my request list
    fun updateRequestList(){
        val currentUser = FirebaseAuth.getInstance().currentUser
        val firestore = FirebaseFirestore.getInstance()
        val docRef = firestore.collection("Requests")
        docRef.whereEqualTo("userId",currentUser!!.uid).get().addOnSuccessListener { documents ->
            val requestList = mutableListOf<RequestModel>()
            for (document in documents) {
                val requestModel = document.toObject(RequestModel::class.java)
                requestModel.documentId = document.id
                requestList.add(requestModel)
            }
            //request adapter
            val adapter = RequestAdapter(requestList,this)
            val recyclerView = findViewById<androidx.recyclerview.widget.RecyclerView>(R.id.requestRecycler)
            recyclerView.adapter = adapter
            recyclerView.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this)
        }.addOnFailureListener{
            Toast.makeText(this, "Error getting documents", Toast.LENGTH_SHORT).show()
        }
    }

}