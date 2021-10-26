package com.example.musicplayer.ui.main

import android.os.Build
import android.os.Bundle
import android.support.v4.media.session.PlaybackStateCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.example.musicplayer.*
import com.example.musicplayer.databinding.MainFragmentBinding
import com.example.musicplayer.fullScreen.FullScreenFragment
import com.example.musicplayer.player.isPlaying
import com.example.musicplayer.recycler.MusicAdapter
import com.example.musicplayer.service.Song
import com.example.musicplayer.viewPager.SwipeAdapter

class MainFragment : Fragment(), MusicListener {

    private lateinit var swipeAdapter: SwipeAdapter
    private val musicAdapter: MusicAdapter = MusicAdapter(this)
    private lateinit var binding: MainFragmentBinding
    private var currentSong: Song? = null
    private var playbackState: PlaybackStateCompat? = null
    private val viewModel: MainViewModel by viewModels {
        requireActivity().appComponent.viewModelsFactory()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = MainFragmentBinding.inflate(inflater)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        swipeAdapter = SwipeAdapter(this)
        binding.viewPager.adapter = swipeAdapter
        binding.musicRecycler.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = musicAdapter
        }
        viewModel.songs.observe(viewLifecycleOwner) {
            musicAdapter.submitList(it)
        }
        viewModel.songs.observe(viewLifecycleOwner) {
            swipeAdapter.submitList(it)
            swipe(currentSong ?: return@observe)
        }
        viewModel.playingSong.observe(viewLifecycleOwner) {
            if (it == null) {
                return@observe
            }
            currentSong = Song(
                it.description.mediaId.toString(),
                it.description.title.toString(),
                it.description.subtitle.toString(),
                it.description.iconUri.toString(),
                it.description.mediaUri.toString()
            )
            swipe(currentSong ?: return@observe)
        }
        viewModel.playbackState.observe(viewLifecycleOwner) {
            playbackState = it
            if (playbackState?.isPlaying == true) binding.playingOrNot.setImageResource(R.drawable.ic_baseline_pause_50)
            else binding.playingOrNot.setImageResource(R.drawable.ic_baseline_play_arrow_50)
        }
        binding.viewPager.registerOnPageChangeCallback(
            object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    if (playbackState?.isPlaying == true) {
                        viewModel.playOrToggle(swipeAdapter.currentList[position])
                    } else {
                        currentSong = swipeAdapter.currentList[position]
                    }
                }
            }
        )
        binding.playingOrNot.setOnClickListener {
            currentSong?.let {
                viewModel.playOrToggle(it, true)
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun playOrToggle(song: Song) {
        viewModel.playOrToggle(song)
    }

    override fun createFullScreenFragment() {
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.container, FullScreenFragment())
            .addToBackStack(null)
            .commit()
    }

    private fun swipe(song: Song) {
        val newIndex = swipeAdapter.getItemPosition(song)
        if (newIndex != -1) {
            binding.viewPager.currentItem = newIndex
        }
    }
}