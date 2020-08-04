package com.jaozinfs.paging.movies.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.jaozinfs.paging.database.local.entities.MovieEntity
import com.jaozinfs.paging.extensions.handleErrors
import com.jaozinfs.paging.movies.data.MoviesPagingSource
import com.jaozinfs.paging.movies.domain.movies.MovieUi
import com.jaozinfs.paging.movies.domain.movies.MoviesRepository
import com.jaozinfs.paging.movies.domain.usecase.*
import com.jaozinfs.paging.utils.SingleLiveEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

class MoviesViewModel(
    private val moviesRepository: MoviesRepository,
    private val getGenresUseCase: GetGenresUseCase,
    private val getGenreResultUseCase: GetGenresUseResultCase,
    private val getMovieDetailsUseCase: GetMovieDetailsUseCase,
    private val getMovieImagesUseCase: GetMovieImagesUseCase,
    private val saveMovieFavoriteUseCase: SaveMovieFavoriteUseCase,
    private val checkIsMovieFavoritedUseCase: CheckIsMovieFavoritedUseCase,
    private val removeMovieFavoriteUseCase: RemoveMovieFavoriteUseCase
) : ViewModel() {


    companion object {
        private const val NETWORK_PAGE_SIZE = 20
    }

    val handleErrorImages = SingleLiveEvent<String>()
    val handlerErrorMovieDetails = SingleLiveEvent<Unit?>()
    val disableFavoriteButton = SingleLiveEvent<Unit?>()


    fun getMovies(
        voteAvarage: String? = null,
        nameFilter: String? = null,
        isAdult: Boolean? = null,
        genres: List<Int>? = null
    ): Flow<PagingData<MovieEntity>> {
        //create flow
        var flow = Pager(
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
        }
        //genres map
        flow.takeIf { !genres.isNullOrEmpty() }?.map {
            it.filter { movie ->
                genres!!.any {
                    movie.genre_ids.contains(it)
                }
            }
        }?.apply {
            flow = this
        }
        return flow
    }

    /**
     * Get genres from network
     * 1-> filter genres
     * 2-> map to list of names
     * 3-> map joinString separator with [ ", " ]
     */
    fun getGenres(movieEntity: MovieEntity) = getGenresUseCase.execute(null).map {
        it.filter { genre ->
            movieEntity.genre_ids!!.contains(genre.id)
        }
    }.map {
        it.joinToString(", ") { genre ->
            genre.name
        }
    }


    fun getGenresList() = getGenresUseCase.execute(null)

    fun getMovieDetails(id: Int): Flow<MovieUi> = getMovieDetailsUseCase.execute(id).handleErrors {
        handlerErrorMovieDetails.call()
    }

    //Get movie images
    //1->sum posters and backdrops
    //2->shuffle
    fun getMovieImages(id: Int): Flow<List<String>> = getMovieImagesUseCase.execute(id).map {
        it.posters.map {
            it.file_path
        } + it.backdrops.map { it.file_path }
    }.map {
        it.shuffled()
    }.handleErrors {
        handleErrorImages.value = it
    }

    /**
     * Save movie
     */
    fun saveMovie(movieUi: MovieUi) = saveMovieFavoriteUseCase.execute(movieUi).handleErrors {
        Log.d("Teste", "Error $it")
    }.flowOn(Dispatchers.IO)

    /**
     * Verify if movie is favorite
     * emit only movie favorited
     */
    fun checkIsFavorited(movieUi: MovieUi) =
        checkIsMovieFavoritedUseCase.execute(movieUi)
            .flowOn(
                Dispatchers.IO
            ).handleErrors {
                disableFavoriteButton.call()
            }.flowOn(Dispatchers.Main)

    /**
     * Remove movie favorite
     * map to deletede one
     * filter and emit only is true
     */
    fun removeMovieFavorited(movieId: Int) =
        removeMovieFavoriteUseCase.execute(movieId)
            .map { it == 1 }
            .filter { it }
            .flowOn(Dispatchers.IO)
}
