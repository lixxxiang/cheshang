package com.android.lixiang.cheshang.ui.fragment

import android.annotation.SuppressLint
import com.blankj.utilcode.util.TimeUtils
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class Utils{
    private val MIN_DELAY_TIME = 1000  // 两次点击间隔不能少于1000ms
    private var lastClickTime: Long = 0

    fun isFastClick(): Boolean {
        var flag = true
        val currentClickTime = System.currentTimeMillis()
        if (currentClickTime - lastClickTime >= MIN_DELAY_TIME) {
            flag = false
        }
        lastClickTime = currentClickTime
        return flag
    }


    fun timeFormat(): String {
        var HOUR = TimeUtils.getNowString().split(" ")[1].split(":")[0]
        val DATE = TimeUtils.getNowString().split(" ")[0].replace("-", "/")
        val YEAR = DATE.split("/")[0].substring(0, DATE.split("/")[0].length)
        val MONTH = DATE.split("/")[1]
        val DAY = DATE.split("/")[2]
        val WEEK = getWeek(DATE)
        return "$YEAR/$MONTH/$DAY $WEEK"
    }

    @SuppressLint("SimpleDateFormat")
    fun getWeek(time: String): String {
        var Week = ""
        val format = SimpleDateFormat("yyyy/MM/dd")
        val c = Calendar.getInstance()
        try {
            c.time = format.parse(time)
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        val wek = c.get(Calendar.DAY_OF_WEEK)

        if (wek == 1) {
            Week += "周日"
        }
        if (wek == 2) {
            Week += "周一"
        }
        if (wek == 3) {
            Week += "周二"
        }
        if (wek == 4) {
            Week += "周三"
        }
        if (wek == 5) {
            Week += "周四"
        }
        if (wek == 6) {
            Week += "周五"
        }
        if (wek == 7) {
            Week += "周六"
        }
        return Week
    }
}