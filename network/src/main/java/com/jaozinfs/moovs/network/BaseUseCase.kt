package com.jaozinfs.moovs.network

import kotlinx.coroutines.flow.Flow

interface
BaseUseCase<Params, T> {
    sealed class Result() {
        class Sucess<D>(val data: D) : Result()
        class Error(val error: Throwable) : Result()
    }

    fun execute(params: Params): Flow<T>

}