package com.example.onlinepromotionsexplorer.Adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.example.onlinepromotionsexplorer.EditeOfferActivity
import com.example.onlinepromotionsexplorer.OfferListActivity
import com.example.onlinepromotionsexplorer.R
import com.example.onlinepromotionsexplorer.Tools.ImageLoader
import com.example.onlinepromotionsexplorer.ViewOfferActivity
import com.example.onlinepromotionsexplorer.models.Offer
import com.example.onlinepromotionsexplorer.models.OfferModel
import com.google.firebase.firestore.FirebaseFirestore

class MyOfferListAdapter(private val dataSet : MutableList<OfferModel>,private val listing : OfferListActivity) : RecyclerView.Adapter<MyOfferListAdapter.ViewHolder>(){
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val offerNameField: TextView
        val imageFiled : ImageView
        val priceField : TextView
        val editButton : Button
        val deleteButton : Button

        init {
            // Define click listener for the ViewHolder's View
            offerNameField = view.findViewById(R.id.userOfferName)
            imageFiled = view.findViewById(R.id.userOfferImage)
            priceField = view.findViewById(R.id.userOfferPrice)
            editButton = view.findViewById(R.id.offerEditButton)
            deleteButton = view.findViewById(R.id.offerDeleteBtn)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.user_offer_card,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.offerNameField.text = dataSet[position].name
        holder.priceField.text = (dataSet[position].price - (dataSet[position].percentage * dataSet[position].price/100)).toString()
        ImageLoader.setImageView(dataSet[position].imgLink,holder.imageFiled)
        holder.editButton.setOnClickListener{
//            var intent : Intent = Intent(holder.offerNameField.context, ViewOfferActivity::class.java)
//            intent.putExtra(ViewOfferActivity.OFFER_TEXT,dataSet[position])
//            holder.offerNameField.context.startActivity(intent)
        }
        holder.deleteButton.setOnClickListener{
            val docRef = FirebaseFirestore.getInstance().collection("Offers").document(dataSet[position].documentId)
            docRef.delete()
                .addOnSuccessListener {
                    listing.updateOfferList()
                    Toast.makeText(holder.offerNameField.context, "Item deleted successfully", Toast.LENGTH_SHORT).show()

                }.addOnFailureListener{
                    Toast.makeText(holder.offerNameField.context, "Item deletion failed", Toast.LENGTH_SHORT).show()
                }
        }
        holder.editButton.setOnClickListener{
            var intent : Intent = Intent(holder.offerNameField.context, EditeOfferActivity::class.java)
            intent.putExtra(EditeOfferActivity.OFFER_MODEL,dataSet[position])
            holder.offerNameField.context.startActivity(intent)
        }
//        holder.offerName.setText(dataSet[position].offerName)
//        holder.bookmarkButton.isChecked = dataSet[position].bookmarked
//        holder.bookmarks.setText(Offer.getBookmarkString(dataSet[position].totalBookmarks))
//        ImageLoader.setImageView(dataSet[position].imgLink,holder.imageView)
//        holder.layout.setOnClickListener{
//            var intent : Intent = Intent(holder.offerName.context, ViewOfferActivity::class.java)
//            intent.putExtra(ViewOfferActivity.OFFER_TEXT,dataSet[position])
//            holder.offerName.context.startActivity(intent)
//        }
//        holder.bookmarkButton.setOnClickListener{
//            dataSet[position].bookmarked = !dataSet[position].bookmarked
//        }

    }

    override fun getItemCount(): Int {
        return dataSet.size
    }

}