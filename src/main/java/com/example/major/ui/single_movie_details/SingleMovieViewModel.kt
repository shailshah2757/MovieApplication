package com.example.major.ui.single_movie_details

import androidx.lifecycle.ViewModel

import io.reactivex.disposables.CompositeDisposable

class SingleMovieViewModel(
    private val movieDetailsRepository: MovieDetailsRepository,
    movieId: Int
) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    val movieDetails by lazy {
        movieDetailsRepository.fetchSingleMovieDetails(compositeDisposable, movieId)
    }

    val networkState by lazy {
        movieDetailsRepository.getMovieDetailsNetworkState()
    }

    //called when activity or fragment gets destroyed
    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }

}