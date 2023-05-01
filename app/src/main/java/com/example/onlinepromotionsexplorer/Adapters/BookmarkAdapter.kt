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
import com.example.onlinepromotionsexplorer.ui.bookmarks.BookmarksFragment

class BookmarkAdapter(private val dataSet : MutableList<Offer>,val activity :BookmarksFragment) : RecyclerView.Adapter<BookmarkAdapter.ViewHolder>(){

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
        holder.nameView.setText(offer.offerName)
        holder.dateView.setText(offer.getRemainingDateText())

        holder.layout.setOnClickListener{

            var intent : Intent = Intent(holder.nameView.context, ViewOfferActivity::class.java)
            intent.putExtra(ViewOfferActivity.OFFER_TEXT,offer)
            holder.nameView.context.startActivity(intent)
        }
        holder.deleteButton.setOnClickListener{

            offer.bookmarked = false
            activity.updateBookmarkList()
        }
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }

}