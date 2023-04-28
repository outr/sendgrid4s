package sendgrid

import fabric._
import fabric.define.DefType
import fabric.rw._
import spice.net.EmailAddress

case class Message(from: EmailAddress,
                   to: List[EmailAddress],
                   cc: List[EmailAddress] = Nil,
                   subject: String,
                   content: List[MessageContent],
                   attachments: List[MessageAttachment] = Nil,
                   templateId: Option[String] = None,
                   sendAt: Option[Long] = None)

object Message {
  implicit val rw: RW[Message] = RW.from[Message](
    r = (message: Message) => obj(
      "personalizations" -> arr(
        obj(
          "to" -> message.to.map { e =>
            obj("email" -> e.json)
          },
          "cc" -> message.cc.map { e =>
            obj("email" -> e.json)
          }
        )
      ),
      "from" -> obj("email" -> message.from.json),
      "subject" -> message.subject,
      "content" -> message.content.json,
      "attachments" -> message.attachments.json,
      "template_id" -> message.templateId.json,
      "send_at" -> message.sendAt.json
    ),
    w = (json: Json) => Message(
      from = json("from" \ "email").as[EmailAddress],
      to = json("personalizations" \ "to").asVector.map(_("email").as[EmailAddress]).toList,
      subject = json("subject").asString,
      content = json("content").asArr.as[List[MessageContent]],
      attachments = json("attachments").as[List[MessageAttachment]],
      templateId = json("template_id").as[Option[String]],
      sendAt = json("send_at").as[Option[Long]]
    ),
    d = DefType.Dynamic
  )
}