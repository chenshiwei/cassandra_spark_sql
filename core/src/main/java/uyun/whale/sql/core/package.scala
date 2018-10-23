package uyun.whale.sql.core

import java.io.File

import ch.qos.logback.classic.Level
import org.apache.spark.sql.{DataFrame, SparkSession}
import uyun.whale.sql.common.conf.LogBack

trait BaseConfig {
  LogBack.setDefaultLevel(Level.WARN)

  implicit def convertToDF(sql: String): DataFrame = {
    try {
      Configuration.getSparkSession.sql(sql)
    } catch {
      case e: Throwable =>
        println(e.getMessage)
        null
    }
  }

}

package object util extends BaseConfig{
  val dataPath: String = s"${System.getProperty("app.home")}/temp"
}