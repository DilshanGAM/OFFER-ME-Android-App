package com.example.onlinepromotionsexplorer

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.onlinepromotionsexplorer.Adapters.MyOfferListAdapter
import com.example.onlinepromotionsexplorer.models.Offer
import com.example.onlinepromotionsexplorer.models.OfferModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class OfferListActivity : AppCompatActivity() {

    //oncreat function
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_offer_list)
        val currentUser = FirebaseAuth.getInstance().currentUser
        if(currentUser == null){
            Toast.makeText(this, "Please  login before adding an offer", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this,LoginActivity::class.java))
        }
        findViewById<FloatingActionButton>(R.id.addOfferButton).setOnClickListener {
            startActivity(Intent(this,InsertofferActivity::class.java))
        }
        updateOfferList()

    }

    //update offer list
    fun updateOfferList(){
        val recycler = findViewById<RecyclerView>(R.id.userOfferRecycle)
        val firestore = FirebaseFirestore.getInstance()
        firestore.collection("Offers").whereEqualTo("userId" ,FirebaseAuth.getInstance().currentUser!!.uid ).get().addOnSuccessListener {
            Offer.offerModelList.clear()
            for (document in it.documents){
                val offerModel  = (document.toObject(OfferModel::class.java)!!)
                offerModel?.documentId = document.id
                Offer.offerModelList.add(offerModel)
            }
            recycler.adapter?.notifyDataSetChanged()
            recycler.adapter = MyOfferListAdapter(Offer.offerModelList,this)
            val layoutManager = GridLayoutManager(this,2, GridLayoutManager.VERTICAL,false)
            recycler.layoutManager = StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.VERTICAL)
        }
    }
}