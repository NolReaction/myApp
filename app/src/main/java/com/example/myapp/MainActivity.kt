package com.example.myapp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val settingsButton = findViewById<AppCompatButton>(R.id.Button_Settings)
        settingsButton.setOnClickListener {
            Log.i("MainActivity", "Clicked settingsButton: Go to settings")
            val intent = Intent(this, SettingsActivity::class.java)
            startActivity(intent)
        }

        val exitButton = findViewById<Button>(R.id.Button_Exit)
        exitButton.setOnClickListener {
            Log.i("MainActivity", "Clicked exitButton: Stop program")

            // Получаем состояние кнопки Music из SharedPreferences
            val sharedPref = getSharedPreferences("SettingsPrefs", Context.MODE_PRIVATE)
            val isMusicOn = sharedPref.getBoolean("isMusicOn", true) // true по умолчанию

            // Выводим состояние музыки в лог
            val musicState = if (isMusicOn) "On" else "Off"
            Log.i("SettingsActivity", "Music state = $musicState")

            finishAndRemoveTask()
        }



    }
}