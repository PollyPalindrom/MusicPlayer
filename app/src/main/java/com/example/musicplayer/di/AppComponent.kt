package com.example.musicplayer.di

import android.app.Application
import com.example.musicplayer.MainActivity
import com.example.musicplayer.service.MusicService
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Component(modules = [AppModule::class, AppBindsModule::class, ServiceModule::class])
@Singleton
interface AppComponent {

    fun inject(mainActivity: MainActivity)
    fun inject(musicService: MusicService)

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }
}