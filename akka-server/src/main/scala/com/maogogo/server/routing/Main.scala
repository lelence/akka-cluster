package com.maogogo.server.routing

import akka.actor.Actor
import akka.actor.ActorSystem
import com.typesafe.config.ConfigFactory
import akka.routing.BroadcastGroup
import akka.actor.ActorRef
import akka.actor.Props
import akka.routing.Broadcast
import akka.routing.RoundRobinGroup
import akka.actor.Timers
import scala.concurrent.duration._
import akka.pattern.ask
import akka.util.Timeout
import akka.routing.AddRoutee
import akka.routing.Routee
import akka.routing.GetRoutees
import akka.routing.RemoveRoutee
import akka.routing.ActorRefRoutee
import scala.util.Random
import akka.routing.ActorSelectionRoutee
import akka.actor.ActorSelection
import akka.routing.Routees

case class AA(i: Int, actor: ActorRef)
case class CC(i: Int, s: String)

object Main extends App {

  val config = ConfigFactory.load("routing")
  val sys = ActorSystem("MySystem", config)

  sys.actorOf(Props[WorkerRoutee], "w1")
  sys.actorOf(Props[WorkerRoutee], "w2")
  sys.actorOf(Props[WorkerRoutee], "w3")

  val paths = List("/user/w1", "/user/w2", "/user/w3")

  val router4: ActorRef =
    sys.actorOf(BroadcastGroup(paths).props(), "router4")

  val router8: ActorRef =
    sys.actorOf(RoundRobinGroup(paths).props(), "router8")

  sys.actorOf(Props(classOf[WorkerBroadcast], router4, router8), "wb")
  sys.actorOf(Props(classOf[WorkerRoundRobinActor], router8), "wr")

}

class WorkerRoundRobinActor(router: ActorRef) extends Actor with Timers {

  import context.dispatcher
  // timers.startPeriodicTimer("bb", "hahah", 7 seconds)

  implicit val timeout = Timeout(5 seconds)

  def receive: Actor.Receive = {
    case s: String ⇒
      router ! Random.nextInt(100)
    case c: CC ⇒
      println("cc =>>" + c.i + "###" + c.s)
  }

}

class WorkerBroadcast(router: ActorRef, kk: ActorRef) extends Actor with Timers {

  import context.dispatcher

  // timers.startSingleTimer("aa", "hahah", 3 seconds)
  timers.startPeriodicTimer("aa", "hahah", 15 seconds)
  implicit val timeout = Timeout(5 seconds)

  def receive: Actor.Receive = {
    case s: String ⇒
      // router ! Broadcast("112233")
      kk ! RemoveRoutee(ActorSelectionRoutee(context.actorSelection("/user/w1")))
      //      kk ! RemoveRoutee(ActorSelectionRoutee(context.actorSelection("/usr/w2")))
      //      kk ! RemoveRoutee(ActorSelectionRoutee(context.actorSelection("/usr/w3")))
      //
      //      kk ! AddRoutee(ActorSelectionRoutee(context.actorSelection("/usr/w3")))

      kk ! GetRoutees
    case r: Routees ⇒
      println("rr ==>>>" + r)
    //      println(r.routees)
  }
}

class WorkerRoutee extends Actor {
  def receive: Actor.Receive = {
    case i: Int ⇒
      println("i ==>>>" + i)
      sender ! CC(i, self.path.toString)
    case s: String ⇒
      // println(s"=${s}=>>>" + self.path)
      sender ! AA(Random.nextInt(100), self)
  }
}