package com.example.onlinepromotionsexplorer.Adapters

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.onlinepromotionsexplorer.R
import com.example.onlinepromotionsexplorer.Tools.ImageLoader
import com.example.onlinepromotionsexplorer.ViewOfferActivity
import com.example.onlinepromotionsexplorer.models.Offer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class HomeRecycleAdapter(private val dataSet : MutableList<Offer>) : RecyclerView.Adapter<HomeRecycleAdapter.ViewHolder>(){
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val offerName: TextView
        val bookmarkButton : CheckBox
        val imageView : ImageView
        val layout : LinearLayout
        val bookmarks : TextView

        init {
            // Define click listener for the ViewHolder's View
            offerName = view.findViewById(R.id.offerName)
            bookmarkButton = view.findViewById(R.id.bookmarkButton)
            imageView = view.findViewById(R.id.offerImage)
            layout  = view.findViewById(R.id.offerCardHolder)
            bookmarks = view.findViewById(R.id.bookmarks)

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.offer_card,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.offerName.setText(dataSet[position].offerName)
        holder.bookmarkButton.isChecked = dataSet[position].bookmarked
        holder.bookmarks.setText(Offer.getBookmarkString(dataSet[position].totalBookmarks))
        ImageLoader.setImageView(dataSet[position].imgLink,holder.imageView)
        holder.layout.setOnClickListener{
            var intent :Intent = Intent(holder.offerName.context, ViewOfferActivity::class.java)
            intent.putExtra(ViewOfferActivity.OFFER_TEXT,dataSet[position])
            holder.offerName.context.startActivity(intent)
        }
        holder.bookmarkButton.setOnClickListener{
            dataSet[position].bookmarked = !dataSet[position].bookmarked
        }

    }

    override fun getItemCount(): Int {
        return dataSet.size
    }

}