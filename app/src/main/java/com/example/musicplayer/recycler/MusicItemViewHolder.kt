package com.example.musicplayer.recycler

import androidx.lifecycle.LifecycleObserver
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.musicplayer.MusicListener
import com.example.musicplayer.R
import com.example.musicplayer.databinding.ItemBinding

class MusicItemViewHolder(
    private val binding: ItemBinding
) :
    RecyclerView.ViewHolder(binding.root), LifecycleObserver {
    fun bind(musicItem: MusicItem, listener: MusicListener) {
        binding.apply {
            binding.name.text = musicItem.title;
            binding.performer.text = musicItem.artist;
        }
        Glide.with(binding.image.context)
            .load(musicItem.bitmapUri)
            .centerCrop()
            .placeholder(R.drawable.ic_baseline_music_note_24)
            .into(binding.image)
        binding.root.setOnClickListener {
            listener.play(musicItem.trackUri)
        }
    }
}