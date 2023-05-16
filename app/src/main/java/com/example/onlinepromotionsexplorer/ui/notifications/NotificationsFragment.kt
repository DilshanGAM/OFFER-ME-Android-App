package com.example.onlinepromotionsexplorer.ui.notifications

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.example.onlinepromotionsexplorer.AccountSettings
import com.example.onlinepromotionsexplorer.OfferListActivity
import com.example.onlinepromotionsexplorer.R
import com.example.onlinepromotionsexplorer.Tools.ImageLoader
import com.example.onlinepromotionsexplorer.databinding.FragmentNotificationsBinding
import com.example.onlinepromotionsexplorer.models.OfferModel
import com.example.onlinepromotionsexplorer.models.UserModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*

class NotificationsFragment : Fragment() {

    private var _binding: FragmentNotificationsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentNotificationsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        root.findViewById<Button>(R.id.myAccBtn1)?.setOnClickListener{
            startActivity(Intent(this.activity, AccountSettings::class.java))
        }

        //get User document from firebase with documentId of current firebase user uid
        val userDoc = FirebaseFirestore.getInstance().collection("Users").document(FirebaseAuth.getInstance().currentUser!!.uid)
        userDoc.get().addOnSuccessListener {
            Log.e("User", it.toString())
            val user = it.toObject(UserModel::class.java)
            root.findViewById<TextView>(R.id.userNameArea).text = user?.name
            ImageLoader.setImageView(user?.profileImage!! , root.findViewById<ImageView>(R.id.userImageField))

            root.findViewById<TextView>(R.id.emailField).text = FirebaseAuth.getInstance().currentUser?.email
        }
        var imageUri: Uri? = null
        val pickMedia = registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
            // Callback is invoked after the user selects a media item or closes the
            // photo picker.
            if (uri != null) {
                imageUri = uri
                Toast.makeText(context, "Image selected", Toast.LENGTH_SHORT).show()

                Log.d("PhotoPicker", "Selected URI: $uri")
            } else {
                Log.d("PhotoPicker", "No media selected")
            }
        }

        root.findViewById<FloatingActionButton>(R.id.addImageBtn).setOnClickListener{
            pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
            var url = "https://firebasestorage.googleapis.com/v0/b/onlinepromotionsexplorer.appspot.com/o/images%2Fimage%3A1?alt=media&token=3b3b5b1e-7b9a-4b7a-8b0a-4b0b0b0b0b0b"
            val storage = FirebaseStorage.getInstance()
            val storageRef = storage.reference

            // Generate a unique filename for the image
            val fileName = UUID.randomUUID().toString()

            // Create a reference to the desired location in Firebase Storage
            val imageRef = storageRef.child("userImages/$fileName")
            GlobalScope.launch{
                var uri = imageUri
                while(imageUri == uri){
                    Thread.sleep(100)
                }
                if(imageUri != null){
                    // Upload the image file to Firebase Storage
                    val uploadTask = imageRef.putFile(imageUri!!)

                    // Register observers to track the upload progress
                    uploadTask.addOnSuccessListener { taskSnapshot ->
                        // Image uploaded successfully
                        println("Image uploaded successfully. Download URL: ${taskSnapshot.metadata?.reference?.downloadUrl}")
                        taskSnapshot.metadata?.reference?.downloadUrl.toString()
                        taskSnapshot.metadata?.reference?.downloadUrl?.addOnSuccessListener {
                            url = it.toString()
                            println("Download URL: $url")
                            FirebaseFirestore.getInstance().collection("Users").document(FirebaseAuth.getInstance().currentUser!!.uid).update(
                                "profileImage", url
                            )      .addOnSuccessListener {
                                ImageLoader.setImageView(url , root.findViewById<ImageView>(R.id.userImageField))
                            }
                        }

                    }.addOnFailureListener { exception ->
                        // An error occurred while uploading the image
                        println("Error uploading image: $exception")
                    }
                }

            }
        }


        return root
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)

    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}