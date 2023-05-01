package com.example.onlinepromotionsexplorer.Adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.onlinepromotionsexplorer.R
import com.example.onlinepromotionsexplorer.Tools.ImageLoader
import com.example.onlinepromotionsexplorer.ViewOfferActivity
import com.example.onlinepromotionsexplorer.models.Notification

class NotificationRecycleAdapter (private val dataSet : MutableList<Notification>): RecyclerView.Adapter<NotificationRecycleAdapter.ViewHolder>(){
    class ViewHolder (view: View) : RecyclerView.ViewHolder(view) {
        var imageView : ImageView
        var nameView : TextView
        var dateView : TextView
        var readLight : ImageView
        var notificationHolder : FrameLayout

        init {
            imageView= view.findViewById(R.id.notificationImage)
            nameView = view.findViewById(R.id.notificationNameField)
            dateView = view.findViewById(R.id.notificationDateField)
            readLight = view.findViewById(R.id.readDot)
            notificationHolder = view.findViewById(R.id.notificationHolder)
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.notification_card,parent,false)
        return NotificationRecycleAdapter.ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var notification = dataSet[position]
        val offer = notification.offer

        ImageLoader.setImageView(offer.imgLink,holder.imageView)
        holder.nameView.setText(offer.offerName)
        holder.dateView.setText(offer.getRemainingDateText())
        if(notification.isRead){
            holder.readLight.visibility = View.INVISIBLE
        }
        holder.notificationHolder.setOnClickListener{
            var intent : Intent = Intent(holder.nameView.context, ViewOfferActivity::class.java)
            intent.putExtra(ViewOfferActivity.OFFER_TEXT,offer)
            notification.isRead = true
            holder.nameView.context.startActivity(intent)
        }


    }

    override fun getItemCount(): Int {
        return dataSet.size
    }
}