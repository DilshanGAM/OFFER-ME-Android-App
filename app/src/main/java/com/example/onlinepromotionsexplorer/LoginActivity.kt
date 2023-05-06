package com.example.onlinepromotionsexplorer

import android.app.Activity
import android.content.ContentValues.TAG
import android.content.Intent
import android.content.IntentSender
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.example.onlinepromotionsexplorer.models.UIEffects
import com.example.onlinepromotionsexplorer.models.UserModel
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.FirebaseFirestore

class LoginActivity : AppCompatActivity() {

    private lateinit var auth : FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_login)

        findViewById<TextView>(R.id.register_text).setOnClickListener{
            startActivity(Intent(this@LoginActivity,Register::class.java))
        }

        supportActionBar?.hide()
        UIEffects.setValidStatusBar(this)
        auth = FirebaseAuth.getInstance();

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(this,gso);

        if(auth.currentUser != null){
            startActivity( Intent(this,HomeActivity::class.java))
        }


    }
    fun click(view: View){
        val signInIntent = googleSignInClient.signInIntent
        launcher.launch(signInIntent)

    }
    private val launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        result ->
        if(result.resultCode == Activity.RESULT_OK){
            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            handleResults(task)
        }
    }
    private fun handleResults(task: Task<GoogleSignInAccount>){
        if(task.isSuccessful){
            val account : GoogleSignInAccount? = task.result
            if(account != null){


                auth.signInWithCredential(GoogleAuthProvider.getCredential(account.idToken,null)).addOnSuccessListener {

                    val user = UserModel("",account.displayName!!,"",account.photoUrl.toString())
                    FirebaseFirestore.getInstance().collection("Users").document(it.user?.uid!!).set(user).addOnSuccessListener {
                        Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show()
                    }
                }


            }
        }else{
            Toast.makeText(this, task.exception.toString(), Toast.LENGTH_SHORT).show()
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)


    }



}