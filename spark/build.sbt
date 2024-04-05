scalaVersion := "2.12.19"

version := "1.0"
name := "simple-project"
organization := "com.example"

val sparkVersion = "3.0.0"
// 没有用hadoop-clients
val hadoopVersion = "3.2.1"
libraryDependencies ++= Seq(
  // provided的意思是不会被打包到jar中
  "org.apache.spark" %% "spark-core" % sparkVersion % "provided",
  "org.json4s" %% "json4s-jackson" % "3.7.0-M5" % "provided",
  "org.apache.spark" %% "spark-sql" % sparkVersion % "provided",
  "org.apache.spark" %% "spark-sql-kafka-0-10" % sparkVersion,
  "org.apache.spark" %% "spark-streaming" % sparkVersion % "provided",
  "org.apache.spark" %% "spark-streaming-kafka-0-10" % sparkVersion,
  "org.apache.spark" %% "spark-token-provider-kafka-0-10" % sparkVersion
)

// 测试学习
libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.18" % Test

// 冲突依赖：保留第一个，遗弃剩下的依赖
assemblyMergeStrategy := {
  case PathList("org", "apache", "spark", "unused", _ @_*) =>
    MergeStrategy.first
  // 其他冲突文件的处理
  case x => MergeStrategy.defaultMergeStrategy(x)
}
