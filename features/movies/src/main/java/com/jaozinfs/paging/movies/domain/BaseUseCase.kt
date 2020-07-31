package com.jaozinfs.paging.movies.domain

import kotlinx.coroutines.flow.Flow

interface BaseUseCase<Params, T> {
    fun execute(params: Params): Flow<T>
}