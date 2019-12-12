package omel.polymogo.nazieh.activities

import android.bluetooth.le.AdvertiseSettings
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import omel.polymogo.nazieh.R
import omel.polymogo.nazieh.helpers.KeyValueBD
import omel.polymogo.nazieh.helpers.MessageAlerts

class MainActivity : AppCompatActivity() , View.OnClickListener{
    var messageAlerts = MessageAlerts()
    val key = KeyValueBD()
    private lateinit var btncustomer: Button
    private lateinit var btnTrusuary: Button
    private lateinit var btnSettings: Button

    private lateinit var mContext:Context

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mContext = this
        btnSettings = findViewById(R.id.btnSetting)
        btnSettings.setOnClickListener(this)
        btnTrusuary = findViewById(R.id.btnTresury)
        btnTrusuary.setOnClickListener(this)
        btncustomer = findViewById(R.id.btnCustomers)
        btncustomer.setOnClickListener(this)
         handle_user()
//        btnSettings.setVisibility(View.GONE)

    }

    override fun onClick(v: View?) {

        if (v != null) {
            when(v.getId()){
                R.id.btnCustomers ->{
                    val i = Intent(this, Customer::class.java)
                    startActivity(i)
                }
                R.id.btnTresury ->{
                    val i = Intent(this, Tresury::class.java)
                    startActivity(i)

                }
                R.id.btnSetting ->{
                    val i = Intent(this, Setting::class.java)
                    startActivity(i)

                }
            }
        }

    }

    fun handle_user(){
        var userKey = key.getUsername(mContext)
        messageAlerts.displayToast(mContext,"kk"+userKey)
        if (userKey=="123456"){
            btnSettings.setVisibility(View.GONE)
        }
    }

}
