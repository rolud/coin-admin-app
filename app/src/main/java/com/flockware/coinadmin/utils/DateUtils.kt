package com.flockware.coinadmin.utils

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

object DateUtils {
    fun today() : Date {
        val c = Calendar.getInstance()
        c[Calendar.HOUR_OF_DAY] = 0
        c[Calendar.MINUTE] = 0
        c[Calendar.SECOND] = 0
        c[Calendar.MILLISECOND] = 0
        return c.time
    }
}

fun Date.plusMonths(value: Int) : Date {
    val c = Calendar.getInstance()
    c.time = this
    c.add(Calendar.MONTH, value)
    return c.time
}

fun Date.minusMonths(value: Int) : Date {
    val c = Calendar.getInstance()
    c.time = this
    c.add(Calendar.MONTH, -value)
    return c.time
}

fun Date.nextMonth() : Date = this.plusMonths(1)
fun Date.previousMonth() : Date = this.minusMonths(1)

fun Date.getMonthDays() : Int {
    val cal = Calendar.getInstance()
    cal.time = this
    return cal.getActualMaximum(Calendar.DAY_OF_MONTH)
}

fun Date.getDayOfTheMonth(): Int {
    val cal = Calendar.getInstance()
    cal.time = this
    return cal[Calendar.DAY_OF_MONTH]
}

/**
 * @return day of the week according to Italian style
 * MON = 1, TUE = 2, WEN = 3, THU = 4,
 * FRI = 5, SAT = 6, SUN = 7
 */
fun Date.getDayOfTheWeek(): Int {
    val cal = Calendar.getInstance()
    cal.time = this
    return when (cal[Calendar.DAY_OF_WEEK]) {
        Calendar.SUNDAY -> 7
        else -> cal[Calendar.DAY_OF_WEEK] - 1
    }
}

fun Date.pattern(pattern: DatePattern) : String {
    val format = SimpleDateFormat(pattern.pattern, Locale.getDefault())
    return format.format(this)
}

fun String.pattern(pattern: DatePattern) : Date? {
    val format = SimpleDateFormat(pattern.pattern, Locale.getDefault())
    return try { format.parse(this) } catch (e: ParseException) { null }
}

enum class DatePattern(val pattern: String) {
    KEBAB_YEAR("yyyy-MM-dd"),
    SLASH_YEAR("dd/MM/yyyy"),
    EXPLICIT_MONTH_YEAR("MMMM yyyy"),
    EXPLICIT_DAY_MONTH_YEAR("dd MMMM yyyy")
}