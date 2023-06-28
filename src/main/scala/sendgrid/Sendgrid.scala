package sendgrid

import cats.effect.IO
import fabric._
import fabric.filter.RemoveEmptyFilter
import fabric.io.JsonParser
import fabric.rw._
import spice.http.client.{HttpClient, RetryManager}
import spice.net._

import scala.concurrent.duration.DurationInt

case class Sendgrid(apiKey: String) {
  private lazy val client = HttpClient
    .url(url"https://api.sendgrid.com/v3/mail/send")
    .header("Authorization", s"Bearer $apiKey")
    .retryManager(RetryManager.simple(5, 5.seconds))

  def send(message: Message): IO[Option[String]] = client
    .post
    .json(message.json.filterOne(RemoveEmptyFilter))
    .send()
    .flatMap { response =>
      response.content match {
        case Some(c) => c.asString.map(s => JsonParser(s))
        case None => IO.pure(obj())
      }
    }
    .map { json =>
      json.get("errors" \ 0 \ "message").map(_.asString)
    }
}