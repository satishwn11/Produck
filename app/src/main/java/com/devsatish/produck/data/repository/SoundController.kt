package com.devsatish.produck.data.repository

import android.content.Context
import android.media.MediaPlayer
import com.devsatish.produck.R

class SoundController(private val context: Context) {

    private var mediaPlayer: MediaPlayer? = null

    fun playBell() {
        if (mediaPlayer == null) {
            mediaPlayer = MediaPlayer.create(context, R.raw.bellsound)
        }
        mediaPlayer?.start()
    }

    fun stopBell() {
        mediaPlayer?.stop()
        mediaPlayer?.release()
        mediaPlayer = null
    }
}
