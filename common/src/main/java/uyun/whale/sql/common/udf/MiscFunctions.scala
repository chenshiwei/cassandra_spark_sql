package uyun.whale.sql.common.udf

import com.alibaba.fastjson.JSON
import uyun.whale.sql.common.util.TimeUtils


class MiscFunctions extends Serializable {

    def toJson: Map[String, String] => String = map => {
        import scala.collection.JavaConverters._
        JSON.toJSON(map.asJava).toString
    }

    def windowTimestamp: (Long, String) => Long = (timestamp, timeType) => {
        timeType match {
            case "1min" => TimeUtils.leftMinute(timestamp)
            case "5min" => TimeUtils.leftFiveMinute(timestamp)
            case "15min" => TimeUtils.leftFifteenMinute(timestamp)
            case "30min" => TimeUtils.leftThirtyMinute(timestamp)
            case "1h" | "1hour" => TimeUtils.leftHour(timestamp)
            case "3h" | "3hour" => TimeUtils.leftThreeHour(timestamp)
            case "12h" | "12hour" => TimeUtils.leftTwelveHour(timestamp)
            case "1d" | "1day" => TimeUtils.leftDay(timestamp)
            case "7d" | "7day" => TimeUtils.leftSevenDay(timestamp)
            case _ => timestamp
        }
    }

    def latest = LatestAggregator()

    def earliest = EarliestAggregator()

    def overrideFunc = OverrideAggregator()

}

  
  