package com.example.major.data.repository

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.example.major.data.api.TheMovieDatabaseInterface
import com.example.major.data.valueObject.Movie
import io.reactivex.disposables.CompositeDisposable

class MovieDatasourceFactory(
    private val apiService: TheMovieDatabaseInterface,
    private val compositeDisposable: CompositeDisposable
) : DataSource.Factory<Int, Movie>() {

    val moviesLiveDataSource = MutableLiveData<MovieDatasource>()

    override fun create(): DataSource<Int, Movie> {
        val movieDataSource = MovieDatasource(apiService, compositeDisposable)
        moviesLiveDataSource.postValue(movieDataSource)
        return movieDataSource
    }

}