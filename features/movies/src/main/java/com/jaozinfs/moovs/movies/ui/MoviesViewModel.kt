package com.jaozinfs.moovs.movies.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.jaozinfs.moovs.database.local.entities.MovieEntity
import com.jaozinfs.moovs.extensions.asLiveData
import com.jaozinfs.moovs.extensions.handleErrors
import com.jaozinfs.moovs.movies.data.MoviesPagingSource
import com.jaozinfs.moovs.movies.domain.movies.MovieUi
import com.jaozinfs.moovs.movies.domain.movies.MoviesRepository
import com.jaozinfs.moovs.movies.domain.usecase.favorites.CheckIsMovieFavoritedUseCase
import com.jaozinfs.moovs.movies.domain.usecase.favorites.GetMoviesFavoritesUseCase
import com.jaozinfs.moovs.movies.domain.usecase.favorites.RemoveMovieFavoriteUseCase
import com.jaozinfs.moovs.movies.domain.usecase.favorites.SaveMovieFavoriteUseCase
import com.jaozinfs.moovs.movies.domain.usecase.movies.GetGenresUseCase
import com.jaozinfs.moovs.movies.domain.usecase.movies.GetGenresUseResultCase
import com.jaozinfs.moovs.movies.domain.usecase.movies.GetMovieDetailsUseCase
import com.jaozinfs.moovs.movies.domain.usecase.movies.GetMovieImagesUseCase
import com.jaozinfs.moovs.utils.SingleLiveEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import timber.log.Timber

class MoviesViewModel(
    private val moviesRepository: MoviesRepository,
    private val getGenresUseCase: GetGenresUseCase,
    private val getGenreResultUseCase: GetGenresUseResultCase,
    private val getMovieDetailsUseCase: GetMovieDetailsUseCase,
    private val getMovieImagesUseCase: GetMovieImagesUseCase,
    private val saveMovieFavoriteUseCase: SaveMovieFavoriteUseCase,
    private val checkIsMovieFavoritedUseCase: CheckIsMovieFavoritedUseCase,
    private val removeMovieFavoriteUseCase: RemoveMovieFavoriteUseCase,
    private val getMoviesFavoritesUseCase: GetMoviesFavoritesUseCase
) : ViewModel() {

    data class MoviesFilterObject(
        val voteAvarage: String? = null,
        val nameFilter: String? = null,
        val isAdult: Boolean? = null,
        val genres: List<Int>? = null,
        val isRefresh: Boolean = false
    )

    companion object {
        private const val NETWORK_PAGE_SIZE = 20
    }

    val handleErrorImages = SingleLiveEvent<String>()
    val handlerErrorMovieDetails = SingleLiveEvent<Unit?>()
    val disableFavoriteButton = SingleLiveEvent<Unit?>()
    val handleErroMovies = SingleLiveEvent<String?>()
    private val _emptyMovies = MutableLiveData<Boolean>().apply { value = false }
    val emptyMovies = _emptyMovies.asLiveData


    private var flow: Flow<PagingData<MovieEntity>>? = null
    private var currentFilterObject: MoviesFilterObject? = null

    fun getMovies(
        moviesFilterObject: MoviesFilterObject
    ): Flow<PagingData<MovieEntity>> = with(moviesFilterObject) {
        val lastResult = flow

        if (moviesFilterObject == currentFilterObject && lastResult != null && !isRefresh) {
            return lastResult
        }
        currentFilterObject = moviesFilterObject
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
        this@MoviesViewModel.flow = flow
        return this@MoviesViewModel.flow!!
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
        Timber.e("Error $it")
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
            .asLiveData()

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


    fun getFavoriteMovies() = getMoviesFavoritesUseCase.execute(null)
        .flowOn(Dispatchers.IO)
        .onEmpty {
            _emptyMovies.value = true
        }.flowOn(Dispatchers.Main)
}
