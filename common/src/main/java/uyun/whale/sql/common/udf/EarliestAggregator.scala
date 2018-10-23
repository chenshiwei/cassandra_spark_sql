package uyun.whale.sql.common.udf

import org.apache.spark.sql.Row
import org.apache.spark.sql.expressions.{MutableAggregationBuffer, UserDefinedAggregateFunction}

case class EarliestAggregator() extends UserDefinedAggregateFunction {

  import org.apache.spark.sql.types._

  override def inputSchema: StructType = StructType(Array(StructField("input", StringType), StructField("timestamp", LongType)))

  override def bufferSchema: StructType = StructType(Array(StructField("earlier", StringType), StructField("timestamp", LongType)))

  override def dataType: DataType = StringType

  override def deterministic: Boolean = true

  override def initialize(buffer: MutableAggregationBuffer): Unit = {
    buffer(1) = Long.MaxValue
  }

  override def update(buffer: MutableAggregationBuffer, input: Row): Unit = {
    merge(buffer, input)
  }

  override def merge(buffer1: MutableAggregationBuffer, buffer2: Row): Unit = {
    if (Option(buffer1.getAs[Long](1)).getOrElse(Long.MaxValue) > Option(buffer2.getAs[Long](1)).getOrElse(Long.MaxValue)) {
      buffer1(0) = buffer2(0)
      buffer1(1) = buffer2(1)
    }
  }

  override def evaluate(buffer: Row): Any = {
    buffer(0)
  }
}
