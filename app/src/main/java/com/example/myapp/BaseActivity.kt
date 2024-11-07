package com.example.myapp

import android.content.Context
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.res.ResourcesCompat

open class BaseActivity : AppCompatActivity() {

    override fun attachBaseContext(newBase: Context) {
        val sharedPref = newBase.getSharedPreferences("SettingsPrefs", Context.MODE_PRIVATE)
        val language = sharedPref.getString("app_language", "en") ?: "en"
        val context = LocaleHelper.setLocale(newBase, language)
        super.attachBaseContext(context)
    }

    override fun onResume() {
        super.onResume()
        applyFontBasedOnLocale()
    }

    fun applyFontBasedOnLocale() {
        val sharedPref = getSharedPreferences("SettingsPrefs", Context.MODE_PRIVATE)
        val language = sharedPref.getString("app_language", "en") ?: "en"

        // Определяем шрифт в зависимости от языка
        val fontRes = if (language == "ru") R.font.russo_one else R.font.audiowide
        val customTypeface = ResourcesCompat.getFont(this, fontRes)

        if (customTypeface == null) {
            Log.e("BaseActivity", "Font not found for language: $language")
            return
        }

        // Логируем, чтобы убедиться, что шрифт загружается
        Log.i("BaseActivity", "Applying font for language: $language")

        // Применяем шрифт к нужным элементам
        findViewById<TextView>(R.id.Text_MUSIC)?.typeface = customTypeface
        findViewById<AppCompatButton>(R.id.Button_Back)?.typeface = customTypeface
        findViewById<AppCompatButton>(R.id.Button_Start)?.typeface = customTypeface
        findViewById<AppCompatButton>(R.id.Button_Settings)?.typeface = customTypeface
        findViewById<AppCompatButton>(R.id.Button_Exit)?.typeface = customTypeface
    }
}


