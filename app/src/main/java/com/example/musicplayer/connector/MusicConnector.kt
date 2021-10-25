package com.example.musicplayer.connector

import android.content.ComponentName
import android.content.Context
import android.os.Build
import android.support.v4.media.MediaBrowserCompat
import android.support.v4.media.MediaMetadataCompat
import android.support.v4.media.session.MediaControllerCompat
import android.support.v4.media.session.PlaybackStateCompat
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.musicplayer.service.MusicService

class MusicConnector(context: Context) {
    private val _playbackState = MutableLiveData<PlaybackStateCompat?>()
    val playbackState: LiveData<PlaybackStateCompat?> = _playbackState
    private val _playingSong = MutableLiveData<MediaMetadataCompat?>()
    val playingSong: LiveData<MediaMetadataCompat?> = _playingSong

    lateinit var mediaController: MediaControllerCompat

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private val mediaBrowserConnectionCallback = MediaBrowserConnectionCallback(context)
    val transportControls
        get() = mediaController.transportControls

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private val mediaBrowser = MediaBrowserCompat(
        context,
        ComponentName(context, MusicService::class.java),
        mediaBrowserConnectionCallback,
        null
    ).apply { connect() }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    fun subscribe(parentId: String, callback: MediaBrowserCompat.SubscriptionCallback) {
        mediaBrowser.subscribe(parentId, callback)
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    fun unsubscribe(parentId: String, callback: MediaBrowserCompat.SubscriptionCallback) {
        mediaBrowser.unsubscribe(parentId, callback)
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private inner class MediaBrowserConnectionCallback(private val context: Context) :
        MediaBrowserCompat.ConnectionCallback() {
        override fun onConnected() {
            mediaController = MediaControllerCompat(context, mediaBrowser.sessionToken).apply {
                registerCallback(MediaControllerCallback())
            }

        }

    }

    private inner class MediaControllerCallback : MediaControllerCompat.Callback() {
        override fun onPlaybackStateChanged(state: PlaybackStateCompat?) {
            _playbackState.postValue(state)
        }

        override fun onMetadataChanged(metadata: MediaMetadataCompat?) {
            _playingSong.postValue(metadata)

        }
    }
}