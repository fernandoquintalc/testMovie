package com.fernando.test.framework.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.fernando.test.domain.Genre
import com.fernando.test.domain.Movie
import com.fernando.test.framework.database.toStringArray
import com.fernando.test.framework.web.model.GenresDto
import com.google.gson.Gson

@Entity(tableName = "movies_top_rate")
class MovieTopRateDB(
    @PrimaryKey override val movieId: Long,
    @ColumnInfo override val name: String,
    @ColumnInfo override var genres : Array<Int>,
    @ColumnInfo override val description: String,
    @ColumnInfo override val year: String,
    @ColumnInfo override val language: String,
    @ColumnInfo override val rate: Double,
    @ColumnInfo override val poster: String?,
) : Movie(movieId, name, genres, description, year, language, rate, poster)