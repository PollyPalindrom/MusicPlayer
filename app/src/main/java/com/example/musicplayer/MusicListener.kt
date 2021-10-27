package com.example.musicplayer

import com.example.musicplayer.service.Song

interface MusicListener {
    fun playOrToggle(song: Song)
    fun createFullScreenFragment()
}