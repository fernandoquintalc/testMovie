package com.fernando.test.framework.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.fernando.test.domain.Movie

@Entity(tableName = "movies_upcoming")
class MovieUpcomingDB(
    @PrimaryKey override val movieId: Long,
    @ColumnInfo override val name: String,
    @ColumnInfo override val genres : Array<Int>,
    @ColumnInfo override val description: String,
    @ColumnInfo override val year: String,
    @ColumnInfo override val language: String,
    @ColumnInfo override val rate: Double,
    @ColumnInfo override val poster: String?,
    @ColumnInfo val dateLong: Long,
) : Movie(movieId, name,genres, description, year, language, rate, poster )