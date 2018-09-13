
name := "akka-slick"

version := "1.0"

scalaVersion := "2.12.6"

resolvers += "Aliyun" at "http://maven.aliyun.com/nexus/content/groups/public"

libraryDependencies ++= {
  val akkaVersion = "2.5.16"

  Seq(
    "com.typesafe.akka" %% "akka-actor" % akkaVersion,
    "com.lightbend.akka" %% "akka-stream-alpakka-slick" % "0.20",
     "mysql" % "mysql-connector-java" % "5.1.47",
    "com.typesafe.akka" %% "akka-testkit" % akkaVersion % Test,
    "org.scalatest" %% "scalatest" % "3.0.5" % Test
  )
}