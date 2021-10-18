package com.example.musicplayer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.musicplayer.json.TrackCatalog
import com.example.musicplayer.player.MediaPlayer
import com.example.musicplayer.recycler.MusicItem
import com.example.musicplayer.res.AppResources
import com.example.musicplayer.ui.main.MainFragment
import javax.inject.Inject

class MainActivity : AppCompatActivity(), MusicListener {

    @Inject
    lateinit var appResources: AppResources

    @Inject
    lateinit var trackCatalog: TrackCatalog

    @Inject
    lateinit var player: MediaPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.appComponent.inject(this)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, MainFragment.newInstance(this))
                .commitNow()
        }
    }

    override fun getTracks(): List<MusicItem> {
        return trackCatalog.getTrackCatalog()
    }

    override fun play(url: String) {
        player.play(url)
    }

    override fun release() {
        player.releasePlayer()
    }

}