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
import omel.polymogo.nazieh.R
import omel.polymogo.nazieh.helpers.KeyValueBD
import omel.polymogo.nazieh.helpers.MessageAlerts
import omel.polymogo.nazieh.models.ExpencessPojo
import omel.polymogo.nazieh.models.incomePojo
import java.time.LocalDateTime


class EditExpensess : AppCompatActivity(), View.OnClickListener {

    var messageAlerts = MessageAlerts()
    val key = KeyValueBD()
    private lateinit var etTotalexpensee: EditText
    private lateinit var etStatment: EditText
    private lateinit var btnSaveExpenses: Button
    private lateinit var mContext: Context

    private lateinit var mFirebaseDatabase: FirebaseDatabase
    private lateinit var myRef: DatabaseReference
    var ExpencesstArray = ArrayList<ExpencessPojo?>()
    var id: String = "m"

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_expensess)

        mContext = this
        etStatment = findViewById(R.id.etStatment)
        etTotalexpensee = findViewById(R.id.etTotalexpensee)
        btnSaveExpenses = findViewById(R.id.btnSaveExpenses)
        btnSaveExpenses.setOnClickListener(this)

        mFirebaseDatabase = FirebaseDatabase.getInstance()
        myRef = mFirebaseDatabase.getReference()

        handle_Edit()

    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onClick(v: View?) {
        if (v != null) {
            when (v.getId()) {
                R.id.btnSaveExpenses -> {
                    val current = LocalDateTime.now().toString()
                    if (btnSaveExpenses.text.equals("حفظ")) {
                        addExpensses(
                            current,
                            (etTotalexpensee.text.toString()).toDouble(),
                            etStatment.text.toString()
                        )

                    }else{

                        updateExpensses(
                            id,
                            current,
                            (etTotalexpensee.text.toString()).toDouble(),
                            etStatment.text.toString()
                        )

                    }

                }

            }
        }
    }

    fun addExpensses(
        date: String,
        expenses: Double,
        statatment: String
    ) {
        try {
            val ref = FirebaseDatabase.getInstance().getReference("expenses")
            val userId = ref.push().key
            val expencess = ExpencessPojo(userId, date, expenses, statatment)
            ref.child(userId.toString()).setValue(expencess).addOnCompleteListener() {
                // update income

                messageAlerts.displayToast(mContext, "تم الحفظ")
                val i = Intent(this, Expenses::class.java)
                startActivity(i)
                finish()
            }

        } catch (e: Exception) {
            Log.d("tag", e.toString())
        }
    }

    fun updateExpensses(
        userId: String,
        date: String,
        expenses: Double,
        statatment: String
    ) {
        try {
            val ref = FirebaseDatabase.getInstance().getReference("expenses")
            val expencess = ExpencessPojo(userId, date, expenses, statatment)
            ref.child(userId.toString()).setValue(expencess).addOnCompleteListener() {
                // update income

                messageAlerts.displayToast(mContext, "تم الحفظ")
                val i = Intent(this, Expenses::class.java)
                startActivity(i)
                finish()
            }

        } catch (e: Exception) {
            Log.d("tag", e.toString())
        }
    }


    fun handle_Edit() {
        btnSaveExpenses.setText("حفظ")
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
        val query = myRef.child("expenses")
        query.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                try {
                    if (dataSnapshot.exists()) {

                        for (issue in dataSnapshot.children) {
                            val note = issue.getValue<ExpencessPojo>(ExpencessPojo::class.java)
                            ExpencesstArray.add(note)
                        }
                    }
                    for (i in ExpencesstArray) {

                        if (i?.userId.equals(id)) {

                            btnSaveExpenses.setText("تعديل")
                            etStatment.setText(i?.statatment)
                            etTotalexpensee.setText(i?.expenses.toString())

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
