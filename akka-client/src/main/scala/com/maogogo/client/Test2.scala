package com.maogogo.client

import akka.actor.Actor
import akka.actor.ActorLogging
import akka.actor.ActorSystem
import akka.actor.Props
import com.typesafe.config.ConfigFactory
import org.slf4j.LoggerFactory
import akka.event.slf4j.Slf4jLogger
import akka.event.LogSource

object Test2 extends App {

  // val  logger = LoggerFactory.getLogger(getClass)
  val config = ConfigFactory.load("logworker.conf")
  val system = ActorSystem("LogSystem", config)
  import akka.event.slf4j.Slf4jLoggingFilter
  val worker = system.actorOf(Props[Worker], "log_worker")

  worker ! "Toan"

}

class Worker extends Actor with com.typesafe.scalalogging.LazyLogging {
  def receive: Actor.Receive = {
    case s: String ⇒
      logger.info("hahahha =>>" + s)
  }
}