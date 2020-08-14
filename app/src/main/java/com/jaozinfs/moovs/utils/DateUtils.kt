package com.jaozinfs.moovs.utils

import android.annotation.SuppressLint
import android.os.Build
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.concurrent.TimeUnit

fun Int?.fromMinutesToHHmm(): String {
    this ?: return "0h"
    val hours = TimeUnit.MINUTES.toHours(toLong())
    val remainMinutes = this - TimeUnit.HOURS.toMinutes(hours)
    return String.format("%2dh%02d", hours, remainMinutes)
}

val String.formatterDateBrazil: String
    get() {
        val parser = SimpleDateFormat("yyyy-MM-dd")
        val formatter = SimpleDateFormat("dd/MM/yyyy")
        return formatter.format(parser.parse(this))
    }