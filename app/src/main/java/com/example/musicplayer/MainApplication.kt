package com.example.musicplayer

import android.app.Application
import android.content.Context
import androidx.multidex.MultiDex
import com.example.musicplayer.di.AppComponent
import com.example.musicplayer.di.DaggerAppComponent

class MainApplication : Application() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()

        appComponent = DaggerAppComponent.builder()
            .application(this)
            .build()
    }
    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }
}

val Context.appComponent: AppComponent
    get() = when (this) {
        is MainApplication -> this.appComponent
        else -> this.applicationContext.appComponent
    }