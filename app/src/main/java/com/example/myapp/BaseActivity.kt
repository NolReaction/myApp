package com.example.myapp

import android.content.Context
import androidx.appcompat.app.AppCompatActivity

open class BaseActivity : AppCompatActivity() {

    override fun attachBaseContext(newBase: Context) {
        val sharedPref = newBase.getSharedPreferences("SettingsPrefs", Context.MODE_PRIVATE)
        val language = sharedPref.getString("app_language", "en") ?: "en"
        val context = LocaleHelper.setLocale(newBase, language)
        super.attachBaseContext(context)
    }
}
