package com.jaozinfs.paging.movies.data

import android.util.Log
import androidx.paging.PagingSource
import com.jaozinfs.paging.database.local.entities.MovieEntity
import com.jaozinfs.paging.movies.domain.movies.MoviesRepository

class MoviesPagingSource(
    private val service: MoviesRepository
) : PagingSource<Int, MovieEntity>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieEntity> {
        return try {
            Log.d("Teste", "Page :${params.key}")
            val page = params.key ?: 1
            val movies = service.getMovies(page)
            return LoadResult.Page(
                movies,
                if (page == 1) null else page - 1,
                if (movies.isEmpty()) null else page + 1
            )
        } catch (error: Exception) {
            LoadResult.Error(error)
        }
    }
}