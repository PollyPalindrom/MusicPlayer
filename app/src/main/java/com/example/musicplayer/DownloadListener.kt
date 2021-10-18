package com.example.musicplayer

import android.widget.ImageView
import java.net.URL

interface DownloadListener {
    fun loadImage(url: URL,imageView: ImageView)
}