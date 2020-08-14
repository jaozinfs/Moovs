package com.jaozinfs.moovs.network

import com.jaozinfs.moovs.network.exceptions.BadRequestException
import retrofit2.Response
import java.net.UnknownHostException

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
        val genericMessageException by lazy {
            "Tente novamente mais tarde."
        }
        return try {

            val response = api()
            response.takeIf { it.isSuccessful }?.body()
                ?: throw when (response.code()) {
                    404 -> BadRequestException(response.code(), genericMessageException)
                    else -> Exception("Error in request")
                }
        } catch (error: Exception) {
            error.printStackTrace()
            throw if (error is UnknownHostException)
                BadRequestException(404, genericMessageException)
            else
                error
        }
    }
     fun <S> getDataFlow(
        api: () -> Response<S>
    ): S {
        val genericMessageException by lazy {
            "Tente novamente mais tarde."
        }
        return try {

            val response = api()
            response.takeIf { it.isSuccessful }?.body()
                ?: throw when (response.code()) {
                    404 -> BadRequestException(response.code(), genericMessageException)
                    else -> Exception("Error in request")
                }
        } catch (error: Exception) {
            error.printStackTrace()
            throw if (error is UnknownHostException)
                BadRequestException(404, genericMessageException)
            else
                error
        }
    }
}
