package com.fernando.test.ui.movie.detail

import androidx.recyclerview.widget.DiffUtil
import com.fernando.test.domain.Genre

class GenreItemDifference : DiffUtil.ItemCallback<Genre>() {
    override fun areItemsTheSame(oldItem: Genre, newItem: Genre): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Genre, newItem: Genre): Boolean {
        return oldItem == newItem
    }
}