package todo
package data

import scala.scalajs.js
import io.circe._


sealed trait State
case object Active extends State
final case class Completed(date: js.Date) extends State
object State {
  implicit val stateCodec = new Codec[State] {
    def apply(c: HCursor): Decoder.Result[State] =
      c.downField("state").as[String].flatMap {
        case "active" =>
          Right(Active)

        case "completed" =>
          c.downField("date")
            .as[String]
            .map(s => new js.Date(js.Date.parse(s)))
            .map(d => Completed(d))

        case err =>
          Left(
            DecodingFailure(
              s"The task type ${err} is not one of the expected types of 'active' or 'completed'",
              List.empty
            )
          )
      }

    def apply(s: State): Json =
      s match {
        case Active =>
          Json.obj("state" -> Json.fromString("active"))
        case Completed(date) =>
          Json.obj(
            "state" -> Json.fromString("completed"),
            "date" -> Json.fromString(date.toString)
          )
      }
  }
}
