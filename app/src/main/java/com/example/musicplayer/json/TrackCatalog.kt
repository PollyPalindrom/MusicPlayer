package com.example.musicplayer.json

import android.support.v4.media.MediaBrowserCompat
import android.support.v4.media.MediaMetadataCompat
import com.example.musicplayer.recycler.MusicItem
import com.example.musicplayer.service.Song
import com.google.android.exoplayer2.source.ConcatenatingMediaSource

interface TrackCatalog {

    fun getSongsList():List<MediaMetadataCompat>
    fun getSpecialSongsList():List<Song>
    fun asMediaSource(): ConcatenatingMediaSource
    fun getSpecialCatalog()
    fun getTrackCatalog(): List<MusicItem>
    fun createMediaItems(): List<MediaBrowserCompat.MediaItem>
}