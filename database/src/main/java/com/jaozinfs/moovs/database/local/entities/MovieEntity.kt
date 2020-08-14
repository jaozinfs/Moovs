package com.jaozinfs.moovs.database.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class MovieEntity(
    val adult: Boolean,
    val backdrop_path: String?,
    val genre_ids: List<Int>,
    @PrimaryKey(autoGenerate = true)
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
    val vote_count: Int,
    val runtime: Int? = null,
    val tagline: String? = null,
    val genres: List<GenreEntity>? = emptyList()
)

data class Response<T>(
    val count: Int,
    val next: Any,
    val previous: Any,
    val results: List<T>
)

