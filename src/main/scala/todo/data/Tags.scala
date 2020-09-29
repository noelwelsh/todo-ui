package todo
package data

import io.circe._
import io.circe.syntax._


final case class Tags(tags: List[Tag])
object Tags {
  val empty = Tags(List.empty)

  implicit val tagsDecoder = new Codec[Tags]{
    def apply(c: HCursor): Decoder.Result[Tags] =
      c.as[List[Tag]].map(tags => Tags(tags))

    def apply(t: Tags): Json =
      t.tags.asJson
  }
}
