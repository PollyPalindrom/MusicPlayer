package com.example.musicplayer.json

import android.content.res.AssetManager
import com.example.musicplayer.recycler.MusicItem
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DefaultTrackCatalog @Inject constructor(
    private val assetManager: AssetManager
) : TrackCatalog {

    init {
        initCatalogFromJson()
    }

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
}