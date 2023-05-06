package com.example.onlinepromotionsexplorer.Services

import android.app.*
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.os.SystemClock
import android.util.Log
import androidx.core.app.NotificationCompat
import com.example.onlinepromotionsexplorer.NotificationActivity
import com.example.onlinepromotionsexplorer.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore

class NotificationService : Service() {

    private lateinit var db: FirebaseFirestore
    private var currentUser: FirebaseUser? = null

    override fun onCreate() {
        super.onCreate()

        // Initialize the Firebase Firestore instance
        db = FirebaseFirestore.getInstance()

        // Get the current user
        currentUser = FirebaseAuth.getInstance().currentUser
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        // Query the Firestore database to get the notifications for the current user
        db.collection("Notifications")
            .whereEqualTo("userId", currentUser?.uid)
            .whereEqualTo("recieved", false)
            .get()
            .addOnSuccessListener { result ->

                for (document in result) {
                    // Create a notification for each unread notification

                    val notificationId = document.id.toString()
                    val notificationIntent = Intent(this, NotificationActivity::class.java)
                    notificationIntent.putExtra("notificationId", notificationId)
                    val pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT)

                    val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        val channel = NotificationChannel("default", "Notifications", NotificationManager.IMPORTANCE_DEFAULT)
                        notificationManager.createNotificationChannel(channel)
                    }

                    val notification = NotificationCompat.Builder(this, "default")
                        .setSmallIcon(R.drawable.ic_notifications_black_24dp)
                        .setContentTitle("New matching offer")
                        .setContentText("You have received new matching offer")
                        .setContentIntent(pendingIntent)
                        .setAutoCancel(true)
                        .build()

                    notificationManager.notify(0, notification)

                    // Mark the notification as read
                    document.reference.update("recieved", true)
                }
            }
            .addOnFailureListener { exception ->
                Log.d(TAG, "Error getting notifications: $exception")
            }
        scheduleNextRun()
        return START_NOT_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }
    private fun scheduleNextRun() {
        val alarmManager = getSystemService(ALARM_SERVICE) as AlarmManager
        val intervalMillis = 60 * 1000 // 1 minute
        val triggerAtMillis = SystemClock.elapsedRealtime() + intervalMillis
        val intent = Intent(this, NotificationService::class.java)
        val pendingIntent = PendingIntent.getService(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        alarmManager.setExactAndAllowWhileIdle(AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerAtMillis, pendingIntent)
    }
}
