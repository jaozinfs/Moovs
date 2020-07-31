package com.jaozinfs.paging.movies.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.jaozinfs.paging.database.local.entities.MovieEntity
import com.jaozinfs.paging.movies.data.MoviesPagingSource
import com.jaozinfs.paging.movies.domain.movies.MovieUi
import com.jaozinfs.paging.movies.domain.movies.MoviesRepository
import com.jaozinfs.paging.movies.domain.usecase.GetGenresUseCase
import com.jaozinfs.paging.movies.domain.usecase.GetMovieDetailsUseCase
import com.jaozinfs.paging.movies.domain.usecase.GetMovieImagesUseCase
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class MoviesViewModel(
    private val moviesRepository: MoviesRepository,
    private val getGenresUseCase: GetGenresUseCase,
    private val getMovieDetailsUseCase: GetMovieDetailsUseCase,
    private val getMovieImagesUseCase: GetMovieImagesUseCase
) : ViewModel() {
    companion object {
        private const val NETWORK_PAGE_SIZE = 20
    }

    fun getMovies(
        voteAvarage: String? = null,
        nameFilter: String? = null,
        isAdult: Boolean? = null,
        genres: List<Int>? = null
    ): Flow<PagingData<MovieEntity>> {
        //create flow
        return Pager(
            config = PagingConfig(pageSize = NETWORK_PAGE_SIZE, enablePlaceholders = false),
            pagingSourceFactory = {
                MoviesPagingSource(moviesRepository)
            }
        ).flow.cachedIn(viewModelScope).map { paginData ->
            paginData.filter {
                val vote = try {
                    voteAvarage?.toInt()
                } catch (exception: NumberFormatException) {
                    exception.printStackTrace()
                    null
                }
                it.vote_average >= vote ?: 0 &&
                        (if (!nameFilter.isNullOrEmpty()) it.original_title.contains(
                            nameFilter,
                            true
                        ) else true)
                        && (if (isAdult != null) it.adult == isAdult else true)

            }
        }.map {
            if (genres.isNullOrEmpty())
                return@map it
            it.filter { movie ->
                genres.any {
                    movie.genre_ids.contains(it)
                }
            }
        }
    }

    /**
     * Get genres from network
     * 1-> filter genres
     * 2-> map to list of names
     * 3-> map joinString separator with [ ", " ]
     */
    fun getGenres(movieEntity: MovieEntity) = getGenresUseCase.execute(null).map {
        it.filter { genre ->
            movieEntity.genre_ids.contains(genre.id)
        }
    }.map {
        it.joinToString(", ") { genre ->
            genre.name
        }
    }

    fun getGenresList() = getGenresUseCase.execute(null)

    fun getMovieDetails(id: Int): Flow<MovieUi> = getMovieDetailsUseCase.execute(id)

    //Get movie images
    //1->sum posters and backdrops
    //2->shuffle
    fun getMovieImages(id: Int): Flow<List<String>> = getMovieImagesUseCase.execute(id).map {
        it.posters.map {
            it.file_path
        } + it.backdrops.map { it.file_path }
    }.map {
        it.shuffled()
    }

    fun autoSlide(startPosition: Int, count: Int, delayMillis: Long = 2_000) = flow {
        var s = startPosition % count
        do {
            emit(s)
            delay(delayMillis)
            s = (s + 1) % count
        } while (true)
    }


}
