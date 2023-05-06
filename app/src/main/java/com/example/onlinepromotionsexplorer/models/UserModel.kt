package com.example.onlinepromotionsexplorer.models

data class UserModel(var documentId : String , var name : String ,  var phone : String ,  var profileImage : String)
{
    constructor() : this("","","","")
}