package com.example.musicplayer.player

import android.content.Context
import android.net.Uri
import com.example.musicplayer.R
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PlayerImpl @Inject constructor(
    private val context: Context,
    private val exoPlayer: ExoPlayer
) : MediaPlayer {


    override fun play(url: String) {
        val userAgent = Util.getUserAgent(context, context.getString(R.string.app_name))
        val mediaSource = ProgressiveMediaSource
            .Factory(DefaultDataSourceFactory(context, userAgent))
            .createMediaSource(MediaItem.fromUri(Uri.parse(url)))
        exoPlayer.setMediaSource(mediaSource)
        exoPlayer.prepare()
        exoPlayer.playWhenReady = true
    }

    override fun releasePlayer() {
        exoPlayer.stop()
        exoPlayer.release()
    }

    override fun pauseOrPlay() {
        if (exoPlayer.isPlaying) {
            exoPlayer.pause()
        } else {
            exoPlayer.play()
        }
    }

}