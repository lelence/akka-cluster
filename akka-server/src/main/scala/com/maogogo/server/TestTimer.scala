package com.maogogo.server

import akka.actor.Actor
import akka.actor.Timers
import scala.concurrent.duration._

object MyActor {
  private case object TickKey
  private case object FirstTick
  private case object Tick
}

class MyActor extends Actor with Timers {

  import MyActor._

  timers.startSingleTimer(TickKey, FirstTick, 500.millis)

  def receive = {
    case FirstTick ⇒
      timers.startPeriodicTimer(TickKey, Tick, 1.second)
    case Tick ⇒
      println("1111111")
    // do something useful here
  }

}