package ru.kosteloff.utils

import android.annotation.SuppressLint
import android.icu.util.Calendar
import android.os.Build
import androidx.annotation.RequiresApi
import java.text.SimpleDateFormat

object TimeUtils {

    @SuppressLint("SimpleDateFormat")
    val formatter = SimpleDateFormat("mm:ss")
    @SuppressLint("NewApi")
    fun getTime(time: Long): String {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = time
        return formatter.format(calendar.time)
    }
}