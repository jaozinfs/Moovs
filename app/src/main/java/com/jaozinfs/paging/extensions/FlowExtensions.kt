package com.jaozinfs.paging.extensions

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

fun <T> Flow<T>.handleErrors(onError: (String?) -> Unit = {}): Flow<T> =
    catch { e -> onError.invoke(e.message) }


/**
 * Flip with module restart
 * emit value into count and restart
 *
 */
fun flipModuleFlow(startPosition: Int, count: Int, delayMillis: Long = 2_000) = flow {
    var s = startPosition % count
    do {
        emit(s)
        delay(delayMillis)
        s = (s + 1) % count
    } while (true)
}



