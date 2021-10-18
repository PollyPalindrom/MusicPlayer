package com.example.musicplayer.json

import android.support.v4.media.MediaBrowserCompat
import com.example.musicplayer.recycler.MusicItem
import com.google.android.exoplayer2.source.ConcatenatingMediaSource

interface TrackCatalog {

    fun getTrackCatalog(): List<MusicItem>
    fun createMediaItems(): List<MediaBrowserCompat.MediaItem>
}