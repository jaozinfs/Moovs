package com.jaozinfs.paging.utils

import java.util.concurrent.TimeUnit

fun Int?.fromMinutesToHHmm(): String {
    this?:return "0h"
    val hours = TimeUnit.MINUTES.toHours(toLong())
    val remainMinutes = this - TimeUnit.HOURS.toMinutes(hours)
    return String.format("%2dh%02d", hours, remainMinutes)
}