package com.example.onlinepromotionsexplorer.Tools

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.widget.ImageView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ImageLoader {
    companion object{
        fun setImageView(url:String, view :ImageView){
            GlobalScope.launch{

                var image : Bitmap? = null
                while(image == null){
                    image = getBitmap(url)
                    delay(500);
                }
                GlobalScope.launch (Dispatchers.Main){
                    view.setImageBitmap(image)
                }
            }
        }
        suspend fun getBitmap(url :String): Bitmap?{
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
}