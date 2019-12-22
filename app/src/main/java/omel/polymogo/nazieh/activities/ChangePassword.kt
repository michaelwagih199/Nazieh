package omel.polymogo.nazieh.activities

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import omel.polymogo.nazieh.R
import omel.polymogo.nazieh.helpers.MessageAlerts

class ChangePassword : AppCompatActivity(),View.OnClickListener {
    var messageAlerts = MessageAlerts()
    private lateinit var  mContext: Context
    private lateinit var etOldPassword: EditText
    private lateinit var etNewPassword:EditText
    private lateinit var etConfirmPassword:EditText
    private lateinit var btnSaveNewPassword: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_password)
        etOldPassword = findViewById(R.id.etOldPassword)
        etNewPassword = findViewById(R.id.etNewPassword)
        etConfirmPassword = findViewById(R.id.etNewPassword)
        mContext = this
        btnSaveNewPassword = findViewById(R.id.btnSavePassword)

    }

    override fun onClick(v: View?) {

    }
}
