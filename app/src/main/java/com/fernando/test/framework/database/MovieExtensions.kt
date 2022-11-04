package com.fernando.test.framework.database

import android.text.TextUtils
import com.fernando.test.domain.Genre
import com.fernando.test.domain.Movie
import com.fernando.test.framework.database.model.GenreDB
import com.fernando.test.framework.database.model.MovieTopRateDB
import com.fernando.test.framework.database.model.MovieUpcomingDB
import com.google.gson.Gson
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

fun List<Movie>.toMovieDBList(): List<MovieUpcomingDB> {

    val list = ArrayList<MovieUpcomingDB>()

    this.forEach {
        list.add(
            MovieUpcomingDB(
                it.movieId,
                it.name,
                it.genres,
                it.description,
                it.year,
                it.language,
                it.rate,
                it.poster,
                it.year.toDateLong(),

            )
        )
    }

    return list

}

fun List<Movie>.toMovieTopRateDBList(): List<MovieTopRateDB> {

    val list = ArrayList<MovieTopRateDB>()

    this.forEach {
        list.add(
            MovieTopRateDB(
                it.movieId,
                it.name,
                it.genres,
                it.description,
                it.year,
                it.language,
                it.rate,
                it.poster,
            )
        )
    }

    return list

}

fun String.toDateLong() : Long{
    val format = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    return format.parse(this)?.time ?: 0
}

fun List<Genre>.toGenreDBList() : List<GenreDB>{
    val list = kotlin.collections.ArrayList<GenreDB>()

    forEach {
        list.add(GenreDB(it.id, it.name))
    }

    return list
}

fun Array<Int>.toStringArray() : String{
    return TextUtils.join(",", this)
}