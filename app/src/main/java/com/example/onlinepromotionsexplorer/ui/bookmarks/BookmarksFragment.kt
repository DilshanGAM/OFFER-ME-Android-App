package com.example.onlinepromotionsexplorer.ui.bookmarks

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.onlinepromotionsexplorer.Adapters.BookmarkAdapter
import com.example.onlinepromotionsexplorer.Adapters.NotificationRecycleAdapter
import com.example.onlinepromotionsexplorer.R
import com.example.onlinepromotionsexplorer.models.Notification
import com.example.onlinepromotionsexplorer.models.Offer


class BookmarksFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root: View =  inflater.inflate(R.layout.fragment_bookmarks, container, false)
        recyclerView = root.findViewById<RecyclerView>(R.id.bookmarkRecycle)
        (activity as AppCompatActivity).setSupportActionBar(root.findViewById(R.id.bookmarkToolbar))


        updateBookmarkList()

        return root
    }
    fun updateBookmarkList(){
        recyclerView.adapter = BookmarkAdapter(
            Offer.getBookMarkedList()
            , this)
        val layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL,false)
        recyclerView.layoutManager = layoutManager
    }


}