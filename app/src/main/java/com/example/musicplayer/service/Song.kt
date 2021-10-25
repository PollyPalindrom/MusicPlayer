package com.example.musicplayer.service

data class Song(
    val id: String,
    val title: String,
    val artist: String,
    val bitmapUri: String,
    val trackUri: String
)