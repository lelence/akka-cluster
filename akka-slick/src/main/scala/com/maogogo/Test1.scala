package com.maogogo

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import slick.basic.DatabaseConfig
import slick.jdbc.JdbcProfile
import akka.stream.scaladsl._
import akka.stream.alpakka.slick.scaladsl._
import slick.jdbc.GetResult
import scala.concurrent.Future
import akka.Done
import slick.sql.SqlAction

object Test1 extends App {

  implicit val system = ActorSystem()
  implicit val mat = ActorMaterializer()
  implicit val ec = system.dispatcher

  val databaseConfig = DatabaseConfig.forConfig[JdbcProfile]("slick-mysql")
  implicit val session = SlickSession.forConfig(databaseConfig)

  implicit val getUserResult = GetResult(r ⇒ User(r.nextString, r.nextString))

  import session.profile.api._

  val dd = sql"SELECT ID, NAME FROM T_TEST".as[User]

  val dropTable = sqlu"""DROP TABLE  if exists ALPAKKA_SLICK_SCALADSL_TEST_USERS"""

  val done: Future[Seq[User]] =
    Slick
      .source(sql"SELECT ID, NAME FROM T_TEST".as[User])
      .log("user")
      .runWith(Sink.seq)

  def insertUser(u: User): DBIO[Int] =
    sqlu"insert into t_test(id, name) values(${u.id}, ${u.name})"

  val users = (1 to 40).map(i ⇒ User(i.toString, s"Name$i")).toSet

  //  Slick.flowWithPassThrough(parallelism, toStatement)

  val ds = Source(users).via(Slick.flow(insertUser)).runWith(Sink.seq).map(_.sum).map { x ⇒

    println("insert into ==>>" + x)

  }
  // .toMat(Sink.seq[Int])(Keep.right).run

  val e = session.db.run(dropTable).map { x ⇒
    println("11111 =>>" + x)
  }

  done.map {
    _.foreach { x ⇒
      println("xxx =>>" + x.id + "###" + x.name)
    }
  }

  //  val done: Future[Done] =
  //    Slick
  //      .source()
  //      .log("user")
  //      .runwi

}

case class User(id: String, name: String)