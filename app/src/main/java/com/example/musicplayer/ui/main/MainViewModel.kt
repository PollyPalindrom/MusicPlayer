package com.example.musicplayer.ui.main

import android.os.Build
import android.support.v4.media.MediaBrowserCompat
import android.support.v4.media.MediaMetadataCompat.METADATA_KEY_MEDIA_ID
import androidx.annotation.RequiresApi
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.musicplayer.connector.MusicConnector
import com.example.musicplayer.json.DefaultTrackCatalog
import com.example.musicplayer.player.isPlayEnabled
import com.example.musicplayer.player.isPlaying
import com.example.musicplayer.player.isPrepared
import com.example.musicplayer.service.Song
import javax.inject.Inject

private const val ROOT_ID = "ROOT_ID"

@RequiresApi(Build.VERSION_CODES.LOLLIPOP)
class MainViewModel @Inject constructor(
    private val musicConnector: MusicConnector
) : ViewModel() {

    val songs = MutableLiveData<List<Song>>()
    val playingSong = musicConnector.playingSong
    val playbackState = musicConnector.playbackState

    init {
        musicConnector.subscribe(ROOT_ID, object : MediaBrowserCompat.SubscriptionCallback() {
            override fun onChildrenLoaded(
                parentId: String,
                children: MutableList<MediaBrowserCompat.MediaItem>
            ) {
                super.onChildrenLoaded(parentId, children)
                val items = children.map {
                    Song(
                        it.mediaId!!,
                        it.description.title.toString(),
                        it.description.subtitle.toString(),
                        it.description.iconUri.toString(),
                        it.description.mediaUri.toString()
                    )
                }
                songs.postValue(items)
                println(items.size)
            }
        })

    }

    fun skipToNextSong() {
        musicConnector.transportControls.skipToNext()
    }

    fun skipToPrevSong() {
        musicConnector.transportControls.skipToPrevious()
    }

    fun seekTo(pos: Long) {
        musicConnector.transportControls.seekTo(pos)
    }

    fun playOrToggle(song: Song, toggle: Boolean = false) {
        val isPrepeared = playbackState.value?.isPrepared ?: false
        if (isPrepeared && song.id == playingSong.value?.getString(METADATA_KEY_MEDIA_ID)) {
            playbackState.value?.let { playbackState ->
                when {
                    playbackState.isPlaying -> if (toggle) musicConnector.transportControls.pause()
                    playbackState.isPlayEnabled -> musicConnector.transportControls.play()
                    else -> Unit
                }
            }
        } else {
            musicConnector.transportControls.playFromMediaId(song.id, null)
        }
    }

    override fun onCleared() {
        super.onCleared()
        musicConnector.unsubscribe(ROOT_ID, object : MediaBrowserCompat.SubscriptionCallback() {})
    }
}