package com.example.musicplayer.recycler

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MusicItem(
    val title: String,
    val artist: String,
    val bitmapUri: String,
    val trackUri: String,
    val duration: Long
)