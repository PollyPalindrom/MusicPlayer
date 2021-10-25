package com.example.musicplayer.di

import android.app.Application
import com.example.musicplayer.MainActivity
import com.example.musicplayer.ViewModelFactory
import com.example.musicplayer.service.MusicService
import com.example.musicplayer.ui.main.MainFragment
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Component(modules = [AppModule::class, AppBindsModule::class, ServiceModule::class])
@Singleton
interface AppComponent {

    fun viewModelsFactory(): ViewModelFactory

    fun inject(mainFragment: MainFragment)
    fun inject(musicService: MusicService)

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }
}