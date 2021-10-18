package com.example.musicplayer.recycler

import androidx.recyclerview.widget.DiffUtil

class MusicDiffCallback : DiffUtil.ItemCallback<MusicItem>() {
    override fun areItemsTheSame(oldItem: MusicItem, newItem: MusicItem): Boolean {
        return oldItem.title == newItem.title
    }

    override fun areContentsTheSame(oldItem: MusicItem, newItem: MusicItem): Boolean {
        return oldItem.artist == newItem.artist && oldItem.title == newItem.title && oldItem.duration == newItem.duration
    }
}