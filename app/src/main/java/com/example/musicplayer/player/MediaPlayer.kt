package com.example.musicplayer.player

import com.google.android.exoplayer2.ExoPlayer

interface MediaPlayer {
    fun play(url: String)
    fun releasePlayer()
    fun pauseOrPlay()
}