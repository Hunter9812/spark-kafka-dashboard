# Spark+Kafka构建实时分析Dashboard案例

学习的是[林子雨的Spark课程实验案例](https://dblab.xmu.edu.cn/post/spark-kafka-dashboard/)，开发环境提供了docker脚本(仅供学习使用)。


## [docker](.container/README.md)

[Marcel-Jan的项目](https://github.com/Marcel-Jan/docker-hadoop-spark)，整合了hadoop、spark、hive的docker环境，其他都正常，就是hive不会自动启动。我修改了docker-compose.yml文件，编排了kafka的服务(这部分是copy的[kafka官方docker的例子](https://github.com/apache/kafka/blob/trunk/docker/examples/jvm/single-node/plaintext/docker-compose.yml))。

**PS**

  - 在本地的`~/docker/spark/`目录下有volume:

    - spark容器中的家目录:复制jar包到家目录

    - spark的work目录:提交任务时的jar包、stderr和stdout

  - 启动`docker-compose up`


## [sparkApp](spark/README.md): Spark应用

## [flask](flask/README.md): web应用和kafka生产者的脚本
