package com.example.musicplayer.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.musicplayer.DownloadListener
import com.example.musicplayer.MusicListener
import com.example.musicplayer.databinding.MainFragmentBinding
import com.example.musicplayer.recycler.MusicAdapter
import java.net.URL

class MainFragment(private val listener: MusicListener) : Fragment() {

    companion object {
        fun newInstance(listener: MusicListener): MainFragment {
            return MainFragment(listener)
        }
    }

    private val musicAdapter: MusicAdapter = MusicAdapter(listener)
    private lateinit var binding: MainFragmentBinding
    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = MainFragmentBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        musicAdapter.submitList(listener.getTracks())
        binding.musicRecycler.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = musicAdapter
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener.release()
    }

}