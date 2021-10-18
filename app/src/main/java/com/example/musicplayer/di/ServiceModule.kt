package com.example.musicplayer.di

import android.content.Context
import com.google.android.exoplayer2.C
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.audio.AudioAttributes
import dagger.Module
import dagger.Provides

@Module
class ServiceModule {
    @Provides
    fun provideAudioAttributes(): AudioAttributes =
        AudioAttributes.Builder().setContentType(C.CONTENT_TYPE_MUSIC).setUsage(C.USAGE_MEDIA)
            .build()

    @Provides
    fun provideExoPlayer(context: Context, audioAttributes: AudioAttributes): ExoPlayer =
        SimpleExoPlayer.Builder(context).build().apply {
            setAudioAttributes(audioAttributes, true)
            setHandleAudioBecomingNoisy(true)
        }
}