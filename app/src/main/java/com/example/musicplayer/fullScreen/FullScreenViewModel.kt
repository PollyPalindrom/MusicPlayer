package com.example.musicplayer.fullScreen

import android.support.v4.media.session.PlaybackStateCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.musicplayer.connector.MusicConnector
import com.example.musicplayer.player.currentPlaybackPosition
import com.example.musicplayer.service.MusicService
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

class FullScreenViewModel @Inject constructor(private val musicConnector: MusicConnector) :
    ViewModel() {
    private val playbackState = musicConnector.playbackState
    private val _duration = MutableLiveData<Long>()
    val duration: LiveData<Long> = _duration
    private val _position = MutableLiveData<Long>()
    val position: LiveData<Long> = _position

    init {
        updatePosition()
    }

    private fun updatePosition() {
        viewModelScope.launch {
            while (true) {
                val pos = playbackState.value?.currentPlaybackPosition
                if (_position.value != pos) {
                    _position.postValue(pos!!)
                    _duration.postValue(MusicService.currentDuration)
                }
                delay(100L)
            }
        }
    }
}