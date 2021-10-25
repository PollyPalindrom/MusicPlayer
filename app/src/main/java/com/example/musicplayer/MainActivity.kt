package com.example.musicplayer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.musicplayer.json.DefaultTrackCatalog
import com.example.musicplayer.json.TrackCatalog
import com.example.musicplayer.player.MediaPlayer
import com.example.musicplayer.recycler.MusicItem
import com.example.musicplayer.res.AppResources
import com.example.musicplayer.ui.main.MainFragment
import javax.inject.Inject

class MainActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, MainFragment())
                .commitNow()
        }
    }

}