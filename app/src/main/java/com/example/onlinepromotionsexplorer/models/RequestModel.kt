package com.example.onlinepromotionsexplorer.models

import java.util.*

data class RequestModel(var documentId : String, val userId : String, val name : String,val category : String,  val price : Double, val start : Date, val end : Date) : java.io.Serializable {
    constructor() : this("", "", "", "", 0.0, Date(), Date())
}
