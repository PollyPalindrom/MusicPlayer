package com.example.musicplayer.di

import com.example.musicplayer.json.DefaultTrackCatalog
import com.example.musicplayer.json.TrackCatalog
import com.example.musicplayer.player.MediaPlayer
import com.example.musicplayer.player.PlayerImpl
import com.example.musicplayer.res.AppResources
import com.example.musicplayer.res.AppResourcesImpl
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
interface AppBindsModule {

    @Binds
    fun bindAppResources(appResources: AppResourcesImpl): AppResources

    @Binds
    @Singleton
    fun bindDefaultTrackCatalog(defaultTrackCatalog: DefaultTrackCatalog): TrackCatalog

    @Binds
    @Singleton
    fun bindPlayer(player: PlayerImpl): MediaPlayer
}