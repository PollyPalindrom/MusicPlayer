package com.example.musicplayer

import com.example.musicplayer.player.MediaPlayer
import com.example.musicplayer.recycler.MusicItem

interface MusicListener {
    fun getTracks(): List<MusicItem>
    fun play(url: String)
    fun release()
}