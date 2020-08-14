package com.jaozinfs.moovs.tvs.data.local

import com.jaozinfs.moovs.database.MoviesDatabase
import com.jaozinfs.moovs.database.local.entities.series.TvEntity
import com.jaozinfs.moovs.tvs.data.mappers.toUI
import com.jaozinfs.moovs.tvs.domain.TvsLocalRepository
import com.jaozinfs.moovs.tvs.domain.model.TvDetailsUI
import com.jaozinfs.moovs.tvs.domain.model.TvUI
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class TvLocalRepositoryImpl(
    private val database: MoviesDatabase
) : TvsLocalRepository {

    override suspend fun getTvsFavorited(): List<TvUI> {
        return database.tvsDao().getTvsFavorited().map {
            it.toUI()
        }
    }

    override suspend fun saveTvDetailsUI(tvDetailsUI: TvDetailsUI): Long {
        return with(tvDetailsUI) {
            database.tvsDao().addTvDetails(
                TvEntity(
                    poster_path,
                    popularity.toFloat(),
                    id,
                    backdrop_path,
                    vote_average.toFloat(),
                    overview,
                    first_air_date,
                    origin_country,
                    emptyList(),
                    original_language,
                    vote_count,
                    name,
                    original_name
                )
            )
        }

    }

    override suspend fun removeTvFavorited(tvId: Int): Int =
        database.tvsDao().removeTvFavorited(tvId)

    override fun checkTvFavorite(tvId: Int): Flow<Boolean> {
        return database.tvsDao().checkTvFavorited(tvId).map {
            it.isNotEmpty()
        }
    }

}
