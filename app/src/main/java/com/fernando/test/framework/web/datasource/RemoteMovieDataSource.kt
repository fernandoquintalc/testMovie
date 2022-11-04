package com.fernando.test.framework.web.datasource

import com.fernando.test.data.datasource.MovieDataSource
import com.fernando.test.domain.Genre
import com.fernando.test.domain.MovieVideo
import com.fernando.test.framework.exception.ServerException
import com.fernando.test.framework.web.model.MovieDto
import com.fernando.test.framework.web.model.ResultMovies
import com.fernando.test.framework.web.retrofit.MovieService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import retrofit2.Retrofit
import java.net.SocketException
import java.net.SocketTimeoutException
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class RemoteMovieDataSource constructor(private val retrofit: Retrofit) : MovieDataSource {


    override suspend fun getVideosByMovieId(movieId: Long): Result<List<MovieVideo>> =
        withContext(Dispatchers.IO)
        {
            try {
                val response =
                    retrofit.create(MovieService::class.java).getVideosByMovieId(movieId = movieId)
                        .execute()

                return@withContext if (response.isSuccessful) {
                    Result.success(response.body()!!.results)
                } else {

                    Result.failure(
                        ServerException(
                            response.code(),
                            response.errorBody().toString()
                        )
                    )


                }
            } catch (exception: HttpException) {
                return@withContext Result.failure(exception)
            } catch (exception: SocketTimeoutException) {
                return@withContext Result.failure(exception)
            }


        }

    override suspend fun getGenres(): Result<List<Genre>> =
        withContext(Dispatchers.IO)
        {
            try {
                val response =
                    retrofit.create(MovieService::class.java).getGenres()
                        .execute()

                return@withContext if (response.isSuccessful) {
                    Result.success(response.body()!!.genres)
                } else {

                    Result.failure(
                        ServerException(
                            response.code(),
                            response.errorBody().toString()
                        )
                    )


                }
            } catch (exception: HttpException) {
                return@withContext Result.failure(exception)
            } catch (exception: SocketTimeoutException) {
                return@withContext Result.failure(exception)
            }


        }


    suspend fun getMoviesUpcomingByPage(page: Long): Result<ResultMovies<MovieDto>> =
        withContext(Dispatchers.IO) {


            val response =
                retrofit.create(MovieService::class.java).getMoviesUpcoming(page).execute()

            return@withContext if (response.isSuccessful) {
                Result.success(response.body()!!)
            } else {

                Result.failure(
                    ServerException(
                        response.code(),
                        response.errorBody().toString()
                    )
                )


            }
        }


    suspend fun getMoviesTopRateByPage(page: Long): Result<ResultMovies<MovieDto>> =
        withContext(Dispatchers.IO) {

            val response =
                retrofit.create(MovieService::class.java).getMoviesTopRated(page).execute()

            return@withContext if (response.isSuccessful) {
                Result.success(response.body()!!)
            } else {
                Result.failure(ServerException(response.code(), response.errorBody().toString()))


            }
        }


}