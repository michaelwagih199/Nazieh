package omel.polymogo.nazieh.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.*
import omel.polymogo.nazieh.R
import omel.polymogo.nazieh.helpers.KeyValueBD
import omel.polymogo.nazieh.helpers.MessageAlerts
import omel.polymogo.nazieh.models.ExpencessPojo
import omel.polymogo.nazieh.models.incomePojo


class Tresury : AppCompatActivity(), View.OnClickListener {

    var messageAlerts = MessageAlerts()
    val key = KeyValueBD()

    private lateinit var btnProfit: Button
    private lateinit var btnExpense: Button
    private lateinit var mContext: Context
    private lateinit var mFirebaseDatabase: FirebaseDatabase
    private lateinit var myRef: DatabaseReference
    private lateinit var txtVtotalIncome: TextView

    private lateinit var txtVTotalRevenu: TextView
    private lateinit var txtVTotalOutCome: TextView
    var profitArray = ArrayList<incomePojo?>()
    var outComeArray = ArrayList<ExpencessPojo?>()

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tresury)
        mContext = this
        btnExpense = findViewById(R.id.btnExpenses)
        btnExpense.setOnClickListener(this)
        btnProfit = findViewById(R.id.btnProfit)
        btnProfit.setOnClickListener(this)
        mFirebaseDatabase = FirebaseDatabase.getInstance()
        myRef = mFirebaseDatabase.getReference()

        txtVtotalIncome = findViewById(R.id.txtVtotalIncome)
        txtVTotalOutCome = findViewById(R.id.txtVTotalOutCome)

        loadIncome()
        loadExpencess()
    }



    override fun onClick(v: View?) {
        if (v != null) {
            when (v.getId()) {
                R.id.btnExpenses -> {
                    val i = Intent(this, Expenses::class.java)
                    startActivity(i)
                    finish()
                }
                R.id.btnProfit -> {
                    val i = Intent(this, Profits::class.java)
                    startActivity(i)
                    finish()
                }
            }
        }
    }

    //function for calculate income
    fun calculateIncome() {
        val query = myRef.child("totalIncome")
        query.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                if (dataSnapshot.exists()) {
                    val result = dataSnapshot.getValue().toString()
                    txtVtotalIncome.text = result
                }

            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.e("ram", databaseError.details)
            }
        })

    }

    fun calculateexpensee() {

        val query = myRef.child("totalExpence")
        query.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                if (dataSnapshot.exists()) {
                    val result = dataSnapshot.getValue().toString()
                    txtVTotalOutCome.text = result
                }

            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.e("ram", databaseError.details)
            }
        })

    }


    private fun loadIncome() {
        var result :Double =0.0
        val query = myRef.child("Income")
        query.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                try {
                    if (dataSnapshot.exists()) {

                        for (issue in dataSnapshot.children) {
                            val note = issue.getValue<incomePojo>(incomePojo::class.java)
                            profitArray.add(note)

                        }

                        for (n in profitArray){
                            result = result.plus(n?.totalIncome!!)
                        }
                        txtVtotalIncome.text = result.toString()

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

    private fun loadExpencess() {
        var result :Double =0.0
        val query = myRef.child("expenses")
        query.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                try {
                    if (dataSnapshot.exists()) {

                        for (issue in dataSnapshot.children) {
                            val note = issue.getValue<ExpencessPojo>(ExpencessPojo::class.java)
                            outComeArray.add(note)

                        }

                        for (n in outComeArray){
                            result = result.plus(n?.expenses!!)
                        }

                        txtVTotalOutCome.text = result.toString()
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


