package com.jaozinfs.moovs.extensions

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlin.coroutines.coroutineContext

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
    } while (coroutineContext.isActive)
}

suspend fun incress(delay: Long) = flow {
    var start = 0
    do {
        emit(start)
        delay(delay)
        start++
    } while (coroutineContext.isActive)
}

fun b() {

}
