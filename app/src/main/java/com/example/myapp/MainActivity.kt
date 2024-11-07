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

    // Объявляем musicServiceIntent как nullable
    private var musicServiceIntent: Intent? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Проверяем начальное состояние музыки из SharedPreferences
        updateMusicState()

        // Кнопка - Settings
        val settingsButton = findViewById<AppCompatButton>(R.id.Button_Settings)
        settingsButton.setOnClickListener {
            Log.i("MainActivity", "Clicked settingsButton: Go to settings")
            val intent = Intent(this, SettingsActivity::class.java)
            startActivity(intent)
        }

        // Кнопка - Exit
        val exitButton = findViewById<Button>(R.id.Button_Exit)
        exitButton.setOnClickListener {
            Log.i("MainActivity", "Clicked exitButton: Stop program")

            // Обновляем состояние музыки перед выходом
            val isMusicOn = updateMusicState()

            // Останавливаем сервис только если он инициализирован и музыка была включена
            if (isMusicOn) {
                musicServiceIntent?.let {
                    stopService(it)
                    Log.i("MainActivity", "Music service stopped")
                }
            }
            finishAndRemoveTask()
        }
    }

    private fun updateMusicState(): Boolean {
        val sharedPref = getSharedPreferences("SettingsPrefs", Context.MODE_PRIVATE)
        val isMusicOn = sharedPref.getBoolean("isMusicOn", true) // true по умолчанию

        if (isMusicOn) {
            // Инициализируем musicServiceIntent и запускаем музыку, если она включена
            if (musicServiceIntent == null) {
                musicServiceIntent = Intent(this, MusicService::class.java)
                startService(musicServiceIntent)
                Log.i("MainActivity", "Music started")
            }
            Log.i("MainActivity", "Music state = On")
        } else {
            Log.i("MainActivity", "Music state = Off")
        }

        return isMusicOn
    }
}
