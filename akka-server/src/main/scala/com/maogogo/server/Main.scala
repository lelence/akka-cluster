package com.maogogo.server

import com.typesafe.config.ConfigFactory
import akka.actor.ActorSystem
import akka.actor.Actor
import scala.concurrent.Future
import scala.annotation.tailrec
import akka.actor.Props
import scala.util.Random
import akka.pattern.pipe
import akka.util.Timeout
import scala.concurrent.duration._

object Main extends App {

  // val config = ConfigFactory load

  server(2551)

  server(2552)

  def server(port: Int): Unit = {
    val config = ConfigFactory.parseString(s"""
        akka.remote.netty.tcp.port=$port
        """).withFallback(ConfigFactory.parseString("akka.cluster.roles = [backend]"))
      .withFallback(ConfigFactory.load("factorial"))

    val system = ActorSystem("ClusterSystem", config)
    system.actorOf(Props[FactorialBackend], name = "factorialBackend")
    println(s"port: $port")

  }

}

class FactorialBackend extends Actor {

  implicit val timeout = Timeout(5 seconds)
  import context.dispatcher

  def receive = {
    case n: Int ⇒
      println("nn===>>>" + n)
      val random = Random.nextInt(100)
      Future.successful(n * random) pipeTo sender
  }

}