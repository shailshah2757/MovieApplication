package com.example.major.ui.popular_movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.major.data.api.POST_PER_PAGE
import com.example.major.data.api.TheMovieDatabaseInterface
import com.example.major.data.repository.MovieDatasource
import com.example.major.data.repository.MovieDatasourceFactory
import com.example.major.data.repository.NetworkState
import com.example.major.data.valueObject.Movie
import io.reactivex.disposables.CompositeDisposable
import kotlin.reflect.KProperty1

@Suppress("DEPRECATION")
class MoviePageListRepository(private val apiService: TheMovieDatabaseInterface) {
    lateinit var moviePagedList: LiveData<PagedList<Movie>>
    lateinit var movieDataSourceFactory: MovieDatasourceFactory

    fun fetchLiveMoviePagedList(compositeDisposable: CompositeDisposable): LiveData<PagedList<Movie>> {
        movieDataSourceFactory = MovieDatasourceFactory(apiService, compositeDisposable)

        val config : PagedList.Config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPageSize(POST_PER_PAGE)
            .build()

        moviePagedList = LivePagedListBuilder(movieDataSourceFactory, config).build()

        return moviePagedList
    }

//    fun getNetworkState(): LiveData<NetworkState> {
//        return Transformations.switchMap<MovieDatasource, NetworkState>(
//            movieDataSourceFactory.moviesLiveDataSource, MovieDatasource::networkState)
//    }

}


