package com.example.onlinepromotionsexplorer.Adapters

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.onlinepromotionsexplorer.R
import com.example.onlinepromotionsexplorer.models.Offer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class HomeRecycleAdapter(private val dataSet : Array<Offer>) : RecyclerView.Adapter<HomeRecycleAdapter.ViewHolder>(){
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val offerName: TextView
        val bookmarkButton : CheckBox
        val imageView : ImageView

        init {
            // Define click listener for the ViewHolder's View
            offerName = view.findViewById(R.id.offerName)
            bookmarkButton = view.findViewById(R.id.bookmarkButton)
            imageView = view.findViewById(R.id.offerImage)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.offer_card,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.offerName.setText(dataSet[position].offerName)
        holder.bookmarkButton.isChecked = dataSet[position].bookmarked
        GlobalScope.launch{

            var image : Bitmap? = null
            while(image == null){
                image = getBitmap(dataSet[position].imgLink)
                delay(500);
            }
            GlobalScope.launch (Dispatchers.Main){
                holder.imageView.setImageBitmap(image)
            }
        }
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }
    suspend fun getBitmap(url :String):Bitmap?{
        var image: Bitmap? = null
        try {
            val inputStream = java.net.URL(url).openStream()
            image = BitmapFactory.decodeStream(inputStream)
        }catch(e:Exception){
            e.printStackTrace()
        }

        return image;
    }
}