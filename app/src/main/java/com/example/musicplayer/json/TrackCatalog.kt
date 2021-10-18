package com.example.musicplayer.json

import com.example.musicplayer.recycler.MusicItem

interface TrackCatalog {

    fun getTrackCatalog(): List<MusicItem>
}