package com.example.musicplayer

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.musicplayer.fullScreen.FullScreenViewModel
import com.example.musicplayer.ui.main.MainViewModel
import javax.inject.Inject
import javax.inject.Provider

class ViewModelFactory @Inject constructor(
    viewMainModelProvider: Provider<MainViewModel>,
    viewFullScreenModelProvider: Provider<FullScreenViewModel>
) :
    ViewModelProvider.Factory {
    private val providers =
        mapOf<Class<*>, Provider<out ViewModel>>(
            MainViewModel::class.java to viewMainModelProvider,
            FullScreenViewModel::class.java to viewFullScreenModelProvider
        )

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return providers[modelClass]!!.get() as T
    }

}