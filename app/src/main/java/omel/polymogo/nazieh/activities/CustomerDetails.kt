package omel.polymogo.nazieh.activities

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import omel.polymogo.nazieh.R
import omel.polymogo.nazieh.helpers.MessageAlerts

class CustomerDetails : AppCompatActivity() , View.OnClickListener{
    var messageAlerts = MessageAlerts()
    private lateinit var  mContext: Context
    lateinit var txtVcustomer :TextView
    lateinit var txtVcustomerId :TextView
    lateinit var recycleCustomer : RecyclerView
    lateinit var btnDelete: Button
    lateinit var btnAddToList : Button

    private lateinit var mFirebaseDatabase: FirebaseDatabase
    private lateinit var myRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_customer_details)
        mContext = this
        txtVcustomer = findViewById(R.id.txtVCustomerNameDetail)
        btnAddToList = findViewById(R.id.fBtnAddCustomerDetail)
        txtVcustomerId = findViewById(R.id.txtVCustomerId)

        mFirebaseDatabase = FirebaseDatabase.getInstance()
        myRef = mFirebaseDatabase.getReference()

        //get data from intent
        val intent = intent

        try {
            val name = intent.getStringExtra("CustomerName")
            val id = intent.getStringExtra("CustomerId")
            txtVcustomer.setText(name)
            txtVcustomerId.setText(id)

        } finally {

        }

    }

    override fun onClick(p0: View?) {

    }

}
