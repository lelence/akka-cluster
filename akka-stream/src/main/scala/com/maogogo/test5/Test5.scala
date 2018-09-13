package com.maogogo.test5

import akka.stream.scaladsl.Source
import akka.stream.scaladsl.Sink
import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import akka.stream.OverflowStrategy

object Test5 extends App {

  implicit val sys = ActorSystem("QuickStart")
  implicit val mat = ActorMaterializer()

  val d = Source(List(1, 2, 3))
    .map(_ + 1).async
    .map(_ * 2).runForeach(println)
  // .to(Sink.ignore)

  val matValuePoweredSource =
    Source.actorRef[String](bufferSize = 100, overflowStrategy = OverflowStrategy.fail)

  val (actorRef, source) = matValuePoweredSource.preMaterialize()

  actorRef ! "Hello!"

  // pass source around for materialization
  source.runWith(Sink.foreach(println))

}