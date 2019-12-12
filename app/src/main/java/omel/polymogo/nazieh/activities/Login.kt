package omel.polymogo.nazieh.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import omel.polymogo.nazieh.R
import omel.polymogo.nazieh.helpers.KeyValueBD
import omel.polymogo.nazieh.helpers.MessageAlerts

 class Login : AppCompatActivity(), View.OnClickListener{
     var messageAlerts = MessageAlerts()
     val key =KeyValueBD()
     private lateinit var  mContext: Context
     private lateinit var etPassword: EditText
     private lateinit var btnLogin: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        etPassword = findViewById(R.id.etPassword)as EditText
        btnLogin = findViewById(R.id.btnLogin) as Button
        btnLogin.setOnClickListener(this);
        mContext=this
        key.setUsername(mContext,"")

    }

    override fun onClick(v: View?) {
        if (v != null) {
            when (v.getId()) {
                R.id.btnLogin -> btnLogin(etPassword.getText().toString())
            }
        }

    }

    fun btnLogin(password : String){

        if (etPassword.text.toString().equals("admin")) {
            key.setUsername(mContext,"admin")
            val i = Intent(this, MainActivity::class.java)
            startActivity(i)
            finish()
        } else if(etPassword.text.toString().equals("123456")) {
            key.setUsername(mContext,"123456")
            val i = Intent(this, MainActivity::class.java)
            startActivity(i)
            finish()
        }else
            messageAlerts.displayToast(mContext, "password wrong")
    }

}
