package omel.polymogo.nazieh.helpers

import android.content.Context
import android.content.SharedPreferences

class KeyValueBD {

    private val sharedPreferences: SharedPreferences? = null
    private val PREF_NAME = "prefs"

    private fun getPrefs(context: Context): SharedPreferences {
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    }

    fun getUsername(context: Context): String? {
        return getPrefs(context).getString("username_key", "admin")
    }

    fun setUsername(context: Context, input: String) {
        val editor = getPrefs(context).edit()
        editor.putString("username_key", input)
        editor.commit()
    }
}