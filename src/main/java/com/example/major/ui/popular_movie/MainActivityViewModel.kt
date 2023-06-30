package com.example.major.ui.popular_movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.example.major.data.repository.NetworkState
import com.example.major.data.valueObject.Movie
import io.reactivex.disposables.CompositeDisposable

@Suppress("DEPRECATION")
class MainActivityViewModel(private val movieRepository: MoviePageListRepository) : ViewModel() {
    private val compositeDisposable = CompositeDisposable()

    val moviePagedList: LiveData<PagedList<Movie>> by lazy {
        movieRepository.fetchLiveMoviePagedList(compositeDisposable)
    }

//    val networkState: LiveData<NetworkState> by lazy {
//        movieRepository.getNetworkState()
//    }

    fun listIsEmpty() : Boolean {
        return moviePagedList.value?.isEmpty() ?: true
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}