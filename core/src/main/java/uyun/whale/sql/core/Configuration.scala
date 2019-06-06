package uyun.whale.sql.core

import java.util.Properties

import org.apache.spark.sql.SparkSession
import org.apache.spark.{SparkConf, SparkContext}

import scala.io.Source

/**
  * Test和小程序依然使用此类,如数据重跑,清理等
  *
  * @author chensw
  * @since at 2016-4-14下午3:04:46
  */

object Configuration {

    private var sparkConf: SparkConf = _
    private var spark: SparkSession = _
    private var properties: Properties = _

    private var keySpace: String = _

    def getProperties: Properties = {
        if (properties == null) {
            getSparkConf()
            properties
        } else properties
    }


    /**
      * 获取配置好的sc(SparkContext)
      */
    def getSc: SparkContext = getSparkSession.sparkContext

    def getSparkSession: SparkSession = {
        if (spark == null) {
            try {
                spark = SparkSession.builder().config(getSparkConf).getOrCreate()
                registerAll()
            } catch {
                case e: Exception =>
                    println(e.getMessage)
                    spark = null
            }
        }
        spark
    }

    /**
      * 加载spark,redis,cassandra等配置
      *
      * @param reload :是否重新加载
      * @return
      */
    def load(register: Boolean = true, reload: Boolean = false): Unit = {

        // 0: 是否重新加载配置
        if (reload && spark != null) {
            closeAll()
        }

        // 1: 读入配置文件,并将所有的配置赋值到sparkConf
        sparkConf = getSparkConf

        // 5 创建SparkSession
        spark = SparkSession.builder().config(sparkConf).getOrCreate()
        if (register) registerAll()
    }

    def closeAll(): Unit = {
        spark.stop()
        spark.close()
        sparkConf = null
        properties = null
    }

    def registerAll(): Unit = {

        spark.read.format("org.apache.spark.sql.cassandra")
            .options(Map("keyspace" -> "system_schema", "table" -> "tables"))
            .load.createOrReplaceTempView("all_tables")
        println(s"keyspace = $getKeySpace")
        println("The register tables as follow:")
        spark.sql(s"select table_name from all_tables where keyspace_name='$getKeySpace'")
            .collect().foreach(row => {
            println(row.getString(0))
            spark.read.format("org.apache.spark.sql.cassandra")
                .options(Map("keyspace" -> getKeySpace, "table" -> row.getString(0)))
                .load.createOrReplaceTempView(row.getString(0))
        })

        import uyun.whale.sql.common.udf.MiscFunctions
        val functions = new MiscFunctions
        println("\nThe register User-Defined function as follow:")
        println("override:User-Defined Aggregate Function FOR STRING group by")
        println("earlier:User-Defined Aggregate Function FOR STRING group by")
        println("window_timestamp: User-Defined Function timestamp  e.g. window_timestamp(timestamp,mode)")
        println("""mode = '1min'|'5min'|'15min'|'30min'|'1h'|'1hour'|'3h'|'3hour'|'12h'|'12hour'|'1d'|'1day'|'7d'|'7day' """)
        println("See another example as follows:")
        spark.udf.register("override", functions.overrideFunc)
        spark.udf.register("earlier", functions.earliest)
        spark.udf.register("window_timestamp", functions.windowTimestamp)


    }

    def getKeySpace: String = {
        if (keySpace == null) getSparkConf.get("cassandra.keyspace", "uem_octopus")
        else keySpace
    }

    def setKeySpace(keySpace: String): Unit = this.keySpace = keySpace

    def getSparkConf: SparkConf = if (sparkConf == null) getSparkConf() else this.sparkConf

    def setSparkConf(sparkConf: SparkConf): Unit = this.sparkConf = sparkConf

    /**
      * 首次启动调用配置文件，将所有需要的配置加载到sparkConf中
      */
    def getSparkConf(
                        pathList: List[String] = List("src/test/resources/sql.properties"),
                        local: Boolean = false): SparkConf = synchronized {
        if (sparkConf == null) {
            properties = new Properties()
            pathList.foreach(path => properties.load(Source.fromFile(path).bufferedReader()))
            import scala.collection.JavaConverters._
            sparkConf = new SparkConf().setAll(properties.asScala)
            if (local) sparkConf.set("spark.master", "local[8]")
        }
        this.keySpace = sparkConf.get("cassandra.keyspace", "system")
        this.sparkConf
    }

}