package todo
package data

import io.circe._

final case class Id(id: Int) extends AnyVal
object Id {
  implicit val idCodec = new Codec[Id]{
    def apply(c: HCursor): Decoder.Result[Id] =
      for {
        id <- c.downField("id").as[Int]
      } yield Id(id)

    def apply(id: Id): Json =
      Json.obj("id" -> Json.fromInt(id.id))
  }
}
