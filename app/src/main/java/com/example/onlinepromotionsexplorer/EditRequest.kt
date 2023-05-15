package com.example.onlinepromotionsexplorer

import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import com.example.onlinepromotionsexplorer.models.RequestModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import java.text.SimpleDateFormat
import java.util.*

class EditRequest : AppCompatActivity() {

    private lateinit var tvDatePicker: TextView
    private lateinit var btnDatePicker: Button
    private lateinit var tvDatePicker1:TextView
    private lateinit var btnDatePicker1:Button
    companion object{
        val REQUEST="REQUEST"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_request_form)
        val currentUser = FirebaseAuth.getInstance().currentUser
        if (currentUser == null) {
            startActivity(Intent(this, HomeActivity::class.java))
        }
        val requestModel = intent.extras?.getSerializable(REQUEST) as RequestModel

        tvDatePicker = findViewById(R.id.tvDate)
        btnDatePicker = findViewById(R.id.btnDatePicker)
        tvDatePicker1=findViewById(R.id.tvDate1)
        btnDatePicker1=findViewById(R.id.btnDatePicker1)
        val btnSubmit: Button = findViewById(R.id.btnSubmit)
        val category: Spinner = findViewById(R.id.requestCategory)
        category.setSelection(resources.getStringArray(R.array.spinner_data).indexOf(requestModel.category))
        val name : EditText = findViewById(R.id.txtnameitem)
        name.setText(requestModel.name)
        val price : EditText = findViewById(R.id.txtprice)
        price.setText(requestModel.price.toString())
        val submit : Button = findViewById(R.id.btnSubmit)


        val myCalendar = Calendar.getInstance()
        myCalendar.time = requestModel.start
        updatelable(myCalendar)
        val datePicker= DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
            myCalendar.set(Calendar.YEAR,year)
            myCalendar.set(Calendar.MONTH,month)
            myCalendar.set(Calendar.DAY_OF_MONTH,dayOfMonth)
            updatelable(myCalendar)
        }
        btnDatePicker.setOnClickListener {
            DatePickerDialog(this,datePicker,myCalendar.get(Calendar.YEAR),myCalendar.get(Calendar.MONTH),myCalendar.get(
                Calendar.DAY_OF_MONTH)).show()
        }

        val myCalendar1= Calendar.getInstance()
        myCalendar1.time = requestModel.end
        updatelable1(myCalendar1)
        val datePicker1= DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
            myCalendar1.set(Calendar.YEAR,year)
            myCalendar1.set(Calendar.MONTH,month)
            myCalendar1.set(Calendar.DAY_OF_MONTH,dayOfMonth)
            updatelable1(myCalendar1)
        }
        btnDatePicker1.setOnClickListener {
            DatePickerDialog(this,datePicker1,myCalendar1.get(Calendar.YEAR),myCalendar1.get(Calendar.MONTH),myCalendar1.get(
                Calendar.DAY_OF_MONTH)).show()

        }
        //update the request
        submit.setOnClickListener {
            val firestore = FirebaseFirestore.getInstance()

            val request = RequestModel(
                "",
                currentUser!!.uid,
                name.text.toString(),
                category.selectedItem.toString(),
                price.text.toString().toDouble(),
                Date(myCalendar.timeInMillis),
                Date(myCalendar1.timeInMillis),

                )
            val reqRef = firestore.collection("Requests").document(requestModel.documentId)
            reqRef.set(request, SetOptions.merge()).addOnSuccessListener {
                Toast.makeText(this, "Request Updated", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, MyRequestList::class.java))
            }.addOnFailureListener {
                Toast.makeText(this, "Request Failed", Toast.LENGTH_SHORT).show()
            }
        }



    }

    private fun updatelable1(myCalendar1: Calendar) {
        val myFormat="dd-MM-yyyy"
        val sdf= SimpleDateFormat(myFormat, Locale.UK)
        tvDatePicker1.setText(sdf.format(myCalendar1.time))
    }

    private fun updatelable(myCalendar: Calendar) {
        val myFormat1="dd-MM-yyyy"
        val sdf1= SimpleDateFormat(myFormat1, Locale.UK)
        tvDatePicker.setText(sdf1.format(myCalendar.time))
    }
}