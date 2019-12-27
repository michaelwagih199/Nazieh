package omel.polymogo.nazieh.activities

import android.content.Context
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.annotation.RequiresApi
import com.google.firebase.database.FirebaseDatabase
import omel.polymogo.nazieh.R
import omel.polymogo.nazieh.helpers.KeyValueBD
import omel.polymogo.nazieh.helpers.MessageAlerts
import omel.polymogo.nazieh.models.IncomeProfitPojo
import java.time.LocalDateTime

class EditProfit : AppCompatActivity(), View.OnClickListener {

    var messageAlerts = MessageAlerts()
    val key = KeyValueBD()
    private lateinit var etCustomerProfitName: EditText
    private lateinit var etTotalIncomeProfit: EditText
    private lateinit var etNotes: EditText
    private lateinit var btnAddProfit: Button
    private lateinit var mContext: Context


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profit)
        mContext = this
        etCustomerProfitName = findViewById(R.id.etcustomerNameProfit)
        etNotes = findViewById(R.id.etNotes)
        etTotalIncomeProfit = findViewById(R.id.etTotalIncomeProfit)
        btnAddProfit = findViewById(R.id.btnSaveProfit)
        btnAddProfit.setOnClickListener(this)
    }


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onClick(v: View?) {
        if (v != null) {
            when (v.getId()) {

                R.id.btnSaveProfit -> {
                    val current = LocalDateTime.now().toString()
                    addProfit(
                        current,
                        (etTotalIncomeProfit.text.toString()).toDouble(),
                        etNotes.text.toString(),
                        etCustomerProfitName.text.toString()
                    )

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
            val income = IncomeProfitPojo(userId, date, totalIncome, notes, customerName)
            ref.child(userId.toString()).setValue(income).addOnCompleteListener() {
                messageAlerts.displayToast(mContext, "تم الحفظ")
            }

        } catch (e: Exception) {
            Log.d("tag", e.toString())
        }
    }

}
