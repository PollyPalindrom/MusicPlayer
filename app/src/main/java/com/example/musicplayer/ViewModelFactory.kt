package com.example.musicplayer

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.musicplayer.ui.main.MainViewModel
import javax.inject.Inject
import javax.inject.Provider

class ViewModelFactory @Inject constructor(viewModelProvider: Provider<MainViewModel>) :
    ViewModelProvider.Factory {
    private val providers =
        mapOf<Class<*>, Provider<out ViewModel>>(MainViewModel::class.java to viewModelProvider)

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return providers[modelClass]!!.get() as T
    }

}