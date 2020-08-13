package com.jaozinfs.paging.tvs.data.mappers

import com.jaozinfs.paging.database.local.entities.series.TvEntity
import com.jaozinfs.paging.tvs.data.network.response.EpisodeResponse
import com.jaozinfs.paging.tvs.data.network.response.SeasonDetailsResponse
import com.jaozinfs.paging.tvs.data.network.response.TvDetailsResponse
import com.jaozinfs.paging.tvs.data.network.response.TvResponse
import com.jaozinfs.paging.tvs.domain.model.*

fun TvResponse.toUI(): TvUI =
    TvUI(
        poster_path,
        popularity,
        id,
        backdrop_path,
        vote_average,
        overview,
        first_air_date,
        origin_country,
        genre_ids,
        original_language,
        vote_count,
        name,
        original_name
    )

fun TvEntity.toUI(): TvUI =
    TvUI(
        poster_path,
        popularity,
        id,
        backdrop_path,
        vote_average,
        overview,
        first_air_date,
        origin_country,
        genre_ids,
        original_language,
        vote_count,
        name,
        original_name
    )

fun TvDetailsResponse.toUI(): TvDetailsUI = TvDetailsUI(
    backdrop_path,
//    val created_by: List<Any>,
    episode_run_time,
    first_air_date,
    genres.map { GenreUI(it.id, it.name) },
    homepage,
    id,
    in_production,
    languages,
    last_air_date,
    LastEpisodeToAirUI(
        last_episode_to_air.air_date,
        last_episode_to_air.episode_number,
        last_episode_to_air.id,
        last_episode_to_air.name,
        last_episode_to_air.overview,
        last_episode_to_air.production_code,
        last_episode_to_air.season_number,
        last_episode_to_air.show_id,
        last_episode_to_air.still_path,
        last_episode_to_air.vote_average,
        last_episode_to_air.vote_count
    ),
    name,
    networks.map { NetworkUI(it.id, it.logo_path, it.name, it.origin_country) },
//    next_episode_to_air: Any,
    number_of_episodes,
    number_of_seasons,
    origin_country,
    original_language,
    original_name,
    overview,
    popularity,
    poster_path,
//    in_production,
    seasons.map {
        SeasonUI(
            it.air_date,
            it.episode_count,
            it.id,
            it.name,
            it.overview,
            it.poster_path,
            it.season_number
        )
    },
    status,
    type,
    vote_average,
    vote_count
)

fun SeasonDetailsResponse.toUI(): SeasonDetailsUI = SeasonDetailsUI(
    _id,
    air_date,
    episodes.map {
        it.toUI()
    },
    id,
    name,
    overview,
    poster_path,
    season_number
)

fun EpisodeResponse.toUI() =
    EpisodeUI(
        air_date,
        episode_number,
        id,
        name,
        overview,
        production_code,
        season_number,
        show_id,
        still_path,
        vote_average,
        vote_count
    )