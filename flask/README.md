# 项目结构

- data目录存放的是用户日志数据；

  数据集压缩包为[data_format.zip](https://pan.baidu.com/s/1cs02Nc),该数据集压缩包是淘宝2015年双11前6个月(包含双11)的交易数据(交易数据有偏移，但是不影响实验的结果)，里面包含3个文件，分别是用户行为日志文件user_log.csv 、回头客训练集train.csv 、回头客测试集test.csv. 在这个案例中只是用user_log.csv这个文件，下面列出文件user_log.csv的数据格式定义：

    用户行为日志user_log.csv，日志中的字段定义如下：
    ```txt
    1. user_id | 买家id
    2. item_id | 商品id
    3. cat_id | 商品类别id
    4. merchant_id | 卖家id
    5. brand_id | 品牌id
    6. month | 交易时间:月
    7. day | 交易事件:日
    8. action | 行为,取值范围{0,1,2,3},0表示点击，1表示加入购物车，2表示购买，3表示关注商品
    9. age_range | 买家年龄分段：1表示年龄=50,0和NULL则表示未知
    10. gender | 性别:0表示女性，1表示男性，2和NULL表示未知
    11. province| 收获地址省份
    ```
    数据具体格式如下：
    ```csv
    user_id,item_id,cat_id,merchant_id,brand_id,month,day,action,age_range,gender,province
    328862,323294,833,2882,2661,08,29,0,0,1,内蒙古
    328862,844400,1271,2882,2661,08,29,0,1,1,山西
    328862,575153,1271,2882,2661,08,29,0,2,1,山西
    328862,996875,1271,2882,2661,08,29,0,1,1,内蒙古
    328862,1086186,1271,1253,1049,08,29,0,0,2,浙江
    328862,623866,1271,2882,2661,08,29,0,0,2,黑龙江
    328862,542871,1467,2882,2661,08,29,0,5,2,四川
    328862,536347,1095,883,1647,08,29,0,7,1,吉林
    ```

- scripts目录存放的是Kafka生产者和消费者；
  试用kafka连接是否正常
- static/js目录存放的是前端所需要的js框架；
- templates目录存放的是html页面；
- app.py为web服务器，接收Structed Streaming处理后的结果，并推送实时数据给浏览器；

# 环境配置

- 安装python

    python==3.7

- 创建并激活虚拟环境 (Optional)

    ```bash
    python -m .venv venv
    ./.venv/Scripts/activate
    ```

- 安装packages

    ```bash
    pip install -r requirements.txt
    ```

# 启动
- 提交spark任务

    [具体看这里](../spark/README.md#运行)

- 启动生产者，模拟数据流

    `python scripts/producer.py`

- 启动app.py

    `python app.py`
