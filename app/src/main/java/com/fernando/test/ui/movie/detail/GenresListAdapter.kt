package com.fernando.test.ui.movie.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.fernando.test.databinding.AdapterGenreBinding
import com.fernando.test.domain.Genre
import com.fernando.test.domain.Movie

class GenresListAdapter : ListAdapter<Genre, GenresListAdapter.Holder>(GenreItemDifference()) {


    class Holder(val binding: AdapterGenreBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(
            AdapterGenreBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.binding.genre = getItem(position)
    }
}