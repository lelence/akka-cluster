
name := "akka-client"

version := "1.0"

scalaVersion := "2.12.6"

resolvers += "Aliyun" at "http://maven.aliyun.com/nexus/content/groups/public"

libraryDependencies ++= {
  val akkaVersion = "2.5.16"
  val slf4jVersion = "1.7.25"
  val logbackVersion = "1.2.3"
  
  Seq(
    "org.slf4j" % "slf4j-api" % slf4jVersion,
    "ch.qos.logback" % "logback-core" % logbackVersion,
    "ch.qos.logback" % "logback-classic" % logbackVersion,
    "ch.qos.logback" % "logback-access" % logbackVersion,
    "com.typesafe.scala-logging" %% "scala-logging" % "3.9.0",
    "com.typesafe.akka" %% "akka-actor" % akkaVersion,
    "com.typesafe.akka" %% "akka-slf4j" % akkaVersion,
    // "com.typesafe.akka" %% "akka-stream-experimental" % akkaStreamVersion,
    // "com.typesafe.akka" %% "akka-remote" % akkaVersion,
    "com.typesafe.akka" %% "akka-cluster" % akkaVersion,
    "com.typesafe.akka" %% "akka-cluster-tools" % akkaVersion,
    "com.typesafe.akka" %% "akka-cluster-metrics" % akkaVersion,
    "com.typesafe.akka" %% "akka-testkit" % akkaVersion % Test
  )
}