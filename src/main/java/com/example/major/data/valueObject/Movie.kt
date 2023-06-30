package com.example.major.data.valueObject


import com.google.gson.annotations.SerializedName

data class Movie(

    @SerializedName("id")
    val id: Int,
    @SerializedName("poster_path")
    val posterPath: String,
    @SerializedName("release_date")
    val releaseDate: String,
    @SerializedName("title")
    val title: String,

    )