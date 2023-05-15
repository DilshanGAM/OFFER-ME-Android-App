package com.example.onlinepromotionsexplorer

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore

class AccountSettings : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account_settings)
        val user = FirebaseAuth.getInstance().currentUser
        val db = FirebaseFirestore.getInstance()
        val userRef = db.collection("Users").document(user!!.uid)
        userRef.get().addOnSuccessListener { document ->
            if (document != null) {
                findViewById<EditText>(R.id.setName).setText(document.getString("name"))
                findViewById<EditText>(R.id.setNumber).setText(document.getString("phone"))
                findViewById<EditText>(R.id.setLocation).setText(document.getString("location"))
            } else {
                Log.d("TAG", "No such document")
            }
        }

        findViewById<Button>(R.id.updateButton).setOnClickListener {

            changeDetails(findViewById<EditText>(R.id.setName).editableText.toString(),findViewById<EditText>(R.id.setNumber).editableText.toString(), findViewById<EditText>(R.id.setLocation).editableText.toString())
        }
        findViewById<Button>(R.id.deleteAccButton).setOnClickListener{
            deleteCurrentUser()
        }


    }


    fun changeDetails(newName: String,newPhone:String , newLocation : String) {
        var user = FirebaseAuth.getInstance().currentUser
        val db = FirebaseFirestore.getInstance()
        val userRef = db.collection("Users").document(user!!.uid)
        userRef.update("name", newName).addOnSuccessListener {
            userRef.update("phone", newPhone).addOnSuccessListener {
                userRef.update("location", newLocation).addOnSuccessListener {
                    startActivity(Intent(this,HomeActivity::class.java))
                }
            }
        }



    }
    fun deleteCurrentUser() {
        val firebaseAuth = FirebaseAuth.getInstance()
        val currentUser: FirebaseUser? = firebaseAuth.currentUser
        var user = FirebaseAuth.getInstance().currentUser
        val db = FirebaseFirestore.getInstance()
        val userRef = db.collection("Users").document(user!!.uid).delete().addOnSuccessListener{
            currentUser?.delete()?.addOnCompleteListener { deleteTask ->
                if (deleteTask.isSuccessful) {
                    startActivity(Intent(this,LoginActivity::class.java))
                } else {
                    // User deletion failed
                    val exception = deleteTask.exception
                    // Handle the error appropriately
                }
            }
        }

    }

}