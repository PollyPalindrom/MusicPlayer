package com.example.musicplayer.viewPager

import androidx.lifecycle.LifecycleObserver
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.musicplayer.R
import com.example.musicplayer.databinding.SwipeItemBinding
import com.example.musicplayer.service.Song

class SwipeViewHolder(
    private val binding: SwipeItemBinding
) :
    RecyclerView.ViewHolder(binding.root), LifecycleObserver {
    fun bind(musicItem: Song) {
        binding.apply {
            binding.name.text = musicItem.title;
            binding.performer.text = musicItem.artist;
        }
        Glide.with(binding.image.context)
            .load(musicItem.bitmapUri)
            .centerCrop()
            .placeholder(R.drawable.ic_baseline_music_note_24)
            .into(binding.image)
    }
}