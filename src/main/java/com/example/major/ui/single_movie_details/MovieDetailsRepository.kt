package com.example.major.ui.single_movie_details

import androidx.lifecycle.LiveData
import com.example.major.data.api.TheMovieDatabaseInterface
import com.example.major.data.repository.MovieDetailsNetworkDatasource
import com.example.major.data.repository.NetworkState
import com.example.major.data.valueObject.MovieDetails
import io.reactivex.disposables.CompositeDisposable

class MovieDetailsRepository(private val apiService: TheMovieDatabaseInterface) {
    lateinit var movieDetailsNetworkDatasource: MovieDetailsNetworkDatasource

    fun fetchSingleMovieDetails(
        compositeDisposable: CompositeDisposable,
        movieId: Int
    ): LiveData<MovieDetails> {
        movieDetailsNetworkDatasource =
            MovieDetailsNetworkDatasource(apiService, compositeDisposable)
        movieDetailsNetworkDatasource.getMovieDetails(movieId)

        return movieDetailsNetworkDatasource.downloadedMovieResponse
    }

    fun getMovieDetailsNetworkState(): LiveData<NetworkState> {
        return movieDetailsNetworkDatasource.networkState
    }

}