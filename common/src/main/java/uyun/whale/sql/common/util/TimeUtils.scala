package uyun.whale.sql.common.util

import java.text.SimpleDateFormat
import java.util.concurrent.TimeUnit
import java.util.{Calendar, Date}

/**
  * 时间日期工具类
  *
  * @author chensw
  * @since 2016-3-31 下午3:11:08
  */
object TimeUtils {
    val SECOND: Long = TimeUnit.SECONDS.toMillis(1)
    val MINUTE: Long = TimeUnit.MINUTES.toMillis(1)
    val HOUR: Long = TimeUnit.HOURS.toMillis(1)
    val DAY: Long = TimeUnit.DAYS.toMillis(1)
    val TIME_ZONE_OFFSET: Long = Calendar.getInstance().getTimeZone.getOffset(0)
    val GTM_8_OFFSET: Long = TimeUnit.HOURS.toMillis(8)

    def leftMinute(time: Long): Long = {
        leftGap(time, MINUTE)
    }

    def leftGap(time: Long, gap: Long): Long = {
        time - time % gap
    }

    def getZero(time: Long, timeUnits: Int*): Long = {
        val calendar: Calendar = Calendar.getInstance()
        calendar.setTimeInMillis(time)
        timeUnits.foreach(timeUnit => {
            calendar.set(timeUnit, 0)
        })
        calendar.getTimeZone.setID("GMT+08:00")
        calendar.getTimeInMillis
    }

    def leftFiveMinute(time: Long): Long = {
        leftGap(time, 5 * MINUTE)
    }

    def leftFifteenMinute(time: Long): Long = {
        leftGap(time, 15 * MINUTE)
    }

    def leftThirtyMinute(time: Long): Long = {
        leftGap(time, 30 * MINUTE)
    }

    def leftTwelveHour(time: Long, timezoneOffset: Long = GTM_8_OFFSET): Long = {
        (time + timezoneOffset) / (12 * HOUR) * 12 * HOUR - timezoneOffset
    }

    def leftThreeHour(time: Long, timezoneOffset: Long = GTM_8_OFFSET): Long = {
        (time + timezoneOffset) / (3 * HOUR) * 3 * HOUR - timezoneOffset
    }

    def leftHour(time: Long): Long = {
        leftGap(time, HOUR)
    }

    def leftSevenDay(time: Long, timezoneOffset: Long = GTM_8_OFFSET): Long = {
        val calendar: Calendar = Calendar.getInstance()
        calendar.setTimeInMillis(time)
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY)
        leftDay(calendar.getTimeInMillis, timezoneOffset)
    }

    def leftDay(time: Long, timezoneOffset: Long = GTM_8_OFFSET): Long = {
        (time + timezoneOffset) / DAY * DAY - timezoneOffset
    }

    def rightDay(time: Long, timezoneOffset: Long = GTM_8_OFFSET): Long = {
        math.ceil((time + timezoneOffset) / DAY.toDouble).toLong * DAY - timezoneOffset
    }

    def isWorkDay(timestamp: Long): Boolean = {
        val cal = Calendar.getInstance()
        cal.setTimeInMillis(timestamp)
        val week = cal.get(Calendar.DAY_OF_WEEK)
        week != Calendar.SATURDAY && week != Calendar.SUNDAY
    }

    def getWeek(timestamp: Long): Int = {
        val cal = Calendar.getInstance()
        cal.setTimeInMillis(timestamp)
        cal.get(Calendar.DAY_OF_WEEK) - 1

    }

    def stringToLong(strTime: String, format: String): Long =
        stringToDate(strTime, format).getTime

    def stringToDate(dateString: String, format: String): Date =
        new SimpleDateFormat(format).parse(dateString)

    def dateToString(date: Date, format: String): String =
        new SimpleDateFormat(format).format(date)

    def longToString(timestamp: Long, format: String): String =
        new SimpleDateFormat(format).format(new Date(timestamp))

}
