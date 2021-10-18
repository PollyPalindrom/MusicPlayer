package com.example.musicplayer.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.musicplayer.MusicListener
import com.example.musicplayer.databinding.ItemBinding

class MusicAdapter(private val listener:MusicListener) : ListAdapter<MusicItem, MusicItemViewHolder>(MusicDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MusicItemViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemBinding.inflate(layoutInflater, parent, false)
        return MusicItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MusicItemViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it, listener) }
    }

}