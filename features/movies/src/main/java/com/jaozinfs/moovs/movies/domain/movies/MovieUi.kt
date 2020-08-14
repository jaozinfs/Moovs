package com.jaozinfs.moovs.movies.domain.movies

import com.jaozinfs.moovs.database.local.entities.GenreEntity
import com.jaozinfs.moovs.utils.fromMinutesToHHmm
import java.io.Serializable

data class MovieUi(
    val adult: Boolean,
    val backdrop_path: String,
    val genre_ids: List<Int>?,
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
    val genres: List<GenreEntity> = emptyList()
) : Serializable {

    //Description have
    //->Date
    //->Genres
    //->RunTime
    val shortDescription: String by lazy {
        "$release_date ${genres.joinToString(separator = ", ") {
            it.name
        }} ${runtime.fromMinutesToHHmm()}"
    }

}