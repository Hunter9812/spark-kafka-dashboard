package org.apache.spark.examples.streaming

import java.util.HashMap
import org.apache.kafka.clients.producer.{
  KafkaProducer,
  ProducerConfig,
  ProducerRecord
}
import org.json4s._
import org.json4s.jackson.Serialization
import org.json4s.jackson.Serialization.write
import org.apache.spark.SparkConf
import org.apache.spark.streaming._
import org.apache.spark.streaming.Interval
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.apache.spark.streaming.kafka010._
import org.apache.spark.streaming.kafka010.LocationStrategies.PreferConsistent
import org.apache.spark.streaming.kafka010.ConsumerStrategies.Subscribe
import java.sql.Timestamp
import org.apache.spark.sql.streaming.OutputMode
import org.apache.spark.sql.{
  Row,
  ForeachWriter,
  DataFrame,
  Dataset,
  SparkSession
}
import scala.util.parsing.json.{JSONArray, JSONObject}

object KafkaWordCount {
  implicit val formats = DefaultFormats // 数据格式化时需要
  def processed(row: Row, bootstrapServers: String): String = {
    var jsonstring = "[{\"" + row(0) + "\": \"" + row(1) + "\"}]"
    val props = new HashMap[String, Object]()
    props.put(
      ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,
      bootstrapServers
    )
    props.put(
      ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
      "org.apache.kafka.common.serialization.StringSerializer"
    )
    props.put(
      ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
      "org.apache.kafka.common.serialization.StringSerializer"
    )
    // todo 创建Kafka生产者对象
    val producer = new KafkaProducer[String, String](props)

    // todo 创建数据
    // rdd.colect即将rdd中数据转化为数组，然后write函数将rdd内容转化为json格式
    val str = jsonstring
    // public ProducerRecord(String topic, K key, V value) {
    val message = new ProducerRecord[String, String]("result", null, str)

    // todo 通过生产者对象将数据发送给Kafka
    producer.send(message)

    jsonstring
  }
  def main(args: Array[String]): Unit = {
    if (args.length != 3) {
      System.err.println(
        "Usage: KafkaWordCount <bootstrapServers> <topics> <checkpointLocation>"
      )
      System.exit(1)
    }
    StreamingExamples.setStreamingLogLevels()
    val Array(bootstrapServers, topics, checkpointLocation) = args

    val spark = SparkSession
      .builder()
      .appName("KafkaWordCount")
      .getOrCreate()
    // 创建连接Kafka的消费者链接

    import spark.implicits._
    val df = spark.readStream
      .format("kafka")
      .option("kafka.bootstrap.servers", bootstrapServers)
      .option("subscribe", topics)
      .option("failOnDataLoss", "false")
      .load()

    val dataSet: Dataset[(String, Timestamp)] =
      df.selectExpr("CAST(value AS STRING)", "CAST(timestamp AS TIMESTAMP)")
        .as[(String, Timestamp)]
    val words = dataSet.toDF("value", "timestamp")

    import org.apache.spark.sql.functions._
    // 根据窗口、开始id分组
    val windowsCount = words
      .withWatermark("timestamp", "1 seconds")
      .groupBy(
        window($"timestamp", "1 seconds", "1 seconds"),
        $"value"
      )
      .count()

    val ds = windowsCount
      .selectExpr("CAST(value AS STRING)", "CAST(count AS STRING)")
      .writeStream
      .foreach(
        new ForeachWriter[Row] {
          def open(partitionId: Long, version: Long): Boolean = true
          def process(record: Row): Unit = processed(record, bootstrapServers)
          def close(errorOrNull: Throwable): Unit = {}
        }
      )
      .option("checkpointLocation", checkpointLocation)
      .outputMode("append")
      .start()

    ds.awaitTermination()
  }
}
