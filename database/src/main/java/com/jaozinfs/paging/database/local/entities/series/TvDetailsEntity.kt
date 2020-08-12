package com.jaozinfs.paging.database.local.entities.series

import androidx.room.Entity
import com.jaozinfs.paging.database.local.entities.GenreEntity

data class TvDetailsEntity(
    val backdrop_path: String?,
    val episode_run_time: List<Int>,
    val first_air_date: String,
    val genres: List<GenreEntity>,
    val homepage: String,
    val id: Int,
    val in_production: Boolean,
    val languages: List<String>,
    val last_air_date: String,
    val last_episode_to_air: LastEpisodeToAirEntity,
    val name: String,
    val networks: List<NetworkEntity>,
    val number_of_episodes: Int,
    val number_of_seasons: Int,
    val origin_country: List<String>,
    val original_language: String,
    val original_name: String,
    val overview: String,
    val popularity: Double,
    val poster_path: String?,
    val seasons: List<SeasonEntity>,
    val status: String,
    val type: String,
    val vote_average: Double,
    val vote_count: Int
)