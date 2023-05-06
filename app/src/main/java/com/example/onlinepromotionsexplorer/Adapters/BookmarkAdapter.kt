package com.example.onlinepromotionsexplorer.Adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.FrameLayout
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.onlinepromotionsexplorer.R
import com.example.onlinepromotionsexplorer.Tools.ImageLoader
import com.example.onlinepromotionsexplorer.ViewOfferActivity
import com.example.onlinepromotionsexplorer.models.Offer
import com.example.onlinepromotionsexplorer.models.OfferModel
import com.example.onlinepromotionsexplorer.ui.bookmarks.BookmarksFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore

class BookmarkAdapter(private val dataSet : MutableList<OfferModel>, val activity :BookmarksFragment) : RecyclerView.Adapter<BookmarkAdapter.ViewHolder>(){

    class ViewHolder(view: View):RecyclerView.ViewHolder(view){
        val imageView : ImageView
        val nameView:TextView
        val dateView : TextView
        val layout : FrameLayout
        val deleteButton : ImageButton

        init{
            imageView= view.findViewById(R.id.bookmarkImage)
            nameView = view.findViewById(R.id.bookmarkNameField)
            dateView = view.findViewById(R.id.bookmarkDateField)
            deleteButton = view.findViewById(R.id.deleteButton)
            layout = view.findViewById(R.id.bookmarkHolder)
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BookmarkAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.bookmark_card,parent,false)
        return BookmarkAdapter.ViewHolder(view)
    }

    override fun onBindViewHolder(holder: BookmarkAdapter.ViewHolder, position: Int) {

        val offer = dataSet[position]

        ImageLoader.setImageView(offer.imgLink,holder.imageView)
        holder.nameView.setText(offer.name)
        holder.dateView.setText(OfferModel.getDaysSince(offer.end).toString() + " days left")

        holder.layout.setOnClickListener{

            var intent : Intent = Intent(holder.nameView.context, ViewOfferActivity::class.java)
            intent.putExtra(ViewOfferActivity.OFFER_TEXT,offer)
            holder.nameView.context.startActivity(intent)
        }
        holder.deleteButton.setOnClickListener{

            val currentUser = FirebaseAuth.getInstance().currentUser
            if(currentUser != null){
                FirebaseFirestore.getInstance().collection("Bookmarks").whereEqualTo("offerId",offer.documentId).whereEqualTo("userId",currentUser.uid).get().addOnSuccessListener {
                    val bookmarkId = it.documents.get(0).id
                    FirebaseFirestore.getInstance().collection("Bookmarks").document(bookmarkId).delete().addOnSuccessListener {
                        val offer = FirebaseFirestore.getInstance().collection("Offers").document(offer.documentId)
                        offer.update("bookmarks", FieldValue.increment(-1)).addOnSuccessListener {

                        }
                        activity.refresh()

                    }
                }



            }


        }
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }

}