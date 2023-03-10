package com.example.onlinepromotionsexplorer

import android.app.ActivityOptions
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class LoadScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_load_screen)
        supportActionBar?.hide();
        startActivity(Intent(this,LoginActivity::class.java),
            ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
    }
}