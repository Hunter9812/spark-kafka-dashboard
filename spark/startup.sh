/spark/bin/spark-submit \
    --class com.example.KafkaWordCount \
    --master spark://spark-master:7077 \
    --deploy-mode cluster \
    /root/simple-project-assembly-1.0.jar broker:19092 sex hdfs://namenode:9000/check
