# coding: utf-8
import csv
import time
from pathlib import PurePath
from kafka import KafkaProducer
here = PurePath(__file__).parent

# 配置 Kafka 服务器地址
bootstrap_servers = 'localhost:9092'

# 实例化一个KafkaProducer示例，用于向Kafka投递消息
producer = KafkaProducer(
    bootstrap_servers=bootstrap_servers,
    api_version=(3,7,0)
)

# 打开数据文件
with open(here / "../data/user_log.csv", "r", encoding="utf-8") as csvfile:
    # 生成一个可用于读取csv文件的reader
    reader = csv.reader(csvfile)

    for line in reader:
        gender = line[9]  # 性别在每行日志代码的第9个元素
        if gender == "gender":
            continue  # 去除第一行表头
        time.sleep(0.1)  # 每隔0.1秒发送一行数据
        # 发送数据，topic为'sex'
        # print(gender)
        producer.send("sex", gender.encode("utf8"))
