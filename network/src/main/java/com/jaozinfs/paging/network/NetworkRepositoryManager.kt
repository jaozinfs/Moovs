package com.jaozinfs.paging.network

import retrofit2.Response

/**
 * Esta classe é para auxiliar uma chamada de rede
 * onde a resposta é um [Response]
 * Caso der sucesso, retorna o objeto.
 * Caso der erro, trata o codigo e lança a exceçãoo
 * //TODO IMPLEMENTAR EXCESSOES
 */
object NetworkRepositoryManager {

    /**
     * Persiste a fução suspensa e retorna o ojeto
     * @return OBJETO PERSISTIDO
     */
    suspend fun <S> getData(
        api: suspend () -> Response<S>
    ): S {
        try {
            val response = api()
            return response.takeIf { it.isSuccessful }?.body()
                ?: throw Exception("Error in request")
        } catch (error: Exception) {
            error.printStackTrace()
            throw error
        }
    }

}
