package com.example.onlinepromotionsexplorer.models

import java.util.Date

data class OfferModel(val name :String?,val category:String?,val details:String?,val start : Date , val end : Date,val price : Double , val percentage : Double , val location :String , val imgLink : String)
