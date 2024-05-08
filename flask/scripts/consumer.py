from kafka import KafkaConsumer

# 配置 Kafka 服务器地址
bootstrap_servers = 'localhost:9092'

# 实例化一个 KafkaConsumer 示例，用于从 Kafka 消费消息
consumer = KafkaConsumer(
    'sex',
    bootstrap_servers=bootstrap_servers,
    api_version=(3,7,0),
    group_id='my-group'  # 使用消费者组以便于协同消费消息
)

# 持续监听并打印接收到的消息
for message in consumer:
    print(message.value.decode("utf-8"))
