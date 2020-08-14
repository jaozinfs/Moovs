package com.jaozinfs.moovs.movies.data.cache

import com.jaozinfs.moovs.movies.domain.movies.GenreUI
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class GenreCacheRepositoryImpl :
    GenreCacheRepository {

    override fun saveGenres(genres: List<GenreUI>) {
        _GenreCache.genres = genres
    }

    override fun getGenres(): List<GenreUI>? =
        _GenreCache.genres
}

private object _GenreCache {
    var genres: List<GenreUI>? = null
        set(value) {
            field = value
            GlobalScope.launch {
                delay(18_000_000)
                field = null
            }
        }
}