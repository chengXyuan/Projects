package com.daking.lottery.util

import java.text.SimpleDateFormat
import java.util.*

class FormatUtils {

    private object Holder {
        val INSTANCE = FormatUtils()
    }

    companion object {
        val instance = Holder.INSTANCE
    }

    fun convertDateTime(timestamp: Long): String {
        if (timestamp <= 0L) {
            return ""
        }
        val format = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        format.timeZone = TimeZone.getTimeZone("GMT+08")
        return format.format(timestamp)
    }

    fun convertTime(timestamp: Long): String {
        if (timestamp <= 0L) {
            return ""
        }
        val format = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
        format.timeZone = TimeZone.getTimeZone("GMT+08")
        return format.format(timestamp)
    }

    fun seconds2Time(seconds: Long): String {
        if (seconds < 0) {
            return "--:--"
        }
        val hour = seconds / 3600
        val minute = seconds % 3600 / 60
        val second = seconds % 3600 % 60
        return if (hour == 0L) String.format("%02d:%02d", minute, second)
        else String.format("%02d:%02d:%02d", hour, minute, second)
    }

    fun getHour(seconds: Long): String {
        if (seconds < 0) {
            return "00"
        }
        val hour = seconds / 3600
        return String.format("%02d", hour)
    }

    fun getMinute(seconds: Long): String {
        if (seconds < 0) {
            return "00"
        }
        val minute = seconds % 3600 / 60
        return String.format("%02d", minute)
    }

    fun getSecond(seconds: Long): String {
        if (seconds < 0) {
            return "00"
        }
        val second = seconds % 3600 % 60
        return String.format("%02d", second)
    }

    fun formatBankNum(bankNum: String): String {
        if (bankNum.isBlank()) {
            return bankNum
        }
        val originNum = bankNum.trim().replace(" ", "")
        val num = originNum.replace("(?<=\\d{4})\\d(?=\\d{3})".toRegex(), "*")
        return num.foldIndexed("") { index, acc, i ->
            if (index % 4 == 3) {
                acc + i + " "
            } else {
                acc + i
            }
        }
    }
}