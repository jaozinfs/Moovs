package com.jaozinfs.paging.movies.data.cache

import com.jaozinfs.paging.movies.domain.movies.GenreUI

interface GenreCacheRepository {
    fun saveGenres(genres: List<GenreUI>)
    fun getGenres(): List<GenreUI>?
}