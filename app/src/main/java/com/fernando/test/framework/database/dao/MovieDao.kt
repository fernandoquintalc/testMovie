package com.fernando.test.framework.database.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import androidx.room.Transaction
import com.fernando.test.domain.Genre
import com.fernando.test.domain.Movie
import com.fernando.test.framework.database.model.GenreDB
import com.fernando.test.framework.database.model.MovieTopRateDB
import com.fernando.test.framework.database.model.MovieUpcomingDB
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {

    @Query("SELECT * FROM movies_upcoming ORDER BY dateLong DESC")
    fun getMovies(): PagingSource<Int, Movie>

    @Query("SELECT * FROM movies_upcoming ORDER BY dateLong DESC")
    fun getMoviesUpcomingCache(): List<Movie>

    @Query("DELETE FROM movies_upcoming")
    fun deleteAll()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(movies: List<MovieUpcomingDB>)

    @Transaction
    fun deleteAndInsert(movies: List<MovieUpcomingDB>) {
        deleteAll()
        insertAll(movies)

    }

    @Query("SELECT * FROM movies_top_rate ORDER BY rate DESC")
    fun getMoviesTopRate(): PagingSource<Int, Movie>

    @Query("DELETE FROM movies_top_rate")
    fun deleteAllMoviesTopRate()

    @Insert(onConflict = REPLACE)
    fun insertAllMoviesTopRate(movies: List<MovieTopRateDB>)

    @Transaction
    fun deleteAndInsertMoviesTopRate(movies: List<MovieTopRateDB>) {
        deleteAllMoviesTopRate()
        insertAllMoviesTopRate(movies)

    }

    @Query("SELECT * FROM movies_top_rate WHERE language = :language LIMIT 1,6")
    fun getMoviesByLanguage(language: String): Flow<List<Movie>>

    @Query("SELECT * FROM movies_top_rate WHERE year LIKE :year LIMIT 1,6")
    fun getMoviesByYear(year: String): Flow<List<Movie>>


    @Insert(onConflict = REPLACE)
    fun insertAllGenres(genres: List<GenreDB>)

    @Query("DELETE FROM genre")
    fun deleteAllGenres()

    @Transaction
    fun deleteAndInsertGenres(genres: List<GenreDB>) {
        deleteAllGenres()
        insertAllGenres(genres)
    }

    @Query("SELECT * FROM genre WHERE id = :genreId")
    fun selectGenreById(genreId: Int): Genre
}