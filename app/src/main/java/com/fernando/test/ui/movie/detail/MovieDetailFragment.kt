package com.fernando.test.ui.movie.detail

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.transition.ChangeBounds

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatDrawableManager
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.transition.TransitionInflater
import com.bumptech.glide.Glide
import com.fernando.test.BuildConfig
import com.fernando.test.R
import com.fernando.test.databinding.FragmentDetailBinding
import com.fernando.test.ui.movie.DividerItemDecoration
import com.fernando.test.ui.movie.list.MovieListFragmentDirections
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class MovieDetailFragment : Fragment() {

    private val viewModel: MovieDetailViewModel by viewModels()
    private val args: MovieDetailFragmentArgs by navArgs()
    private var binding: FragmentDetailBinding? = null
    private val adapter = GenresListAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (binding == null) {

            postponeEnterTransition(250, TimeUnit.MILLISECONDS)
            binding = FragmentDetailBinding.inflate(inflater, container, false)

            binding?.let { binding ->
                binding.movie = args.movie
                binding.contentImage.imageView2.transitionName = "postal_${args.movie.movieId}"
                if (args.movie.poster != null)
                    Glide.with(binding.root).load(BuildConfig.URL_IMAGE + args.movie.poster)
                        .into(binding.contentImage.imageView2)
                binding.contentDetail.button.setOnClickListener {
                    viewModel.getVideo()?.let {
                        findNavController().navigate(MovieDetailFragmentDirections.actionMovieDetailFragmentToMoviePlayerFragment(args.movie,it))
                    }
                }
                binding.appbar.setNavigationOnClickListener {
                    findNavController().popBackStack()
                }
                binding.contentDetail.genresRecyclerView.adapter = adapter
                val decoration = androidx.recyclerview.widget.DividerItemDecoration(requireContext(), DividerItemDecoration.HORIZONTAL)
                decoration.setDrawable(ColorDrawable(Color.WHITE));
                binding.contentDetail.genresRecyclerView.addItemDecoration(decoration)
            }
            sharedElementEnterTransition =
                androidx.transition.ChangeBounds()
            sharedElementReturnTransition =
                androidx.transition.ChangeBounds()
            startPostponedEnterTransition()


        }
        return binding?.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        postponeEnterTransition()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel.genres.observe(viewLifecycleOwner){
            it?.let {
                adapter.submitList(it)
            }
        }
        viewModel.videos.observe(viewLifecycleOwner) {
            it?.let {
                it.onSuccess {
                    lifecycleScope.launch(Dispatchers.Main){
                        binding?.contentDetail?.button?.text =
                            getText(R.string.button_see_trailer)
                        binding?.contentDetail?.button?.isEnabled = true
                    }
                    viewModel.setVideos(it)
                }.onFailure {
                    binding?.contentDetail?.button?.text =
                        getText(R.string.button_video_not_available)
                }
            }
        }
        lifecycleScope.launch(Dispatchers.IO) {
            viewModel.searchVideos(args.movie)
        }
        viewModel.loadGenres(args.movie.genres)
    }


}