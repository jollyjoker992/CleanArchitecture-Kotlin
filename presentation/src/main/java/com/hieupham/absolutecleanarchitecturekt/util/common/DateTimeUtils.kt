package com.hieupham.absolutecleanarchitecturekt.util.common

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class DateTimeUtils {

    companion object {
        const val DATE_TIME_FORMAT_1 = "yyyy-MM-DD'T'HH:mm:ss.SSSSSS'Z'"
        const val DATE_TIME_FORMAT_2 = "yyyy MMM DD HH:mm:ss"

        fun changeFormat(dateStr: String, inputFormat: String, outputFormat: String): String? {
            val millis = stringToMillis(dateStr, inputFormat)
            return if (millis == -1L) null else millisToStr(millis, outputFormat)
        }

        fun changeFormat(dateStr: String, outputFormat: String): String? {
            return changeFormat(dateStr, DATE_TIME_FORMAT_1, outputFormat)
        }

        fun millisToStr(millis: Long, format: String): String {
            val sdf = SimpleDateFormat(format, Locale.getDefault())
            val calendar = Calendar.getInstance()
            calendar.timeInMillis = millis
            val date = calendar.time
            return sdf.format(date)
        }

        fun millisToDate(millis: Long): Date {
            val calendar = Calendar.getInstance()
            calendar.timeInMillis = millis
            return calendar.time
        }

        fun stringToMillis(dateStr: String, format: String): Long {
            val sdf = SimpleDateFormat(format, Locale.getDefault())
            return try {
                val date = sdf.parse(dateStr)
                date.time
            } catch (e: ParseException) {
                -1
            } catch (e: NullPointerException) {
                -1
            }

        }
    }
}