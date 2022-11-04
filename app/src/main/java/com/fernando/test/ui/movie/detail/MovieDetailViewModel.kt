package com.fernando.test.ui.movie.detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fernando.test.data.repository.MovieRepository
import com.fernando.test.domain.Genre
import com.fernando.test.domain.Movie
import com.fernando.test.domain.MovieVideo
import com.fernando.test.usecases.GetGenreByIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    private val repository: MovieRepository,
    private val getGenreByIdUseCase: GetGenreByIdUseCase
) :
    ViewModel() {

    private val _videos = MutableLiveData<Result<List<MovieVideo>>>()
    val videos: LiveData<Result<List<MovieVideo>>> get() = _videos

    private val _genres = MutableLiveData<List<Genre>>()
    val genres: LiveData<List<Genre>> get() = _genres

    private var videoId: String? = null


    suspend fun searchVideos(movie: Movie) {
        repository.getVideosByMovie(movie).let {
            _videos.postValue(it)
        }
    }

    fun getVideo() = videoId

    fun setVideos(videos: List<MovieVideo>) {
        viewModelScope.launch(Dispatchers.IO){
            try {
                videoId = videos.first { it.site == "YouTube" && it.official }.key
                Log.e("Video searched", videoId ?: "NoId")
            } catch (ex: NoSuchElementException) {
                ex.printStackTrace()
            }
        }



    }

    fun loadGenres(genres : Array<Int>){
        viewModelScope.launch(Dispatchers.IO){
            val list = ArrayList<Genre>()
            genres.forEach { genre ->
                Log.e("Genre", genre.toString())
                list.add(getGenreByIdUseCase(genre))
            }
            _genres.postValue(list)
        }
    }


}