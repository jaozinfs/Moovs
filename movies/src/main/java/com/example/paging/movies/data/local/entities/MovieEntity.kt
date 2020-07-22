package com.example.paging.movies.data.local.entities

import java.io.Serializable

data class MovieEntity(
    val adult: Boolean,
    val backdrop_path: String,
    val genre_ids: List<Int>,
    val id: Int,
    val original_language: String,
    val original_title: String,
    val overview: String,
    val popularity: Double,
    val poster_path: String,
    val release_date: String,
    val title: String,
    val video: Boolean,
    val vote_average: Double,
    val vote_count: Int
) : Serializable

data class Response<T>(
    val count: Int,
    val next: Any,
    val previous: Any,
    val results: List<T>
)