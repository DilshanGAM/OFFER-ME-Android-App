package com.example.onlinepromotionsexplorer.models

import android.content.Context
import android.content.res.Configuration
import android.view.View
import androidx.appcompat.app.AppCompatActivity

class UIEffects {
    companion object{
        fun setValidStatusBar(activity: AppCompatActivity){
            var decor: View = activity.window.decorView
            decor.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            when (activity.resources?.configuration?.uiMode?.and(Configuration.UI_MODE_NIGHT_MASK)) {
                Configuration.UI_MODE_NIGHT_YES -> {decor.systemUiVisibility = 0 }
                Configuration.UI_MODE_NIGHT_NO -> {decor.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR}
                Configuration.UI_MODE_NIGHT_UNDEFINED -> {decor.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR}
            }

        }
    }
}