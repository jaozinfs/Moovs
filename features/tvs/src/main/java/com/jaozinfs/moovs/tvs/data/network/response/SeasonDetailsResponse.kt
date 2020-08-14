package com.jaozinfs.moovs.tvs.data.network.response

data class SeasonDetailsResponse(
    val _id: String,
    val air_date: String,
    val episodes: List<EpisodeResponse> = emptyList(),
    val id: Int,
    val name: String,
    val overview: String,
    val poster_path: String?,
    val season_number: Int
)