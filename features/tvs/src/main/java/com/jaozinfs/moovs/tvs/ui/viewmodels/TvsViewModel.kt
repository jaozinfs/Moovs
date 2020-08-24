package com.jaozinfs.moovs.tvs.ui.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.jaozinfs.moovs.extensions.handleErrors
import com.jaozinfs.moovs.tvs.domain.model.TvDetailsUI
import com.jaozinfs.moovs.tvs.domain.model.TvUI
import com.jaozinfs.moovs.tvs.domain.usecases.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.zip
import timber.log.Timber

class TvsViewModel(
    private val getTvsPopularUseCase: GetTvsPopularUseCase,
    private val getTvsOnAirUseCase: GetTvsOnAirUseCase,
    private val getTvDetailsUseCase: GetTvDetailsUseCase,
    private val getTvsFavoritedUseCase: GetTvsFavoritedUseCase,
    private val saveTvFavoriteUseCase: SaveTvFavoriteUseCase,
    private val removeTvFavoriteUseCase: RemoveTvFavoriteUseCase,
    private val checkTvIsFavoriteUseCase: CheckTvIsFavoriteUseCase,
    private val getSeasonEpisodesUseCase: GetSeasonEpisodesUseCase,
    private val getSeasonEpisodeUseCase: GetSeasonEpisodeUseCase
) : ViewModel() {
    companion object {
        const val POPULAR = "popular"
        const val ON_AIR = "onTheAir"
        const val FAVORITED = "favs"
    }

    fun observeTvIsFavorite(tvId: Int) =
        checkTvIsFavoriteUseCase.execute(tvId).asLiveData(viewModelScope.coroutineContext)


    /**
     * Get combine flow with all categories
     * @return [Flow] Triple<List<TvUI>, List<TvUI>, List<TvUi>> -> with segments of combined
     */
    fun getCategoriesCombined() =
        getTvsPopularFirstItem().zip(getTvsOnAir()) { popular, onAIr ->
            Pair(popular, onAIr)
        }.zip(getTvsFavoritedUseCase.execute(Unit)) { first, second ->
            Triple(first.first, first.second, second)
        }.flowOn(Dispatchers.IO)

    private fun getTvsPopularFirstItem() = getTvsPopularUseCase.execute(null).map { it.take(10) }
    private fun getTvsOnAir() = getTvsOnAirUseCase.execute(null).map { it.take(10) }


    fun getTvDetails(tvId: Int) = getTvDetailsUseCase.execute(tvId)

    /**
     * Get all items by category
     */
    fun getTvsByCategory(category: String): Flow<List<TvUI>> {
        return when (category) {
            POPULAR -> {
                getTvsPopularUseCase.execute(null)
            }
            ON_AIR -> {
                getTvsOnAirUseCase.execute(null)
            }
            FAVORITED -> {
                getTvsFavoritedUseCase.execute(Unit)
            }
            else -> {
                throw NotImplementedError("Categoria não implementada")
            }
        }
    }


    fun saveTvFavorite(tvUI: TvDetailsUI) =
        saveTvFavoriteUseCase
            .execute(tvUI)
            .handleErrors {
                Timber.e( "Error: $it")
            }


    fun removeTvFavorite(tvId: Int) =
        removeTvFavoriteUseCase
            .execute(tvId)
            .handleErrors {
                Timber.e( "Error: $it")
            }

    fun getSeasonEpisodes(tvId: Int, seaonsId: Int) =
        getSeasonEpisodesUseCase.execute(GetSeasonEpisodesUseCase.Params(tvId, seaonsId))

    fun getEpisodeDetails(tvID: Int, seasonID: Int, episodeID: Int) =
        getSeasonEpisodeUseCase.execute(GetSeasonEpisodeUseCase.Params(tvID, seasonID, episodeID))

}