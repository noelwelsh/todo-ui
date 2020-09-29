package todo
package data

import io.circe._
import io.circe.syntax._

final case class Task(
    state: State,
    description: String,
    notes: Option[String],
    tags: List[Tag]
) {
  def uncomplete: Task =
    this.copy(state = Active)
}
object Task {
  implicit val taskCodec = new Codec[Task] {
    def apply(c: HCursor): Decoder.Result[Task] =
      for {
        state <- c.downField("state").as[State]
        description <- c.downField("description").as[String]
        notes <- c.downField("notes").as[Option[String]]
        tags <- c.downField("tags").as[List[Tag]]
      } yield Task(state, description, notes, tags)

    def apply(t: Task): Json =
      Json.obj(
        "state" -> t.state.asJson,
        "description" -> Json.fromString(t.description),
        "notes" -> t.notes.asJson,
        "tags" -> t.tags.asJson
      )
  }
}
