package com.fernando.test.ui.movie.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.coroutineScope
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.paging.PagingData
import androidx.transition.ChangeBounds
import com.fernando.test.R
import com.fernando.test.databinding.FragmentStartBinding
import com.fernando.test.domain.Movie
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@AndroidEntryPoint
class MovieListFragment : Fragment() {


    private val viewModel: MovieListViewModel by viewModels()

    private var binding: FragmentStartBinding? = null
    private val moviesUpcomingAdapter = MovieUpcomingAdapter(MovieListCallback())
    private val moviesTopRateAdapter = MovieUpcomingAdapter(MovieListCallback())
    private val moviesAdapter = MovieUpcomingAdapter(MovieListCallback())

    private val onItemMovieClickListener = object : MovieUpcomingAdapter.ItemMovieClickListener {
        override fun onItemMovieClick(movie: Movie, view: ImageView) {
            val extras = FragmentNavigatorExtras(view to view.transitionName)
            findNavController().navigate(
                MovieListFragmentDirections.actionMovieListFragmentToMovieDetailFragment(
                    movie
                ), extras
            )
        }
    }

    init {
        moviesTopRateAdapter.listener = onItemMovieClickListener
        moviesUpcomingAdapter.listener = onItemMovieClickListener
        moviesAdapter.listener = onItemMovieClickListener
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        sharedElementReturnTransition =
            ChangeBounds()
        sharedElementEnterTransition =
            ChangeBounds()
        if (binding == null) {
            binding = FragmentStartBinding.inflate(inflater, container, false)
            binding?.let { binding ->
                binding.moviesUpcomingRecyclerView.adapter = moviesUpcomingAdapter
                binding.moviesUpcomingRecyclerView.apply {
                    postponeEnterTransition()
                    viewTreeObserver
                        .addOnPreDrawListener {
                            startPostponedEnterTransition()
                            true
                        }
                }
                binding.moviesTopRatedRecyclerView.apply {
                    adapter = moviesTopRateAdapter
                    postponeEnterTransition()
                    viewTreeObserver
                        .addOnPreDrawListener {
                            startPostponedEnterTransition()
                            true
                        }
                }
                binding.moviesRecyclerView.adapter = moviesAdapter
                binding.chipGroup.setOnCheckedStateChangeListener{ _, ids ->
                    if(ids.first() == R.id.englishChip){
                        lifecycle.coroutineScope.launch(Dispatchers.IO){
                            viewModel.getMoviesByLanguage("en").collect(){
                                moviesAdapter.submitData(PagingData.from(it))
                            }
                        }
                    }else{
                        lifecycle.coroutineScope.launch(Dispatchers.IO){
                            viewModel.getMoviesByYear("2020").collect(){
                                moviesAdapter.submitData(PagingData.from(it))
                            }
                        }
                    }
                }
            }
        }
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        registerFlow()
        postponeEnterTransition()
        view.doOnPreDraw { startPostponedEnterTransition() }

    }

    private fun registerFlow() {
        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.IO) {
            viewModel.moviesUpcomingFlowNetWork.collectLatest { pagingData ->
                moviesUpcomingAdapter.submitData(pagingData)
            }
        }
        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.IO) {
            viewModel.moviesTopRateFlowNetWork.collectLatest { pagingData ->
                moviesTopRateAdapter.submitData(pagingData)
            }
        }
        lifecycle.coroutineScope.launch(Dispatchers.IO){
            viewModel.getMoviesByLanguage("en").collect(){
                moviesAdapter.submitData(PagingData.from(it))
            }
        }


    }
}