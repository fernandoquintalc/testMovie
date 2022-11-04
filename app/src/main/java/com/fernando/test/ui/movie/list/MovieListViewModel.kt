package com.fernando.test.ui.movie.list

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.fernando.test.domain.Genre
import com.fernando.test.framework.database.MovieDatabase
import com.fernando.test.framework.paging.MovieUpcomingRemoteMediator
import com.fernando.test.framework.paging.MoviesTopRateRemoteMediator
import com.fernando.test.framework.web.datasource.RemoteMovieDataSource
import com.fernando.test.usecases.GetGenreByIdUseCase
import com.fernando.test.usecases.GetMovieListByLanguageUseCase
import com.fernando.test.usecases.GetMovieListByYearUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieListViewModel @Inject constructor(
    remoteMovieDataSource: RemoteMovieDataSource,
    private val database: MovieDatabase,
    private val getMovieListByLanguageUseCase: GetMovieListByLanguageUseCase,
    private val getMovieListByYearUseCase: GetMovieListByYearUseCase,

) :
    ViewModel() {

    @OptIn(ExperimentalPagingApi::class)
    val moviesUpcomingFlowNetWork = Pager(
        // Configure how data is loaded by passing additional properties to
        // PagingConfig, such as prefetchDistance.
        PagingConfig(pageSize = 32),
        remoteMediator = MovieUpcomingRemoteMediator(database, remoteMovieDataSource)
    ) {
        database.movieDao().getMovies()
    }.flow
        .cachedIn(viewModelScope)

    @OptIn(ExperimentalPagingApi::class)
    val moviesTopRateFlowNetWork = Pager(
        // Configure how data is loaded by passing additional properties to
        // PagingConfig, such as prefetchDistance.
        PagingConfig(pageSize = 32),
        remoteMediator = MoviesTopRateRemoteMediator(database, remoteMovieDataSource)
    ) {
        database.movieDao().getMoviesTopRate()
    }.flow
        .cachedIn(viewModelScope)



    suspend fun getMoviesByLanguage(language : String) = getMovieListByLanguageUseCase(language)

    suspend fun getMoviesByYear(year : String) = getMovieListByYearUseCase(year)


}