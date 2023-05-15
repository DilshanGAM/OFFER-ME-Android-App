package com.example.onlinepromotionsexplorer

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract.CommonDataKinds.Email
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.example.onlinepromotionsexplorer.models.UIEffects
import com.example.onlinepromotionsexplorer.models.UserModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore

class Register : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        findViewById<TextView>(R.id.login_text).setOnClickListener{
            startActivity(Intent(this@Register,LoginActivity::class.java))


        }

    }
    fun createUserWithEmailAndPassword(email: String, password: String, onComplete: (FirebaseUser?) -> Unit, onError: (Exception) -> Unit) {
        val auth = FirebaseAuth.getInstance()
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    onComplete(user)
                } else {
                    try {
                        throw task.exception!!
                    } catch (e: FirebaseAuthInvalidCredentialsException) {
                        onError(e)
                    } catch (e: FirebaseAuthUserCollisionException) {
                        onError(e)
                    } catch (e: Exception) {
                        onError(e)
                    }
                }
            }
    }
    fun submit(view: android.view.View) {
        val usernameField = findViewById<TextView>(R.id.edtUsername)
        val passwordField = findViewById<TextView>(R.id.edtRegPassword)
        val confirmPasswordField = findViewById<TextView>(R.id.edtConfirmPassword)
        val phoneField = findViewById<TextView>(R.id.edtRegPhone)
        val regBtn = findViewById<Button>(R.id.regButton)
        val emailField = findViewById<TextView>(R.id.edtRegEmail)
        regBtn.setOnClickListener{
            val email = emailField.text.toString()
            val username = usernameField.text.toString()
            val password = passwordField.text.toString()
            val confirmPassword = confirmPasswordField.text.toString()
            val phone = phoneField.text.toString()
            if(username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty() || phone.isEmpty()){
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
            }else if(password != confirmPassword){
                Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show()

            }else{
                createUserWithEmailAndPassword(email,password,onComplete = {
                    val user = UserModel("",username,phone,"https://www.w3schools.com/w3css/img_avatar3.png","")
                    FirebaseFirestore.getInstance().collection("Users").document(it!!.uid).set(user).addOnSuccessListener {
                        Toast.makeText(this, "Registration Successful", Toast.LENGTH_SHORT).show()
                    }


                    startActivity(Intent(this@Register,LoginActivity::class.java))
                },onError = {
                    Toast.makeText(this, it.message.toString(), Toast.LENGTH_SHORT).show()

                })
            }
        }
    }
}