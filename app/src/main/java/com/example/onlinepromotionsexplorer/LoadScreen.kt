package com.example.onlinepromotionsexplorer

import android.app.ActivityOptions
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.onlinepromotionsexplorer.models.UIEffects
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class LoadScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_load_screen)
        UIEffects.setValidStatusBar(this)
        GlobalScope.launch {
            delay(1000)
            startActivity(Intent(this@LoadScreen,LoginActivity::class.java))
        }



    }


}