package com.example.musicplayer.fullScreen

import android.os.Build
import android.os.Bundle
import android.support.v4.media.session.PlaybackStateCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.musicplayer.MainListener
import com.example.musicplayer.R
import com.example.musicplayer.appComponent
import com.example.musicplayer.databinding.FullScreenFragmentBinding
import com.example.musicplayer.player.isPlaying
import com.example.musicplayer.service.Song
import com.example.musicplayer.ui.main.MainViewModel

class FullScreenFragment : Fragment() {


    private lateinit var binding: FullScreenFragmentBinding
    private val viewModel: FullScreenViewModel by viewModels {
        requireActivity().appComponent.viewModelsFactory()
    }
    private val mainViewModel: MainViewModel by viewModels {
        requireActivity().appComponent.viewModelsFactory()
    }

    private var currentSong: Song? = null

    private var playbackState: PlaybackStateCompat? = null
    private var update = true

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FullScreenFragmentBinding.inflate(inflater)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (currentSong == null) {
            currentSong = mainViewModel.songs.value?.get(0)
            currentSong?.let { update(it) }
        }
        mainViewModel.playingSong.observe(viewLifecycleOwner) {
            if (it == null) return@observe
            currentSong = Song(
                it.description.mediaId.toString(),
                it.description.title.toString(),
                it.description.subtitle.toString(),
                it.description.iconUri.toString(),
                it.description.mediaUri.toString()
            )
            update(currentSong!!)
        }
        binding.stopOrPlay.setOnClickListener {
            currentSong?.let {
                mainViewModel.playOrToggle(it, true)
            }
        }
        binding.prevSong.setOnClickListener {
            mainViewModel.skipToPrevSong()
        }
        binding.nextSong.setOnClickListener {
            mainViewModel.skipToNextSong()
        }
        mainViewModel.playbackState.observe(viewLifecycleOwner) {
            playbackState = it
            if (playbackState?.isPlaying == true) binding.stopOrPlay.setImageResource(R.drawable.ic_baseline_pause_50)
            else binding.stopOrPlay.setImageResource(R.drawable.ic_baseline_play_arrow_50)
            binding.seekBar.progress = it?.position?.toInt() ?: 0
        }
        viewModel.position.observe(viewLifecycleOwner) {
            if (update) {
                binding.seekBar.progress = it.toInt()
            }
        }
        viewModel.duration.observe(viewLifecycleOwner) {
            binding.seekBar.max = it.toInt()
        }
        binding.seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                update = false
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                seekBar?.let {
                    mainViewModel.seekTo(it.progress.toLong())
                }
                update = true
            }
        })
    }

    private fun update(song: Song) {
        Glide.with(binding.image.context)
            .load(song.bitmapUri)
            .centerCrop()
            .placeholder(R.drawable.ic_baseline_music_note_24)
            .into(binding.image)
        binding.name.text = song.title
        binding.performer.text = song.artist
    }
}