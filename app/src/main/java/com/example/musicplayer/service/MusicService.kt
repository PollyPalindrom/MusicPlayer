package com.example.musicplayer.service

import android.app.PendingIntent
import android.content.Intent
import android.os.Bundle
import android.support.v4.media.MediaBrowserCompat
import android.support.v4.media.MediaDescriptionCompat
import android.support.v4.media.MediaMetadataCompat
import android.support.v4.media.session.MediaSessionCompat
import androidx.media.MediaBrowserServiceCompat
import com.example.musicplayer.appComponent
import com.example.musicplayer.callback.MusicPlaybackPreparer
import com.example.musicplayer.callback.MusicPlayerListener
import com.example.musicplayer.callback.PlayerEventListener
import com.example.musicplayer.json.DefaultTrackCatalog
import com.example.musicplayer.json.TrackCatalog
import com.example.musicplayer.player.MusicNotificationManager
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.ext.mediasession.MediaSessionConnector
import com.google.android.exoplayer2.ext.mediasession.TimelineQueueNavigator
import javax.inject.Inject

private const val SERVICE = "MusicService"
private const val ROOT_ID = "ROOT_ID"

class MusicService : MediaBrowserServiceCompat() {

    @Inject
    lateinit var defaultTrackCatalog: TrackCatalog

    @Inject
    lateinit var exoPlayer: ExoPlayer

    private lateinit var playerEventListener: PlayerEventListener
    private lateinit var musicNotificationManager: MusicNotificationManager
    var forground = false
    var playing: MediaMetadataCompat? = null
    private lateinit var mediaSession: MediaSessionCompat
    private lateinit var mediaSessionConnector: MediaSessionConnector
    private var initialized = false

    override fun onCreate() {
        super.onCreate()

        this.appComponent.inject(this)

        defaultTrackCatalog.getSpecialCatalog()
        val activityIntent = packageManager.getLaunchIntentForPackage(packageName)?.let {
            PendingIntent.getActivity(this, 0, it, 0)
        }
        mediaSession = MediaSessionCompat(this, SERVICE).apply {
            setSessionActivity(activityIntent)
            isActive = true
        }
        sessionToken = mediaSession.sessionToken
        musicNotificationManager =
            MusicNotificationManager(this, mediaSession.sessionToken, MusicPlayerListener(this)) {
                currentDuration = exoPlayer.duration
            }
        val musicPlaybackPreparer = MusicPlaybackPreparer(defaultTrackCatalog) {
            playing = it
            preparePLayer(defaultTrackCatalog.getSongsList(), it, true)
        }
        mediaSessionConnector = MediaSessionConnector(mediaSession)
        mediaSessionConnector.setPlaybackPreparer(musicPlaybackPreparer)
        mediaSessionConnector.setQueueNavigator(MusicNavigator())
        mediaSessionConnector.setPlayer(exoPlayer)
        playerEventListener = PlayerEventListener(this)
        exoPlayer.addListener(playerEventListener)
        musicNotificationManager.show(exoPlayer)
    }

    private fun preparePLayer(
        songs: List<MediaMetadataCompat>,
        itemToPlay: MediaMetadataCompat?,
        playNow: Boolean
    ) {
        val curSongIndex = if (playing == null) 0 else songs.indexOf(itemToPlay)
        exoPlayer.prepare(defaultTrackCatalog.asMediaSource())
        exoPlayer.seekTo(curSongIndex, 0L)
        exoPlayer.playWhenReady = playNow
    }

    override fun onGetRoot(
        clientPackageName: String,
        clientUid: Int,
        rootHints: Bundle?
    ): BrowserRoot? {
        return BrowserRoot(ROOT_ID, null)
    }

    override fun onLoadChildren(
        parentId: String,
        result: Result<MutableList<MediaBrowserCompat.MediaItem>>
    ) {
        when (parentId) {
            ROOT_ID -> {
                result.sendResult(defaultTrackCatalog.createMediaItems().toMutableList())
                if (!initialized && defaultTrackCatalog.getSongsList().isNotEmpty()) {
                    preparePLayer(
                        defaultTrackCatalog.getSongsList(),
                        defaultTrackCatalog.getSongsList()[0],
                        false
                    )
                    initialized = true
                }
            }
        }
    }

    override fun onTaskRemoved(rootIntent: Intent?) {
        super.onTaskRemoved(rootIntent)
        exoPlayer.stop()
    }

    override fun onDestroy() {
        super.onDestroy()
        exoPlayer.release()
        exoPlayer.removeListener(playerEventListener)
    }

    private inner class MusicNavigator : TimelineQueueNavigator(mediaSession) {
        override fun getMediaDescription(player: Player, windowIndex: Int): MediaDescriptionCompat {
            return defaultTrackCatalog.getSongsList()[windowIndex].description
        }

    }

    companion object {
        var currentDuration = 0L
            private set
    }
}