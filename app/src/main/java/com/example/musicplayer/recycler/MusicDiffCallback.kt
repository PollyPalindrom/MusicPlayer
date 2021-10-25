package com.example.musicplayer.recycler

import androidx.recyclerview.widget.DiffUtil
import com.example.musicplayer.service.Song

class MusicDiffCallback : DiffUtil.ItemCallback<Song>() {
    override fun areItemsTheSame(oldItem: Song, newItem: Song): Boolean {
        return oldItem.title == newItem.title
    }

    override fun areContentsTheSame(oldItem: Song, newItem: Song): Boolean {
        return oldItem.artist == newItem.artist && oldItem.title == newItem.title
    }
}