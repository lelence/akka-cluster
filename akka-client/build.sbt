
name := "akka-client"

version := "1.0"

scalaVersion := "2.12.6"

resolvers += "Aliyun" at "http://maven.aliyun.com/nexus/content/groups/public"

libraryDependencies ++= {
  val akkaVersion = "2.5.16"

  Seq(
    "com.typesafe.akka" %% "akka-actor" % akkaVersion,
    // "com.typesafe.akka" %% "akka-stream-experimental" % akkaStreamVersion,
    // "com.typesafe.akka" %% "akka-remote" % akkaVersion,
    "com.typesafe.akka" %% "akka-cluster" % akkaVersion,
    "com.typesafe.akka" %% "akka-cluster-tools" % akkaVersion,
    "com.typesafe.akka" %% "akka-cluster-metrics" % akkaVersion,
    "com.typesafe.akka" %% "akka-testkit" % akkaVersion % Test
  )
}