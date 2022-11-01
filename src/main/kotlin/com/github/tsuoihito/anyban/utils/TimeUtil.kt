package com.github.tsuoihito.anyban.utils

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss Z")

fun calcCurrentTimeString(): String {
    return simpleDateFormat.format(Calendar.getInstance().time)
}

fun calcAMonthLaterString(): String {
    val calendar = Calendar.getInstance()
    calendar.add(Calendar.MONTH, 1)
    return simpleDateFormat.format(calendar.time)
}

fun calcIsExpired(expiresOn: String): Boolean {
    return try {
        val expiresOnDate = simpleDateFormat.parse(expiresOn)
        val nowDate = Calendar.getInstance().time
        nowDate.time - expiresOnDate.time > 0
    } catch (e: ParseException) {
        true
    }
}
