
name := "akka-stream"

version := "1.0"

scalaVersion := "2.12.6"

libraryDependencies ++= {
  val akkaVersion = "2.5.16"

  Seq(
    "com.typesafe.akka" %% "akka-actor" % akkaVersion,
    "com.typesafe.akka" %% "akka-stream" % akkaVersion,
    
    "com.typesafe.akka" %% "akka-testkit" % akkaVersion % Test,
    "org.scalatest" %% "scalatest" % "3.0.5" % Test
  )
}