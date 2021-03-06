package com.example.musicplayer.json

import android.content.Context
import android.content.res.AssetManager
import android.net.Uri
import android.support.v4.media.MediaBrowserCompat
import android.support.v4.media.MediaBrowserCompat.MediaItem.FLAG_PLAYABLE
import android.support.v4.media.MediaDescriptionCompat
import android.support.v4.media.MediaMetadataCompat
import android.support.v4.media.MediaMetadataCompat.*
import androidx.core.net.toUri
import com.example.musicplayer.R
import com.example.musicplayer.recycler.MusicItem
import com.example.musicplayer.service.Song
import com.google.android.exoplayer2.source.ConcatenatingMediaSource
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DefaultTrackCatalog @Inject constructor(
    private val context: Context,
    private val assetManager: AssetManager
) : TrackCatalog {

    init {
        initCatalogFromJson()
    }

    var songs = emptyList<MediaMetadataCompat>()
    private var _catalog: List<MusicItem>? = null
    private val catalog: List<MusicItem> get() = requireNotNull(_catalog)


    private fun initCatalogFromJson() {
        val moshi = Moshi.Builder()
            .build()

        val arrayType = Types.newParameterizedType(List::class.java, MusicItem::class.java)
        val adapter: JsonAdapter<List<MusicItem>> = moshi.adapter(arrayType)

        val file = "playlist.json"

        val myJson = assetManager.open(file).bufferedReader().use { it.readText() }

        _catalog = adapter.fromJson(myJson)
    }

    override fun getTrackCatalog() = catalog

    override fun getSpecialCatalog() {
        songs = catalog.map { song ->
            MediaMetadataCompat.Builder().putString(METADATA_KEY_ARTIST, song.artist)
                .putString(METADATA_KEY_TITLE, song.title)
                .putString(METADATA_KEY_DISPLAY_TITLE, song.title)
                .putString(METADATA_KEY_DISPLAY_ICON_URI, song.bitmapUri)
                .putString(METADATA_KEY_MEDIA_URI, song.trackUri)
                .putString(METADATA_KEY_MEDIA_ID, song.title)
                .putString(METADATA_KEY_DISPLAY_SUBTITLE, song.artist)
                .putString(METADATA_KEY_DISPLAY_DESCRIPTION, song.artist)
                .build()
        }
    }

    override fun getSongsList(): List<MediaMetadataCompat> {
        return songs
    }

    override fun getSpecialSongsList(): List<Song> {
        return songs.map { Song(
            it.description.mediaId.toString(),
            it.description.title.toString(),
            it.description.subtitle.toString(),
            it.description.iconUri.toString(),
            it.description.mediaUri.toString()
        ) }
    }

    override fun asMediaSource(): ConcatenatingMediaSource {
        val concatenatingMediaSource = ConcatenatingMediaSource()
        songs.forEach { song ->

            val mediaSource =
                ProgressiveMediaSource.Factory(
                    DefaultDataSourceFactory(
                        context, Util.getUserAgent(
                            context, context.getString(
                                R.string.app_name
                            )
                        )
                    )
                )
                    .createMediaSource(
                        song.getString(MediaMetadataCompat.METADATA_KEY_MEDIA_URI).toUri()
                    )
            concatenatingMediaSource.addMediaSource(mediaSource)
        }
        return concatenatingMediaSource
    }

    override fun createMediaItems(): MutableList<MediaBrowserCompat.MediaItem> {
        val list = songs.map { song ->
            val desc = MediaDescriptionCompat.Builder()
                .setMediaUri(song.getString(METADATA_KEY_MEDIA_URI).toUri())
                .setTitle(song.description.title).setSubtitle(song.description.subtitle)
                .setMediaId(song.description.mediaId)
                .setIconUri(song.description.iconUri)
                .build()
            MediaBrowserCompat.MediaItem(desc, FLAG_PLAYABLE)
        }
        return list.toMutableList()
    }

}