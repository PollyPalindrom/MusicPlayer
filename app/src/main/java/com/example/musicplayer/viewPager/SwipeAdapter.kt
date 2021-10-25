package com.example.musicplayer.viewPager

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.musicplayer.databinding.SwipeItemBinding
import com.example.musicplayer.recycler.MusicDiffCallback
import com.example.musicplayer.service.Song

class SwipeAdapter() :
    ListAdapter<Song, SwipeViewHolder>(MusicDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SwipeViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = SwipeItemBinding.inflate(layoutInflater, parent, false)
        return SwipeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SwipeViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    fun getItemPosition(song: Song): Int {
        return currentList.indexOf(song)
    }
}