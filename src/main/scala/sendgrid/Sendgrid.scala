package sendgrid

import cats.effect.IO
import fabric._
import fabric.io.JsonParser
import fabric.rw._
import spice.http.client.HttpClient
import spice.net._

case class Sendgrid(apiKey: String) {
  private lazy val client = HttpClient
    .url(url"https://api.sendgrid.com/v3/mail/send")
    .header("Authorization", s"Bearer $apiKey")

  def send(message: Message): IO[Json] = client
    .post
    .json(message.json)
    .send()
    .map { response =>
      response.content.map { c =>
        val s = c.asString
        if (s == null || s.isEmpty) {
          obj()
        } else {
          JsonParser(s)
        }
      }.getOrElse(obj())
    }
}