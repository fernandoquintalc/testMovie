package com.fernando.test.ui.movie.list

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.view.ViewCompat
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.fernando.test.BuildConfig
import com.fernando.test.databinding.AdapterMoviePosterBinding
import com.fernando.test.domain.Movie

class MovieUpcomingAdapter(diffCallback: DiffUtil.ItemCallback<Movie>) :
    PagingDataAdapter<Movie, MovieUpcomingAdapter.MovieHolder>(
        diffCallback
    ) {

    var listener : ItemMovieClickListener? = null


    override fun onBindViewHolder(holder: MovieHolder, position: Int) {
        val item = getItem(position)
        holder.binding.movie = getItem(position)

        item?.let {
            if (item.poster != null)
                Glide.with(holder.itemView).load(BuildConfig.URL_IMAGE + item.poster)
                    .into(holder.binding.imageView)
            holder.binding.imageView.transitionName = "postal_${item.movieId}"
            holder.itemView.setOnClickListener {
                listener?.onItemMovieClick(item, holder.binding.imageView)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieHolder {
        return MovieHolder(
            AdapterMoviePosterBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    class MovieHolder(val binding: AdapterMoviePosterBinding) :
        RecyclerView.ViewHolder(binding.root)

    interface ItemMovieClickListener {

        fun onItemMovieClick(movie: Movie, view : ImageView)
    }

}