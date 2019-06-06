package uyun.whale.sql.common.udf

import org.apache.spark.sql.Row
import org.apache.spark.sql.expressions.{MutableAggregationBuffer, UserDefinedAggregateFunction}

case class OverrideAggregator() extends UserDefinedAggregateFunction {

    import org.apache.spark.sql.types._

    override def inputSchema: StructType = StructType(Array(StructField("input", StringType)))

    override def bufferSchema: StructType = StructType(Array(StructField("first", StringType)))

    override def dataType: DataType = StringType

    override def deterministic: Boolean = true

    override def initialize(buffer: MutableAggregationBuffer): Unit = {
    }

    override def update(buffer: MutableAggregationBuffer, input: Row): Unit = {
        buffer(0) = input(0)
    }

    override def merge(buffer1: MutableAggregationBuffer, buffer2: Row): Unit = {
        if (buffer2(0) != null) buffer1(0) = buffer2(0)
    }

    override def evaluate(buffer: Row): Any = {
        buffer(0)
    }
}
