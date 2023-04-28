package sendgrid

import fabric.rw.RW
import spice.net.ContentType

import java.nio.file.{Files, Path}
import java.util.Base64

case class MessageAttachment(content: String, `type`: String, filename: String)

object MessageAttachment {
  implicit val rw: RW[MessageAttachment] = RW.gen

  def apply(path: Path, contentType: ContentType): MessageAttachment = {
    val bytes = Files.readAllBytes(path)
    val encoded = Base64.getEncoder.encodeToString(bytes)
    MessageAttachment(
      content = encoded,
      `type` = contentType.`type`,
      filename = path.getFileName.toString
    )
  }
}