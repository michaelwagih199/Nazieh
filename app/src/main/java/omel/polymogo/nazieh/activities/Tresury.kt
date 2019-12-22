package omel.polymogo.nazieh.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import omel.polymogo.nazieh.R
import omel.polymogo.nazieh.helpers.KeyValueBD
import omel.polymogo.nazieh.helpers.MessageAlerts

class Tresury : AppCompatActivity() , View.OnClickListener {

    var messageAlerts = MessageAlerts()
    val key = KeyValueBD()

    private lateinit var btnProfit: Button
    private lateinit var btnExpense: Button

    private lateinit var mContext: Context

    private lateinit var mFirebaseDatabase: FirebaseDatabase
    private lateinit var myRef: DatabaseReference


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

    }

    override fun onClick(v: View?) {
        if (v!=null){
            when(v.getId()){
                R.id.btnExpenses->{
                    val i = Intent(this, Expenses::class.java)
                    startActivity(i)
                }
                R.id.btnProfit ->{
                    val i = Intent(this, Profits::class.java)
                    startActivity(i)
                }
            }
        }


    }
}
