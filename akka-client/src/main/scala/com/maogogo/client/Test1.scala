package com.maogogo.client

import com.typesafe.config.ConfigFactory
import akka.actor.Actor
import akka.routing.FromConfig
import akka.pattern.ask
import akka.util.Timeout
import scala.concurrent.duration._
import scala.util.Random
import akka.actor.ActorSystem
import akka.cluster.Cluster
import akka.actor.Props

object Test1 extends App {

  val config = ConfigFactory.parseString("akka.cluster.roles = [frontend]").
    withFallback(ConfigFactory.load("factorial"))

  val system = ActorSystem("ClusterSystem", config)

  system.log.info("Factorials will start when 2 backend members in the cluster.")

  Cluster(system) registerOnMemberUp {
    system.actorOf(
      Props[FactorialFrontend],
      name = "factorialFrontend")
  }
}

class FactorialFrontend extends Actor {

  import context.dispatcher

  val backend = context.actorOf(
    FromConfig.props(),
    name = "factorialBackendRouter")

  override def preStart(): Unit = {
    context.system.scheduler.schedule(5 seconds, 10 seconds, self, Random.nextInt(100))
  }

  implicit val timeout = Timeout(5 seconds)
  def receive = {
    case n: Int ⇒
      // println("nn ==>>>" + n)
      println("bbb ==>>" + backend)
      backend ! 10
    //      (backend ? n).mapTo[Int].foreach { x ⇒
    //        println("resp ==>>>" + x)
    //      }
  }

}