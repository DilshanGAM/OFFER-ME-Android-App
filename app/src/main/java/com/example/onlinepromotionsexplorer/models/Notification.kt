package com.example.onlinepromotionsexplorer.models

class Notification (val offer: Offer, var isRead : Boolean) :java.io.Serializable{

    constructor(offer:Offer):this(offer,true)
    companion object{
        var notificationList : MutableList<Notification> = mutableListOf(
            Notification(Offer("Pizza",false,"sdaf","https://picsum.photos/300/300",793),true),
            Notification(Offer("Radio",false,"sdaf","https://picsum.photos/300/200",5005),false),
            Notification(Offer("Television",false,"sdaf","https://picsum.photos/200/300",789),true),
            Notification(Offer("Pizza",false,"sdaf","https://picsum.photos/300/300",89),true),
            Notification(Offer("Pizza",false,"sdaf","https://picsum.photos/300/300",555),true),
            Notification(Offer("PHONE",false,"sdaf","https://picsum.photos/300/300",135),false),
            Notification(Offer("Pizza",false,"sdaf","https://picsum.photos/300/300",996),true),
            Notification(Offer("Pizza",false,"sdaf","https://picsum.photos/300/300",1005),true),
            Notification(Offer("Pizza",false,"sdaf","https://picsum.photos/300/300",7566),true)
        )

    }

}