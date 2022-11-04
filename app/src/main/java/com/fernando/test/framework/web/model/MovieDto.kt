package com.fernando.test.framework.web.model

import com.fernando.test.domain.Genre
import com.fernando.test.domain.Movie
import com.fernando.test.framework.database.toStringArray
import com.google.gson.annotations.SerializedName

class MovieDto(
    @SerializedName("id") override val movieId: Long,
    @SerializedName("title") override val name: String,
    @SerializedName("genre_ids") override val genres: Array<Int>,
    @SerializedName("overview") override val description: String,
    @SerializedName("release_date") override val year: String,
    @SerializedName("original_language") override val language: String,
    @SerializedName("vote_average") override val rate: Double,
    @SerializedName("poster_path") override val poster: String,
) :
    Movie(movieId, name, genres, description, year, language, rate, poster)