package com.jaozinfs.paging.tvs.domain.model

data class SeasonUI(
    val air_date: String?,
    val episode_count: Int,
    val id: Int,
    val name: String,
    val overview: String,
    val poster_path: String?,
    val season_number: Int
)