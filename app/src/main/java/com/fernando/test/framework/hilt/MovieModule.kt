package com.fernando.test.framework.hilt

import android.app.Application
import androidx.room.Room
import com.fernando.test.BuildConfig
import com.fernando.test.data.repository.DefaultMovieRepository
import com.fernando.test.data.repository.MovieRepository
import com.fernando.test.framework.database.MovieDatabase
import com.fernando.test.framework.database.datasource.LocalDataSource
import com.fernando.test.framework.web.datasource.RemoteMovieDataSource
import com.fernando.test.framework.web.retrofit.ApiKeyInterceptor
import com.fernando.test.usecases.GetGenreByIdUseCase
import com.fernando.test.usecases.GetMovieListByLanguageUseCase
import com.fernando.test.usecases.GetMovieListByYearUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class MovieModule {

    @Singleton
    @Provides
    fun getRetrofit(): Retrofit {
        val client = OkHttpClient.Builder()
            .addInterceptor(ApiKeyInterceptor())
            .build()
        return Retrofit.Builder().baseUrl(BuildConfig.URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client).build()
    }

    @Singleton
    @Provides
    fun getRemoteMoviesDataSource(retrofit: Retrofit): RemoteMovieDataSource {
        return RemoteMovieDataSource(retrofit)
    }

    @Singleton
    @Provides
    fun getDatabase(application: Application): MovieDatabase {
        return Room.databaseBuilder(application, MovieDatabase::class.java, "movieDB").build()
    }

    @Singleton
    @Provides
    fun getMovieRepository(remoteMovieDataSource: RemoteMovieDataSource, database: MovieDatabase): MovieRepository =
        DefaultMovieRepository(remoteMovieDataSource, LocalDataSource(database.movieDao()))

    @Singleton
    @Provides
    fun getMovieListLanguageUseCase(movieRepository: MovieRepository) = GetMovieListByLanguageUseCase(movieRepository)

    @Singleton
    @Provides
    fun getMovieListYearUseCase(movieRepository: MovieRepository) = GetMovieListByYearUseCase(movieRepository)

    @Singleton
    @Provides
    fun getGetGenreByIdUseCase(movieRepository: MovieRepository) = GetGenreByIdUseCase(movieRepository)

}