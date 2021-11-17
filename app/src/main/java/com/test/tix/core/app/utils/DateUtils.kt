package com.test.tix.core.app.utils

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import com.test.tix.R
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

object DateUtils {

    @SuppressLint("SimpleDateFormat")
    fun stringTimeAgo(timeAgo: String?, context: Context): String {
        var convTime: String? = null
        var convTimeAt: String? = null

        try {
            val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")

            var pasTime = dateFormat.parse(timeAgo)

            val nowTime = Date()
            val dateDiff = nowTime.time - pasTime.time
            val seconds = TimeUnit.MILLISECONDS.toSeconds(dateDiff)
            val minutes = TimeUnit.MILLISECONDS.toMinutes(dateDiff)
            val hours = TimeUnit.MILLISECONDS.toHours(dateDiff)
            val days = TimeUnit.MILLISECONDS.toDays(dateDiff)
            val weeks = TimeUnit.MILLISECONDS.toDays(dateDiff / 7)

            if (seconds < 60) {
                convTime = seconds.toString() + " " + context.resources.getString(R.string.seconds_ago)
            } else if (minutes < 60) {
                convTime = minutes.toString() + " " + context.resources.getString(R.string.minutes_ago)
            } else if (hours < 24) {
                convTime = hours.toString() + " " + context.resources.getString(R.string.hours_ago)
            } else if (days >= 7) {
                convTime = weeks.toString() + " " + context.resources.getString(R.string.weeks_ago)
                if (days > 30) {
                    convTime = (days / 30).toString() + " " + context.resources.getString(R.string.months_ago)
                } else if (days > 360) {
                    convTime = (days / 360).toString() + " " + context.resources.getString(R.string.years_ago)
                } else {
                }
            } else if (days < 7) {
                convTime = days.toString() + " " + context.resources.getString(R.string.days_ago)
            }

        } catch (e: ParseException) {
            e.printStackTrace()
            Log.e("ConvTimeE", e.message!!)
        }

        return convTime ?: convTimeAt!!
    }
}