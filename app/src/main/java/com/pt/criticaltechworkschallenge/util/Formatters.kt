package com.pt.criticaltechworkschallenge.util

import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

fun String.formatDate(): String {
    return try {
        val zonedDateTime = ZonedDateTime.parse(this)
        val formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)
        zonedDateTime.format(formatter)
    } catch (_: Exception) {
        this
    }
}