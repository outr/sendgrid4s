package sendgrid

import fabric.rw._

case class MessageContent(`type`: String, value: String)

object MessageContent {
  implicit val rw: RW[MessageContent] = RW.gen
}