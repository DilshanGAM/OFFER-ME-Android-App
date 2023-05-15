package com.example.onlinepromotionsexplorer.models

import java.util.*
//made this serializable to convert to this type when retrivig data from database
data class RequestModel(var documentId : String, val userId : String, val name : String,val category : String,  val price : Double, val start : Date, val end : Date) : java.io.Serializable {
    constructor() : this("", "", "", "", 0.0, Date(), Date())
}
