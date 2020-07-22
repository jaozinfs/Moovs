package com.example.paging.datasource

import android.util.Log
import androidx.paging.PagingSource
import com.example.paging.data.repository.local.entities.MovieEntity
import com.example.paging.data.repository.network.movies.MoviesRepository

class MoviesRemoteMediator(
    val service: MoviesRepository
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