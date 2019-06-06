package uyun.whale.sql.core.util

import uyun.whale.sql.common.util.TimeUtils

import scala.beans.BeanProperty

class SparkSQLHelper {
    @BeanProperty
    var sql: String = _

    def show(): Unit = {
        sql.show(50, false)
    }

    def show(str: String): Unit = {
        str.show(1000, false)
    }

    def saveCsv(): Unit = {
        val path = getDir
        sql.repartition(1).write.option("header", "true").option("timestampFormat", "yyyy/MM/dd HH:mm:ss ZZ")
            .csv(path)
        println(s"Done $path")

    }

    def saveJson(): Unit = {
        val path = getDir
        sql.repartition(1).write.option("timestampFormat", "yyyy/MM/dd HH:mm:ss ZZ")
            .json(path)
        println(s"Done $path")
    }

    def getDir: String = {
        val token: Array[String] = sql.toLowerCase.split(" ")
        s"$dataPath/${token(token.indexOf("from") + 1)}_${
            TimeUtils.longToString(System.currentTimeMillis(),
                "yyyy_MM_dd_HH_mm_ss")
        }"
    }


}
