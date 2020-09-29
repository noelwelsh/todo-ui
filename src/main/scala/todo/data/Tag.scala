package todo
package data

import io.circe._


final case class Tag(tag: String)
object Tag {
  implicit val tagCodec = new Codec[Tag] {
    def apply(c: HCursor): Decoder.Result[Tag] =
      c.downField("tag").as[String].map(n => Tag(n))

    def apply(t: Tag): Json =
      Json.obj("tag" -> Json.fromString(t.tag))
  }
}
