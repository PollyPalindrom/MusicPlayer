package com.example.musicplayer.di

import android.app.Application
import android.content.Context
import android.content.res.AssetManager
import dagger.Module
import dagger.Provides

@Module
class AppModule {

    @Provides
    fun provideAppContext(app: Application): Context = app.applicationContext

    @Provides
    fun provideAssetManager(app: Application): AssetManager = app.assets

}