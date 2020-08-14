package com.jaozinfs.moovs.tvs.data.network.response

data class TvsBaseResponse<T>(
    val count: Int,
    val next: Any,
    val previous: Any,
    val results: List<T>
)