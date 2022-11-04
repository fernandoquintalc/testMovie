package com.fernando.test.framework.paging

import androidx.paging.*
import com.fernando.test.domain.Movie
import com.fernando.test.framework.database.MovieDatabase
import com.fernando.test.framework.database.toGenreDBList
import com.fernando.test.framework.database.toMovieDBList
import com.fernando.test.framework.database.toMovieTopRateDBList
import com.fernando.test.framework.web.datasource.RemoteMovieDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import retrofit2.HttpException
import java.io.IOException

@OptIn(ExperimentalPagingApi::class)
class MoviesTopRateRemoteMediator constructor(
    database: MovieDatabase,
    private val remoteMovieDataSource: RemoteMovieDataSource
) : RemoteMediator<Int, Movie>() {
    private val movieDao = database.movieDao()
    private var page = 1L
    override suspend fun load(loadType: LoadType, state: PagingState<Int, Movie>): MediatorResult {
        return try {
            var isEnd = false
            val response = remoteMovieDataSource.getMoviesTopRateByPage(page)
            if (response.isSuccess) {
                val data = response.getOrNull()
                data?.let { data ->
                    isEnd = data.page == 3
                    if (!isEnd) {
                        page++
                    }
                    if (loadType == LoadType.REFRESH) {
                        val responseGenres = remoteMovieDataSource.getGenres()
                        if (responseGenres.isSuccess) {
                            val dataGenre = responseGenres.getOrNull()
                            dataGenre?.let { dataGenre ->
                                runBlocking(Dispatchers.IO) {
                                    movieDao.deleteAndInsertGenres(dataGenre.toGenreDBList())
                                }
                            }
                        }

                        runBlocking(Dispatchers.IO) {
                            movieDao.deleteAndInsertMoviesTopRate(data.results.toMovieTopRateDBList())
                        }
                    } else {
                        runBlocking(Dispatchers.IO) {
                            movieDao.insertAllMoviesTopRate(data.results.toMovieTopRateDBList())
                        }
                    }
                }
            }

            MediatorResult.Success(
                endOfPaginationReached = isEnd
            )


        } catch (e: IOException) {
            MediatorResult.Error(e)
        } catch (e: HttpException) {
            MediatorResult.Error(e)
        }

    }


}