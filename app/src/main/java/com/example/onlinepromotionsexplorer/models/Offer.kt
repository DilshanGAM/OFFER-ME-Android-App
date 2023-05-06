package com.example.onlinepromotionsexplorer.models

import android.util.Log
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import org.jetbrains.annotations.TestOnly
import org.junit.Test
import java.util.Date

class Offer :java.io.Serializable{



    fun getRemainingDateText(): String {
        return "not created calculation function"
    }

    var offerName:String = "Not inserted"
    var bookmarked: Boolean = false
    var details: String = "Not given"
    var imgLink : String  = "null"
    var totalBookmarks : Int = 0
    var category : String = "null"
    var startDate : Date = Date()
    var endDate : Date = Date()
    var price : Double = 0.0
    var discount : Double = 0.0
    var location  : String = "null"


    constructor(offerName: String, bookmarked: Boolean, details: String, imgLink: String,totalBookmarks : Int) {
        this.offerName = offerName
        this.bookmarked = bookmarked
        this.details = details
        this.imgLink = imgLink
        this.totalBookmarks = totalBookmarks
    }
    fun setBookMarked(status : Boolean){
        this.bookmarked = status
    }
    companion object{




        var offerList : MutableList<Offer> = mutableListOf(
            Offer("Radio",false,"Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum","https://picsum.photos/300/300",1347),
            Offer("Television",false,"Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum","https://picsum.photos/200/300",6650),
            Offer("Mini LED projector",false,"Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum","https://picsum.photos/200/300",77),
            Offer("Mouse",true,"Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum","https://picsum.photos/200/300",99),
            Offer("Phone",false,"Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum","https://picsum.photos/300/300",63),
            Offer("Laptop",false,"Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum","https://picsum.photos/200/300",559),
            Offer("Chairs",false,"Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum","https://picsum.photos/200/300",8890),
            Offer("Milk powder",false,"Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum","https://picsum.photos/200/300",770),
            Offer("Camera",false,"Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum","https://picsum.photos/300/300",556),
            Offer("Trouser",false,"Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum","https://picsum.photos/200/300",784),
            Offer("Car tires",false,"Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum","https://picsum.photos/200/300",965),
            Offer("Coconut",false,"Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum","https://picsum.photos/200/300",81)
        )
        var offerModelList : MutableList<OfferModel> = mutableListOf()
        fun getBookMarkedList() :MutableList<Offer>{
            var bookMarkedList : MutableList<Offer> = mutableListOf()
            for(offer in offerList){
                if(offer.bookmarked){
                    bookMarkedList.add(offer)
                }
            }
            return bookMarkedList
        }


        fun getOfferList(){
            var database : DatabaseReference = Firebase.database.getReference("Offers")
            database.child("Offers").get().addOnSuccessListener {
                Log.i(it.toString(),"<<<<<<<< ")
            }

        }
        fun getBookmarkString(bookmarks:Int):String{
            if(bookmarks<=999){
                return (bookmarks.toString())
            }else if(bookmarks<=999999){
                if(bookmarks%1000>99){
                    return ((bookmarks/1000).toString()+"."+((bookmarks%1000)/100).toString()+"K")

                }else{
                    return ((bookmarks/1000).toString()+"K")
                }
            }else if(bookmarks <= 999999999){
                if(bookmarks%1000000>99999){
                    return ((bookmarks/1000000).toString()+"."+((bookmarks%1000000)/100000).toString()+"M")
                }else{
                    return ((bookmarks/1000000).toString()+"M")
                }

            }else{
                return "1B+"
            }
        }


    }

}