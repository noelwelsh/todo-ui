package todo
package data

import io.circe._
import io.circe.syntax._

final case class Tasks(tasks: List[(Id, Task)]) {
  def add(t: (Id, Task)): Tasks =
    Tasks(tasks :+ t)

  def replace(id: Id, task: Task): Tasks =
    Tasks(tasks.map{ case (i, t) => if(i == id) (i -> task) else (i -> t) })
}
object Tasks {
  val empty: Tasks = Tasks(List.empty)

  val elementDecoder = new Decoder[(Id, Task)] {
    def apply(c: HCursor): Decoder.Result[(Id, Task)] = {
      for {
        id <- c.downField("id").as[Int]
        task <- c.downField("task").as[Task]
      } yield (Id(id) -> task)
    }
  }

  implicit val tasksCodec = new Codec[Tasks] {
    def apply(c: HCursor): Decoder.Result[Tasks] = {
      c.as(Decoder.decodeList(elementDecoder)).map(t => Tasks(t))
    }

    def apply(t: Tasks): Json =
      Json.arr(
        t.tasks.map {
          case (id, task) =>
            Json.obj("id" -> Json.fromInt(id.id), "task" -> task.asJson)
        }: _*
      )
  }
}
