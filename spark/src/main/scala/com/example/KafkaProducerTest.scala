package com.example

import java.util.Properties
import org.apache.kafka.clients.producer.{KafkaProducer, ProducerRecord}

object KafkaProducerTest {
  def main(args: Array[String]): Unit = {
    val props = new Properties()
    props.put("bootstrap.servers", "localhost:9092") // Kafka 服务器地址
    props.put(
      "key.serializer",
      "org.apache.kafka.common.serialization.StringSerializer"
    ) // 键的序列化器
    props.put(
      "value.serializer",
      "org.apache.kafka.common.serialization.StringSerializer"
    ) // 值的序列化器

    val producer = new KafkaProducer[String, String](props) // 创建 Kafka 生产者实例

    val topic = "sex" // 要发送消息的主题

    try {
      // 循环发送消息
      for (i <- 1 to 100) {
        val message = s"Message $i" // 要发送的消息内容
        val record =
          new ProducerRecord[String, String](topic, message) // 创建消息记录
        producer.send(record) // 发送消息
        println(s"Sent message: $message")
        Thread.sleep(1000) // 休眠1秒
      }
    } catch {
      case e: Exception => e.printStackTrace()
    } finally {
      producer.close() // 关闭生产者
    }
  }
}
