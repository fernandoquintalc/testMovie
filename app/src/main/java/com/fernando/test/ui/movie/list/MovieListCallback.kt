package com.fernando.test.ui.movie.list

import androidx.recyclerview.widget.DiffUtil
import com.fernando.test.domain.Movie

class MovieListCallback : DiffUtil.ItemCallback<Movie>() {
    override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem.movieId == newItem.movieId
    }

    override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem == newItem
    }
}