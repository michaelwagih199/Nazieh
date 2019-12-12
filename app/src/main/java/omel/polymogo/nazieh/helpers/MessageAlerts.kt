package omel.polymogo.nazieh.helpers

import android.content.Context
import android.widget.Toast

class MessageAlerts {

    fun displayToast(context: Context, message: String) {
        val toast = Toast.makeText(
            context.applicationContext,
            message,
            Toast.LENGTH_LONG
        )
        toast.show()
    }
}