package com.vikination.spaceflightnewsapp.data.utils

import java.text.SimpleDateFormat
import java.util.Locale
import java.util.TimeZone

object DateFormatter {
    fun formatDate(apiDate: String?): String {
        return try {
            val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
            inputFormat.timeZone = TimeZone.getTimeZone("UTC") // API sends UTC time

            val outputFormat = SimpleDateFormat("dd MMMM yyyy, HH:mm", Locale.getDefault())
            val date = apiDate?.let { inputFormat.parse(it) } ?: return "Invalid Date"

            outputFormat.format(date)
        } catch (e: Exception) {
            "Invalid Date"
        }
    }
}