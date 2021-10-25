package com.example.musicplayer.di

import android.app.Application
import android.content.Context
import android.content.res.AssetManager
import com.example.musicplayer.connector.MusicConnector
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule {

    @Provides
    fun provideAppContext(app: Application): Context = app.applicationContext

    @Provides
    fun provideAssetManager(app: Application): AssetManager = app.assets

    @Provides
    @Singleton
    fun provideMusicConnector(context: Context) = MusicConnector(context)
}