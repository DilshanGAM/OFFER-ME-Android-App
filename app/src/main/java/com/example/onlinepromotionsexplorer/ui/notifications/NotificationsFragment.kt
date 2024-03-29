package com.example.onlinepromotionsexplorer.ui.notifications

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.onlinepromotionsexplorer.AccountSettings
import com.example.onlinepromotionsexplorer.R
import com.example.onlinepromotionsexplorer.Tools.ImageLoader
import com.example.onlinepromotionsexplorer.databinding.FragmentNotificationsBinding
import com.example.onlinepromotionsexplorer.models.UserModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

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


        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}