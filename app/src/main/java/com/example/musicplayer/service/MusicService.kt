package com.example.musicplayer.service

import android.app.PendingIntent
import android.os.Bundle
import android.support.v4.media.MediaBrowserCompat
import android.support.v4.media.session.MediaSessionCompat
import androidx.media.MediaBrowserServiceCompat
import com.example.musicplayer.callback.MusicPlayerListener
import com.example.musicplayer.json.TrackCatalog
import com.example.musicplayer.player.MusicNotificationManager
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.ext.mediasession.MediaSessionConnector
import javax.inject.Inject

private const val SERVICE = "MusicService"

class MusicService : MediaBrowserServiceCompat() {
    @Inject
    lateinit var trackCatalog: TrackCatalog

    @Inject
    lateinit var exoPlayer: ExoPlayer
    private lateinit var musicNotificationManager: MusicNotificationManager
    var isForeground = false
    private lateinit var mediaSession: MediaSessionCompat
    private lateinit var mediaSessionConnector: MediaSessionConnector

    override fun onCreate() {
        super.onCreate()
        val activityIntent = packageManager.getLaunchIntentForPackage(packageName)?.let {
            PendingIntent.getActivity(this, 0, it, 0)
        }
        mediaSession = MediaSessionCompat(this, SERVICE).apply {
            setSessionActivity(activityIntent)
            isActive = true
        }
        sessionToken = mediaSession.sessionToken
        musicNotificationManager = MusicNotificationManager(this,mediaSession.sessionToken,MusicPlayerListener(this)){

        }
        mediaSessionConnector = MediaSessionConnector(mediaSession)
        mediaSessionConnector.setPlayer(exoPlayer)
    }

    override fun onGetRoot(
        clientPackageName: String,
        clientUid: Int,
        rootHints: Bundle?
    ): BrowserRoot? {
        TODO("Not yet implemented")
    }

    override fun onLoadChildren(
        parentId: String,
        result: Result<MutableList<MediaBrowserCompat.MediaItem>>
    ) {
        TODO("Not yet implemented")
    }
}