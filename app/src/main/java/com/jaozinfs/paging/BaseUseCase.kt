package com.jaozinfs.paging

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

interface BaseUseCase<Params> {
    sealed class Result() {
        class Sucess<D>(val data: D) : Result()
        class Error(val error: Throwable) : Result()
    }

    fun execute(params: Params): Flow<Result>

    fun <T> stateFlow(scope:suspend () -> T): Flow<Result> = flow {
        runCatching {
            val result = scope.invoke()
            emit(Result.Sucess(result))
        }.onFailure {
            emit(Result.Error(it))
        }
    }
}