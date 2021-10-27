package com.example.musicplayer.fullScreen

import android.support.v4.media.MediaMetadataCompat
import android.support.v4.media.session.PlaybackStateCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.musicplayer.connector.MusicConnector
import com.example.musicplayer.player.currentPlaybackPosition
import com.example.musicplayer.player.isPlayEnabled
import com.example.musicplayer.player.isPlaying
import com.example.musicplayer.player.isPrepared
import com.example.musicplayer.service.MusicService
import com.example.musicplayer.service.Song
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

class FullScreenViewModel @Inject constructor(private val musicConnector: MusicConnector) :
    ViewModel() {
    val curPlayingSong = musicConnector.playingSong
    val playbackState = musicConnector.playbackState
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
    fun skipToNextSong() {
        musicConnector.transportControls.skipToNext()
    }

    fun skipToPreviousSong() {
        musicConnector.transportControls.skipToPrevious()
    }

    fun playOrToggle(song: Song, toggle: Boolean = false) {
        val isPrepared = playbackState.value?.isPrepared ?: false
        if(isPrepared && song.title ==
            curPlayingSong.value?.getString(MediaMetadataCompat.METADATA_KEY_MEDIA_ID)) {
            playbackState.value?.let { playbackState ->
                when {
                    playbackState.isPlaying -> if(toggle) musicConnector.transportControls.pause()
                    playbackState.isPlayEnabled -> musicConnector.transportControls.play()
                    else -> Unit
                }
            }
        } else {
            musicConnector.transportControls.playFromMediaId(song.title, null)
        }
    }
}