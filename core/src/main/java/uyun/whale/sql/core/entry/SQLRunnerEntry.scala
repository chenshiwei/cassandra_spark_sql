package uyun.whale.sql.core.entry

import uyun.whale.sql.common.conf.LogBack
import uyun.whale.sql.core.util.SparkSQLHelper

object SQLRunnerEntry {

  def main(args: Array[String]): Unit = {
    var appHome = s"${System.getProperty("user.dir")}/build"
    if (!System.getProperty("os.name").toLowerCase().contains("windows")) {
      appHome = args(0)
    }
    System.setProperty("app.home", appHome)
    LogBack.configureLogbackPath(s"$appHome/conf/logback.xml")
    println(s"The config file is '$appHome/conf/sql.properties'")
    import uyun.whale.sql.core.Configuration._
    val sparkConf = getSparkConf(List(s"$appHome/conf/sql.properties"))
    sparkConf.getAll.filter(_._1 != "spark.jars").foreach { conf =>
      if (conf._1.contains("pass")) println(s"${conf._1} = ******")
      else println(s"${conf._1} = ${conf._2}")
    }
    load()
    val runner = new SparkSQLHelper
    val test =
      s""" SELECT ${System.currentTimeMillis()} AS timestamp,
         | window_timestamp(${System.currentTimeMillis()},"12h") AS left12h""".stripMargin
    println("if use write as follows :")
    println(s"> ${test.replace("\n", "\n> ")};")
    runner.show(test)
    println("Good! You can use it | Remark: if you want to move cursor in linux, you need to press 'Ctrl' ")
    while (true) {
      println("input the SQL end with ';' | use 'show tables;' to see the tables which you can use | use 'exit;' to exit")
      var sql = ""
      var part = ""
      while (!part.endsWith(";")) {
        part = Console.readLine(">")
        sql += part
      }
      if (sql.toLowerCase.startsWith("exit")) {
        closeAll()
        System.exit(0)
      }
      if (sql.toLowerCase.replace(";","").trim == "show tables") {
        runner.show(s"select table_name from all_tables where keyspace_name='$getKeySpace'")
      } else {
        runner.setSql(sql.replace(";", ""))
        try {
          runner.show()
          val flag: String = Console.readLine("save (no/csv/json):")
          flag.toLowerCase.trim match {
            case "csv" => runner.saveCsv()
            case "json" => runner.saveJson()
            case "no" => println("Don't save !")
            case _ => println("Undefined!")
          }
        } catch {
          case e: Exception => println(s"ERROR:${e.getMessage}")
        }
      }
    }

  }

}