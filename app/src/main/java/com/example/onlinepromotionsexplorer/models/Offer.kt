package com.example.onlinepromotionsexplorer.models

class Offer {
    var offerName:String = "Not inserted"
    var bookmarked: Boolean = false
    var details: String = "Not given"
    var imgLink : String  = "null"

    constructor(offerName: String, bookmarked: Boolean, details: String, imgLink: String) {
        this.offerName = offerName
        this.bookmarked = bookmarked
        this.details = details
        this.imgLink = imgLink
    }
}