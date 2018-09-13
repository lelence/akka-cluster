package com.maogogo

import akka.stream.scaladsl.Source
import akka.NotUsed
import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import scala.concurrent.Future
import akka.Done
import akka.stream.IOResult
import akka.util.ByteString
import akka.stream.scaladsl.FileIO
import java.nio.file.Paths

object Test1 extends App {

  implicit val sys = ActorSystem("QuickStart")
  implicit val mat = ActorMaterializer()

  val source: Source[Int, NotUsed] = Source(1 to 10)

  val done: Future[Done] = source.runForeach(println)
  
  val factorials = source.scan(BigInt(1))((acc, next) ⇒ acc * next)

  val result: Future[IOResult] =
    factorials
      .map(num ⇒ ByteString(s"$num\n"))
      .runWith(FileIO.toPath(Paths.get("factorials.txt")))

  implicit val ec = sys.dispatcher
  done.onComplete(_ ⇒ sys.terminate())
}