package com.example.myapp

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.IBinder
import android.util.Log

class MusicService : Service() {

    private lateinit var mediaPlayer: MediaPlayer

    // Список треков
    private val musicList = listOf(
        R.raw.track1 to "track1",
        R.raw.track2 to "track2",
        R.raw.track3 to "track3",
        R.raw.track4 to "track4",
        R.raw.track5 to "track5",
        R.raw.track6 to "track6"
    )

    override fun onCreate() {
        super.onCreate()
        Log.i("MusicService", "Service created")

        playRandomTrack()
    }

    private fun playRandomTrack() {
        if (::mediaPlayer.isInitialized) {
            mediaPlayer.release()
        }

        // Выбираем случайный трек
        val (trackResource, trackName) = musicList.random()

        // Создаём и запускаем MediaPlayer
        mediaPlayer = MediaPlayer.create(this, trackResource)
        mediaPlayer.isLooping = true
        mediaPlayer.start()

        // Логируем название трека
        Log.i("MusicService", "Playing track: $trackName")
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onDestroy() {
        super.onDestroy()
        if (::mediaPlayer.isInitialized) {
            mediaPlayer.stop()
            mediaPlayer.release()
            Log.i("MusicService", "MediaPlayer stopped and released")
        }
    }
}
