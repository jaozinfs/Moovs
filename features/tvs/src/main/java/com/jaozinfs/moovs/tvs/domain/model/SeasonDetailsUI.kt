package com.jaozinfs.moovs.tvs.domain.model

data class SeasonDetailsUI(
    val _id: String,
    val air_date: String,
    val episodeResponses: List<EpisodeUI>,
    val id: Int,
    val name: String,
    val overview: String,
    val poster_path: String?,
    val season_number: Int
)