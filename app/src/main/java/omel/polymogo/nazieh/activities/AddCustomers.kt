package omel.polymogo.nazieh.activities

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.FirebaseDatabase
import omel.polymogo.nazieh.R
import omel.polymogo.nazieh.helpers.MessageAlerts
import omel.polymogo.nazieh.models.Customer

class AddCustomers : AppCompatActivity(),View.OnClickListener {

    private var customer: Customer? = null
    var messageAlerts = MessageAlerts()
    private lateinit var  mContext: Context
    lateinit var btnSaveCustomer:Button
    lateinit var etName:EditText
    lateinit var etPhone:EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_customers)
        etName = findViewById(R.id.etcustomerName)
        etPhone = findViewById(R.id.etPhoneName)
        btnSaveCustomer = findViewById(R.id.btnSaveCustomer)
        btnSaveCustomer.setOnClickListener(this)
        mContext = this
    }

    override fun onClick(v: View?) {
        if (v!=null){
            when(v.getId()){
                R.id.btnSaveCustomer->{
                    if (etName.text.toString().isEmpty()){
                        messageAlerts.displayToast(mContext,"ضع اسم العميل ")
                    }else if (etPhone.text.toString().isEmpty())
                        messageAlerts.displayToast(mContext,"ضع رقم التليفون")
                    else
                    addUser(etName.text.toString(),etPhone.text.toString())
                }

            }
        }

    }

    fun addUser(
        userName: String,
        userPhone: String

    ) {
        try {
            val  ref =FirebaseDatabase.getInstance().getReference("Customers")
            val userId = ref.push().key
            val user = Customer(userId,userName,userPhone)
            ref.child(userId.toString()).setValue(user).addOnCompleteListener(){
                messageAlerts.displayToast(mContext,"تم الحفظ")
            }


        } catch (e: Exception) {
            e.printStackTrace()
        }
    }




}

