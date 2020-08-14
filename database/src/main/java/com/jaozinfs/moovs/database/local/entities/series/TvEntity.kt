package com.jaozinfs.moovs.database.local.entities.series

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class TvEntity(
    val poster_path: String?,
    val popularity: Float,
    @PrimaryKey
    val id: Int,
    val backdrop_path: String?,
    val vote_average: Float,
    val overview: String,
    val first_air_date: String,
    val origin_country: List<String>,
    val genre_ids: List<Int>,
    val original_language: String,
    val vote_count: Int,
    val name: String,
    val original_name: String
)