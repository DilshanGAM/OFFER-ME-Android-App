package com.example.onlinepromotionsexplorer

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.CheckBox
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import com.example.onlinepromotionsexplorer.Tools.ImageLoader
import com.example.onlinepromotionsexplorer.models.Offer
import com.example.onlinepromotionsexplorer.models.UIEffects

class ViewOfferActivity : AppCompatActivity() {
    companion object{
        val OFFER_TEXT : String = "OFFER_OBJECT"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_offer)
        var intent: Intent = getIntent()
        val offer : Offer =  intent!!.extras!![OFFER_TEXT] as Offer

        var title : TextView = findViewById(R.id.nameSlot)
        var description : TextView = findViewById(R.id.descriptionSlot)
        var location : TextView = findViewById(R.id.locationSlot)

        var bookmark :CheckBox = findViewById(R.id.bookmarkButtonView)
        var dueDate :TextView = findViewById(R.id.dateSlot)
        var imageView : ImageView = findViewById(R.id.imageSlot)
        title.setText(offer.offerName)
        description.setText(offer.details)
        bookmark.isChecked = offer.bookmarked
        ImageLoader.setImageView(offer.imgLink,imageView)
        setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_ios_new_24)
        UIEffects.setValidStatusBar(this)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onNavigateUp()
    }

}