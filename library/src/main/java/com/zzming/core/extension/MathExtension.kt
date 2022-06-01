package com.zzming.core.extension

import android.text.TextUtils
import java.math.RoundingMode
import java.text.DecimalFormat

fun String?.toDoubleRound(round: Int = 2): String? {
    if (this == null || round < 1) {
        return this
    }
    var format = "0."
    for (i in 0..round) {
        format += "#"
    }
    val decimalFormat = DecimalFormat(format)
    decimalFormat.roundingMode = RoundingMode.HALF_UP
    return decimalFormat.format(this.toDouble())
}

fun String?.tryToInt(): String? {
    try {
        if (TextUtils.isEmpty(this)) {
            return this
        }
        return this?.toDouble()?.toInt().toString()
    } catch (e: Exception) {
        return this
    }
}