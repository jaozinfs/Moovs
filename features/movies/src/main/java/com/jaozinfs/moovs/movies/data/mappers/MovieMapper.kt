package com.jaozinfs.moovs.movies.data.mappers

import com.jaozinfs.moovs.database.local.entities.MovieEntity
import com.jaozinfs.moovs.movies.data.network.model.GenreNetwork
import com.jaozinfs.moovs.movies.data.network.model.MovieImagesNetwork
import com.jaozinfs.moovs.movies.domain.movies.*

fun GenreNetwork.toUI(): GenreUI = GenreUI(id, name)

fun MovieImagesNetwork.toUI(): MovieImagesUI =
    MovieImagesUI(
        backdrops = backdrops.map { BackDropUI(it.file_path) },
        posters = posters.map { PostersUI(it.file_path) }
    )

fun MovieEntity.toUI(): MovieUi {
    return MovieUi(
        adult,
        backdrop_path ?: poster_path,
        genre_ids,
        id,
        original_language,
        original_title,
        overview,
        popularity,
        poster_path,
        release_date,
        title,
        video,
        vote_average,
        vote_count,
        runtime,
        tagline,
        genres ?: emptyList()
    )
}