package com.example.onlinepromotionsexplorer

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast

class MyRequestList : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_request_list)

        val Update=findViewById<Button>(R.id.btnUpdate)
        Update.setOnClickListener {
            Toast.makeText(this,"Update your details", Toast.LENGTH_SHORT).show()
            val intent4=Intent(this,EditRequest::class.java)
            startActivity(intent4)

        }
        val delete=findViewById<Button>(R.id.btnreqCancel)
        delete.setOnClickListener {
            Toast.makeText(this,"Delete your details", Toast.LENGTH_SHORT).show()

        }

        //Cliked floating button then go to request page
        val btnfab=findViewById<com.google.android.material.floatingactionbutton.FloatingActionButton>(R.id.btnfab)
        btnfab.setOnClickListener {
            Toast.makeText(this,"You clicked this", Toast.LENGTH_SHORT).show()
            val intent1=Intent(this,Request_Form::class.java)
            startActivity(intent1)
        }






    }
}