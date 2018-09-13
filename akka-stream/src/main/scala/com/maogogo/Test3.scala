package com.maogogo

import akka.stream.scaladsl.Sink
import akka.stream.scaladsl.Flow
import akka.util.ByteString
import scala.concurrent.Future
import akka.stream.IOResult
import akka.stream.scaladsl.FileIO
import java.nio.file.Paths
import akka.stream.scaladsl.Keep
import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import akka.stream.scaladsl.Source
import akka.NotUsed
import akka.Done
import scala.concurrent.duration._

object Test3 extends App {

  def lineSink(filename: String): Sink[String, Future[IOResult]] = {
    Flow[String]
      .map(s ⇒ ByteString(s + "\n")).toMat(FileIO.toPath(Paths.get(filename)))(Keep.right)
  }

  implicit val sys = ActorSystem("QuickStart")
  implicit val mat = ActorMaterializer()

  val source: Source[Int, NotUsed] = Source(1 to 5)

  val done: Future[Done] = source.runForeach(println)

  val factorials = source.scan(BigInt(1))((acc, next) ⇒ acc * next)

  //  factorials.zipWith(that)(combine)

  factorials
    .zipWith(Source(0 to 5))((num, idx) ⇒ s"$idx! = $num")
    .throttle(1, 1.second)
    .runForeach(println)

}