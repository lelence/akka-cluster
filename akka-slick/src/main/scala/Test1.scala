import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import slick.basic.DatabaseConfig
import slick.jdbc.JdbcProfile

import akka.stream.scaladsl._
import akka.stream.alpakka.slick.scaladsl._
import slick.jdbc.GetResult
import scala.concurrent.Future
import akka.Done

object Test1 extends App {

  implicit val system = ActorSystem()
  implicit val mat = ActorMaterializer()
  implicit val ec = system.dispatcher

  val databaseConfig = DatabaseConfig.forConfig[JdbcProfile]("slick-mysql")
  implicit val session = SlickSession.forConfig(databaseConfig)

  implicit val getUserResult = GetResult(r ⇒ User(r.nextString, r.nextString))

  import session.profile.api._

  val done: Future[Done] =
    Slick
      .source(sql"SELECT ID, NAME FROM T_TEST".as[User])
      .log("user")
      .runWith(Sink.ignore)

  done.onComplete {
    case _ ⇒

      val d = getUserResult
//      com.mysql.jdbc.Driver
      println(d)

      println("1111111111")

    //      session.close()
    //      system.terminate()
  }
}

case class User(id: String, name: String)