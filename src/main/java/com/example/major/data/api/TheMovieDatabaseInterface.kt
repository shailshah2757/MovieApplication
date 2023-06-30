package com.example.major.data.api

import com.example.major.data.valueObject.MovieDetails
import com.example.major.data.valueObject.MovieResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TheMovieDatabaseInterface {

    //https://api.themoviedb.org/3/movie/popular?api_key=5f6757cde1c3371fb39f67ab27573691&page=1
    //https://api.themoviedb.org/3/movie/697843?api_key=5f6757cde1c3371fb39f67ab27573691
    //https://api.themoviedb.org/3/

    //observable - it is the data stream that does some work and emits data
    //observer - it is the counter part of the observable, it receives the data immediate by the observable
    @GET("movie/{movie_id}")
    fun getMovieDetails(@Path("movie_id") id: Int): Single<MovieDetails>

    @GET("movie/popular")
    fun getPopularMovie(@Query("page") page: Int): Single<MovieResponse>
}