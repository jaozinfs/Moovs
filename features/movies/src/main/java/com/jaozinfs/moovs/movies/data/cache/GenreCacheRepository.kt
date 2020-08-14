package com.jaozinfs.moovs.movies.data.cache

import com.jaozinfs.moovs.movies.domain.movies.GenreUI

interface GenreCacheRepository {
    fun saveGenres(genres: List<GenreUI>)
    fun getGenres(): List<GenreUI>?
}