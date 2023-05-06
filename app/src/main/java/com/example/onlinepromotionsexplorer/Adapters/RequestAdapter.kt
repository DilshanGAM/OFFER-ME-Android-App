package com.example.onlinepromotionsexplorer.Adapters

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.example.onlinepromotionsexplorer.*
import com.example.onlinepromotionsexplorer.Tools.ImageLoader
import com.example.onlinepromotionsexplorer.models.Offer
import com.example.onlinepromotionsexplorer.models.RequestModel
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class RequestAdapter(private val dataSet : MutableList<RequestModel>,private val context : MyRequestList) : RecyclerView.Adapter<RequestAdapter.ViewHolder>(){

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nameField: TextView
        val dateField : TextView
        val priceField : TextView
        val editBtn : Button
        val deleteBtn : Button

        init {
            // Define click listener for the ViewHolder's View
            nameField = view.findViewById(R.id.rqstName)
            dateField = view.findViewById(R.id.rqstEndDate)
            priceField = view.findViewById(R.id.rqstPrice)
            editBtn  = view.findViewById(R.id.reqEditBtn)
            deleteBtn = view.findViewById(R.id.reqDeleteBtn)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.request_card,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.nameField.setText("Item name : "+dataSet[position].name)
        holder.dateField.setText("Date : "+dataSet[position].end.toString())
        holder.priceField.setText("Price : " +
                ""+dataSet[position].price.toString())
        holder.deleteBtn.setOnClickListener{
            val reqRef = FirebaseFirestore.getInstance().collection("Requests").document(dataSet[position].documentId)
            reqRef.delete().addOnSuccessListener {
                Toast.makeText(holder.nameField.context,"Deleted",Toast.LENGTH_SHORT).show()
                context.updateRequestList()
            }.addOnFailureListener{
                Toast.makeText(holder.nameField.context,"Error",Toast.LENGTH_SHORT).show()
            }
            Toast.makeText(holder.nameField.context,"Edit",Toast.LENGTH_SHORT).show()
        }
        holder.editBtn.setOnClickListener{
            var intent : Intent = Intent(holder.nameField.context, EditRequest::class.java)
            intent.putExtra(EditRequest.REQUEST,dataSet[position])
            holder.nameField.context.startActivity(intent)
        }
//        holder.offerName.setText(dataSet[position].offerName)
//        holder.bookmarkButton.isChecked = dataSet[position].bookmarked
//        holder.bookmarks.setText(Offer.getBookmarkString(dataSet[position].totalBookmarks))
//        ImageLoader.setImageView(dataSet[position].imgLink,holder.imageView)
//        holder.layout.setOnClickListener{
//            var intent :Intent = Intent(holder.offerName.context, ViewOfferActivity::class.java)
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