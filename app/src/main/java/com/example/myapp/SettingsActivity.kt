package com.example.myapp

import android.content.Context
import android.content.Intent
import android.content.res.ColorStateList
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class SettingsActivity : AppCompatActivity() {

    private var isGreen = true // Флаг для состояния

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_settings)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.constraintLayout)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val backButton = findViewById<AppCompatButton>(R.id.Button_Back)
        backButton.setOnClickListener {
            Log.i("SettingsActivity", "Clicked backButton: Finish SettingsActivity")
            finish()
        }

        val musicButton = findViewById<Button>(R.id.Button_Music)

        // Восстанавливаем состояние из SharedPreferences
        val sharedPref = getSharedPreferences("SettingsPrefs", Context.MODE_PRIVATE)
        isGreen = sharedPref.getBoolean("isMusicOn", true) // По умолчанию музыка включена (зелёный цвет)

        // Устанавливаем цвет и текст кнопки в зависимости от состояния
        if (isGreen) {
            musicButton.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(this, R.color.myGreen))
            musicButton.text = getString(R.string.on)
            startMusicService() // Запускаем музыку при открытии, если включена
        } else {
            musicButton.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(this, R.color.myRed))
            musicButton.text = getString(R.string.off)
            stopMusicService() // Останавливаем музыку, если выключена
        }

        // Обработчик клика для переключения состояния
        musicButton.setOnClickListener {
            if (isGreen) {
                Log.i("SettingsActivity", "Music: Off")
                musicButton.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(this, R.color.myRed))
                musicButton.text = getString(R.string.off)
                stopMusicService()
            } else {
                Log.i("SettingsActivity", "Music: On")
                musicButton.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(this, R.color.myGreen))
                musicButton.text = getString(R.string.on)
                startMusicService()
            }
            isGreen = !isGreen
            Log.i("SettingsActivity", "Settings saved")
            saveSettings()
        }
    }

    private fun startMusicService() {
        val musicServiceIntent = Intent(this, MusicService::class.java)
        startService(musicServiceIntent)
    }

    private fun stopMusicService() {
        val musicServiceIntent = Intent(this, MusicService::class.java)
        stopService(musicServiceIntent)
    }

    private fun saveSettings() {
        val sharedPref = getSharedPreferences("SettingsPrefs", Context.MODE_PRIVATE)
        with(sharedPref.edit()) {
            putBoolean("isMusicOn", isGreen) // Сохраняем состояние кнопки
            apply()
        }
    }
}
