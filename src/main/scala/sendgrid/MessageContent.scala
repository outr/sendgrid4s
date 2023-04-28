package sendgrid

import fabric.rw.RW

case class MessageContent(`type`: String, value: String)

object MessageContent {
  implicit val rw: RW[MessageContent] = RW.gen
}