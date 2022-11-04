package com.fernando.test.ui.movie.player

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.fernando.test.databinding.FragmentPlayerBinding
import com.fernando.test.databinding.FragmentStartBinding
import com.fernando.test.ui.movie.detail.MovieDetailFragmentArgs
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MoviePlayerFragment : Fragment() {

    private var binding: FragmentPlayerBinding? = null
    private val args: MoviePlayerFragmentArgs by navArgs()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (binding == null) {
            binding = FragmentPlayerBinding.inflate(inflater, container, false)
            binding?.youtubePlayer?.addYouTubePlayerListener(object : AbstractYouTubePlayerListener(){
                override fun onReady(youTubePlayer: YouTubePlayer) {
                    youTubePlayer.loadVideo(args.videoId,0f)
                }
            })
        }
        return binding?.root
    }
}