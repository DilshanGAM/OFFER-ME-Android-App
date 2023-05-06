package com.example.onlinepromotionsexplorer

import android.app.DatePickerDialog
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import com.example.onlinepromotionsexplorer.models.OfferModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.google.firebase.storage.FirebaseStorage
import java.util.*

class EditeOfferActivity : AppCompatActivity() {
    companion object{
        val OFFER_MODEL = "OFFER_MODEL"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edite_offer)

        val offerModel = intent.extras?.getSerializable(OFFER_MODEL) as? OfferModel
        var imageUri : Uri? = null
        val currentUser = FirebaseAuth.getInstance().currentUser
        //initializing inputs
        var categoryInput = findViewById<Spinner>(R.id.spinner)
        var nameInput = findViewById<EditText>(R.id.nameInput)
        var detailsInput = findViewById<EditText>(R.id.nameInput)

        var priceInput = findViewById<EditText>(R.id.priceInput)
        var percentageInput = findViewById<EditText>(R.id.percentageInput)
        var locationInput = findViewById<EditText>(R.id.locationInput)
        categoryInput.setSelection(resources.getStringArray(R.array.spinner_data).indexOf(offerModel!!.category))
        nameInput.setText(offerModel.name)
        detailsInput.setText(offerModel.details)
        priceInput.setText(offerModel.price.toString())
        percentageInput.setText(offerModel.percentage.toString())
        locationInput.setText(offerModel.location)


        if(currentUser == null){
            Toast.makeText(this, "Please  login before editing an offer", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this,LoginActivity::class.java))
        }



        //image input dialog initialization
        val pickMedia = registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
            // Callback is invoked after the user selects a media item or closes the
            // photo picker.
            if (uri != null) {
                imageUri = uri
                Toast.makeText(this, "Image selected", Toast.LENGTH_SHORT).show()

                Log.d("PhotoPicker", "Selected URI: $uri")
            } else {
                Log.d("PhotoPicker", "No media selected")
            }
        }

        findViewById<Button>(R.id.offerImageSelecter).setOnClickListener{
            pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        }

        //Date input fields
        var startInput = findViewById<EditText>(R.id.startInput)
        var endInput = findViewById<EditText>(R.id.endInput)

        //Calendar variables initialized to 1st January 2021
        val calendarStart = Calendar.getInstance()
        calendarStart.time = offerModel!!.start
        println(offerModel!!.start)
        val calendarEnd = Calendar.getInstance()
        calendarEnd.time = offerModel!!.end
        println(offerModel!!.end)


        startInput.setText(calendarStart.get(Calendar.YEAR).toString() + "/" + (calendarStart.get(
            Calendar.MONTH)+1) + "/" + calendarStart.get(Calendar.DAY_OF_MONTH).toString())
        endInput.setText(calendarEnd.get(Calendar.YEAR).toString() + "/" + (calendarEnd.get(Calendar.MONTH)+1) + "/" + calendarEnd.get(
            Calendar.DAY_OF_MONTH))

        findViewById<Button>(R.id.startDateBtn).setOnClickListener{
            var datePicker = DatePickerDialog.OnDateSetListener { datePicker, year, month, day ->
                calendarStart.set(Calendar.YEAR,year)
                calendarStart.set(Calendar.MONTH,month)
                calendarStart.set(Calendar.DAY_OF_MONTH,day)
                startInput.setText(calendarStart.get(Calendar.YEAR).toString() + "/" + (calendarStart.get(
                    Calendar.MONTH)+1).toString() + "/" + calendarStart.get(Calendar.DAY_OF_MONTH).toString())
            }
            DatePickerDialog(this,datePicker,calendarStart.get(Calendar.YEAR),calendarStart.get(
                Calendar.MONTH),calendarStart.get(
                Calendar.DAY_OF_MONTH)).show()
        }

        findViewById<Button>(R.id.endDateBtn).setOnClickListener{
            var datePicker = DatePickerDialog.OnDateSetListener { datePicker, year, month, day ->
                calendarEnd.set(Calendar.YEAR,year)
                calendarEnd.set(Calendar.MONTH,month)
                calendarEnd.set(Calendar.DAY_OF_MONTH,day)

                endInput.setText(calendarEnd.get(Calendar.YEAR).toString() + "/" + (calendarEnd.get(
                    Calendar.MONTH)+1).toString() + "/" + calendarEnd.get(Calendar.DAY_OF_MONTH).toString())
            }
            DatePickerDialog(this,datePicker,calendarEnd.get(Calendar.YEAR),calendarEnd.get(Calendar.MONTH),calendarEnd.get(
                Calendar.DAY_OF_MONTH)).show()
        }
        findViewById<Button>(R.id.addItemButton).setOnClickListener {
            var url = "https://firebasestorage.googleapis.com/v0/b/onlinepromotionsexplorer.appspot.com/o/images%2Fimage%3A1?alt=media&token=3b3b5b1e-7b9a-4b7a-8b0a-4b0b0b0b0b0b"
            val storage = FirebaseStorage.getInstance()
            val storageRef = storage.reference

            // Generate a unique filename for the image
            val fileName = UUID.randomUUID().toString()

            // Create a reference to the desired location in Firebase Storage
            val imageRef = storageRef.child("offerImages/$fileName")


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
                        //saving data when image available

                        println(startInput.text.toString())
                        var offer = OfferModel("",currentUser!!.uid,nameInput.text.toString(),categoryInput.selectedItem.toString(),detailsInput.text.toString(),
                            Date(calendarStart.timeInMillis),
                            Date(calendarEnd.timeInMillis),(priceInput.text.toString()).toDouble(),percentageInput.text.toString().toDouble(),locationInput.text.toString(),url,0)

                        val fireStore = FirebaseFirestore.getInstance()
                        val offerRef = fireStore.collection("Offers").document(offerModel!!.documentId)
                        offerRef.set(offer, SetOptions.merge()).addOnSuccessListener {
                            Toast.makeText(this, "Offer edited successfully", Toast.LENGTH_SHORT).show()
                            startActivity(Intent(this,OfferListActivity::class.java))
                        }.addOnFailureListener {
                            Toast.makeText(this, "Error editing offer", Toast.LENGTH_SHORT).show()
                        }
                    }

                }.addOnFailureListener { exception ->
                    // An error occurred while uploading the image
                    println("Error uploading image: $exception")
                }
            }else{
                //saving data when image not available
                var offer = OfferModel(offerModel!!.documentId,currentUser!!.uid,nameInput.text.toString(),categoryInput.selectedItem.toString(),detailsInput.text.toString(),
                    Date(calendarStart.timeInMillis),
                    Date(calendarEnd.timeInMillis),(priceInput.text.toString()).toDouble(),percentageInput.text.toString().toDouble(),locationInput.text.toString(),offerModel!!.imgLink,offerModel!!.bookmarks)

                val fireStore = FirebaseFirestore.getInstance()
                val offerRef = fireStore.collection("Offers").document(offerModel!!.documentId)
                offerRef.set(offer, SetOptions.merge()).addOnSuccessListener {
                    Toast.makeText(this, "Offer edited successfully", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this,OfferListActivity::class.java))
                }.addOnFailureListener {
                    Toast.makeText(this, "Error editing offer", Toast.LENGTH_SHORT).show()
                }
            }


        }

    }
}