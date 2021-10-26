package com.example.musicplayer

import com.example.musicplayer.player.MediaPlayer
import com.example.musicplayer.recycler.MusicItem
import com.example.musicplayer.service.Song

interface MusicListener {
    fun playOrToggle(song: Song)
    fun createFullScreenFragment()
}