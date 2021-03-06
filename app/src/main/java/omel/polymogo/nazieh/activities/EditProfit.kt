package omel.polymogo.nazieh.activities

import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.annotation.RequiresApi
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_edit_profit.*
import omel.polymogo.nazieh.R
import omel.polymogo.nazieh.helpers.KeyValueBD
import omel.polymogo.nazieh.helpers.MessageAlerts
import omel.polymogo.nazieh.models.incomePojo
import omel.polymogo.nazieh.recyclerviewAdapter.IncomeAdapter
import java.time.LocalDateTime

class EditProfit : AppCompatActivity(), View.OnClickListener {

    var messageAlerts = MessageAlerts()
    val key = KeyValueBD()
    lateinit var etCustomerProfitName: EditText
    lateinit var etTotalIncomeProfit: EditText
    lateinit var etNotes: EditText
    lateinit var btnAddProfit: Button
    lateinit var mContext: Context
    lateinit var mFirebaseDatabase: FirebaseDatabase
    lateinit var myRef: DatabaseReference
    var profitArray = ArrayList<incomePojo?>()
    var id: String = "m"


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profit)
        mContext = this
        etCustomerProfitName = findViewById(R.id.etcustomerNameProfit)
        etNotes = findViewById(R.id.etNotes)
        etTotalIncomeProfit = findViewById(R.id.etTotalIncomeProfit)
        btnAddProfit = findViewById(R.id.btnSaveProfit)
        btnAddProfit.setOnClickListener(this)

        mFirebaseDatabase = FirebaseDatabase.getInstance()
        myRef = mFirebaseDatabase.getReference()

        handle_Edit()

    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onClick(v: View?) {
        if (v != null) {
            when (v.getId()) {
                R.id.btnSaveProfit -> {
                    val current = LocalDateTime.now().toString()
                    if (btnAddProfit.text.equals("حفظ")) {
                        addProfit(
                            current,
                            (etTotalIncomeProfit.text.toString()).toDouble(),
                            etNotes.text.toString(),
                            etCustomerProfitName.text.toString()
                        )
                    } else {
                        updateProfit(
                            id,
                            current,
                            (etTotalIncomeProfit.text.toString()).toDouble(),
                            etNotes.text.toString(),
                            etCustomerProfitName.text.toString()
                        )
                    }
                }

            }
        }
    }

    fun addProfit(
        date: String,
        totalIncome: Double,
        notes: String,
        customerName: String
    ) {
        try {
            val ref = FirebaseDatabase.getInstance().getReference("Income")
            val userId = ref.push().key
            val income = incomePojo(userId, date, totalIncome, notes, customerName)
            ref.child(userId.toString()).setValue(income).addOnCompleteListener() {
                messageAlerts.displayToast(mContext, "تم الحفظ")
                val i = Intent(this, Profits::class.java)
                startActivity(i)
                finish()
            }

        } catch (e: Exception) {
            Log.d("tag", e.toString())
        }
    }

    fun updateProfit(
        userId: String,
        date: String,
        totalIncome: Double,
        notes: String,
        customerName: String
    ) {
        try {
            val ref = FirebaseDatabase.getInstance().getReference("Income")
            val income = incomePojo(userId, date, totalIncome, notes, customerName)
            ref.child(userId).setValue(income).addOnCompleteListener() {
                messageAlerts.displayToast(mContext, "تم التعديل")
                val i = Intent(this, Profits::class.java)
                startActivity(i)
                finish()
            }

        } catch (e: Exception) {
            Log.d("tag", e.toString())
        }
    }

    fun handle_Edit() {
        btnAddProfit.setText("حفظ")
        val bundle: Bundle? = intent.extras
        if (bundle != null) {
            val message = bundle.getString("editFlag")
            id = bundle.getString("ID")
            if (message.equals("edit")) {
                editNode(id)
            } else {

            }

        }
    }

    private fun editNode(id: String?) {
        rtriveLayouData(id)
    }


    private fun rtriveLayouData(id: String?) {
        val query = myRef.child("Income")
        query.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                try {
                    if (dataSnapshot.exists()) {

                        for (issue in dataSnapshot.children) {
                            val note = issue.getValue<incomePojo>(incomePojo::class.java)
                            profitArray.add(note)
                        }
                    }
                    for (i in profitArray) {
                        if (i?.userId.equals(id)) {
                            btnAddProfit.setText("تعديل")
                            etcustomerNameProfit.setText(i?.customerName)
                            etTotalIncomeProfit.setText(i?.totalIncome.toString())
                            etNotes.setText(i?.notes)
                        }
                    }

                } catch (e: Exception) {
                    e.printStackTrace()
                }

            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.e("ram", databaseError.details)
            }
        })

    }



}
