package com.example.onlinepromotionsexplorer.models

import java.util.*


data class OfferModel(var documentId : String,val userId:String ,val name :String?,val category:String?,val details:String?,val start : Date , val end : Date,val price : Double , val percentage : Double , val location :String , val imgLink : String,var bookmarks : Int):java.io.Serializable{
    companion object{
        fun getDaysSince(date: Date): Long {
            val today = Calendar.getInstance().time
            val diff = ( date.time - today.time)
            if(diff <= 0)
                return 0
            return diff / (24 * 60 * 60 * 1000)
        }
    }
    constructor() : this("","","","", "", Date(), Date(),0.0,0.0,"","",0)
}

