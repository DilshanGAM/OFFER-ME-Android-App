package com.example.onlinepromotionsexplorer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.onlinepromotionsexplorer.Adapters.HomeRecycleAdapter
import com.example.onlinepromotionsexplorer.Adapters.NotificationRecycleAdapter
import com.example.onlinepromotionsexplorer.models.Notification
import com.example.onlinepromotionsexplorer.models.Offer
import com.example.onlinepromotionsexplorer.models.UIEffects

class NotificationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notification)
        setSupportActionBar(findViewById(R.id.notificationActivityToolbar))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_ios_new_24)
        UIEffects.setValidStatusBar(this)
        var recyclerView = findViewById<RecyclerView>(R.id.notificationRecycle)


        recyclerView.adapter = NotificationRecycleAdapter(Notification.notificationList
        )
        val layoutManager = LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)
        recyclerView.layoutManager = layoutManager

    }


}