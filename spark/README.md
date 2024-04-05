# 构建项目

## sbt

- 教程

    - 视频
      https://www.bilibili.com/video/BV13d4y1a7qa

    - 文档
      https://www.scala-sbt.org/1.x/docs/

- 问题

    - 依赖问题

        ```bash
        sbt:Simple Project> compile
        [error] stack trace is suppressed; run last update for the full output
        [error] (update) lmcoursier.internal.shaded.coursier.error.FetchError$DownloadingArtifacts: Error fetching artifacts:
        [error] file:/C:/Users/hunte/.m2/repository/org/slf4j/slf4j-api/1.7.30/slf4j-api-1.7.30.jar: not found: C:\Users\hunte\.m2\repository\org\slf4j\slf4j-api\1.7.30\slf4j-api-1.7.30.jar
        [error] file:/C:/Users/hunte/.m2/repository/org/apache/yetus/audience-annotations/0.5.0/audience-annotations-0.5.0.jar: not found: C:\Users\hunte\.m2\repository\org\apache\yetus\audience-annotations\0.5.0\audience-annotations-0.5.0.jar
        [error] Total time: 4 s, completed 2024-4-4 10:13:04
        ```
        用maven安装上对应版本的jar包就行，对应仓库地址：
        - [audience-annotations](https://mvnrepository.com/artifact/org.apache.yetus/audience-annotations)
        - [slf4j-api](https://mvnrepository.com/artifact/org.slf4j/slf4j-api)

    - 运行时问题

        防止运行时因为依赖出现问题，加载一个sbt的[plugin](https://www.scala-sbt.org/1.x/docs/Using-Plugins.html)：[sbt-assembly](https://github.com/sbt/sbt-assembly)

    - 提交的spark任务连接不上kafka

        一个6年前的[blog](https://rmoff.net/2018/08/02/kafka-listeners-explained/)解决了我的问题，其中最重要的一张图片：

        ![](https://raw.githubusercontent.com/Hunter9812/Jordan/main/img/kafka-listeners-explained.png)

# 运行

- 编译

    ```bash
    sbt assembly
    ```

- 复制jar包和脚本到Volumes

    ```bash
    cp .\target\scala-2.12\simple-project-assembly-1.0.jar ~\docker\spark\
    cp .\startup.sh ~\docker\spark\
    ```

- 提交任务

    - 连接docker容器

        ```bash
        docker exec -it spark-master bash
        ```

    - 启动脚本

        ```bash
        sh /root/startup.sh
        ```
