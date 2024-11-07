package com.example.myapp

import android.animation.ArgbEvaluator
import android.animation.ObjectAnimator
import android.content.Context
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.transition.TransitionManager
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
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
        } else {
            musicButton.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(this, R.color.myRed))
            musicButton.text = getString(R.string.off)
        }

        // Обработчик клика для переключения состояния
        musicButton.setOnClickListener {
            if (isGreen) {
                Log.i("SettingsActivity", "Music: Off")
                musicButton.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(this, R.color.myRed))
                musicButton.text = getString(R.string.off)
            } else {
                Log.i("SettingsActivity", "Music: On")
                musicButton.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(this, R.color.myGreen))
                musicButton.text = getString(R.string.on)
            }
            isGreen = !isGreen
            Log.i("SettingsActivity", "Settings saved")
            saveSettings()
        }
    }


    private fun saveSettings() {
        val sharedPref = getSharedPreferences("SettingsPrefs", Context.MODE_PRIVATE)
        with(sharedPref.edit()) {
            putBoolean("isMusicOn", isGreen) // Сохраняем состояние кнопки
            apply()
        }
    }
}
