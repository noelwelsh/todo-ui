package todo

import io.circe.Decoder
import io.circe.parser.parse
import io.circe.syntax._
import org.scalajs.dom
import scala.scalajs.js
import scala.concurrent.Future
import org.scalajs.dom.experimental._
import todo.data._

object Api {
  implicit val ec: scala.concurrent.ExecutionContext =
    scala.concurrent.ExecutionContext.global

  val host = "http://localhost:3000"

  def applicationJson = {
    val h = new Headers()
    h.append("Content-Type", "application/json")
    h
  }

  def tasks: Future[Tasks] = {
    val ri = defaultRequest
    ri.method = HttpMethod.GET
    ri.mode = RequestMode.cors

    Fetch
      .fetch(s"${host}/api/tasks", ri)
      .toFuture
      .flatMap(r => r.text().toFuture)
      .map{ s =>
        Decoder[Tasks].decodeJson(parse(s).toOption.get) match {
          case Left(err)    => throw err
          case Right(tasks) => tasks
        }}
  }

  def tasks(tag: Tag): Future[Tasks] = {
    val ri = defaultRequest
    ri.method = HttpMethod.GET
    ri.mode = RequestMode.cors

    Fetch
      .fetch(s"${host}/api/tasks/${tag.tag}", ri)
      .toFuture
      .flatMap(r => r.text().toFuture)
      .map{ s =>
        Decoder[Tasks].decodeJson(parse(s).toOption.get) match {
          case Left(err)    => throw err
          case Right(tasks) => tasks
        }}
  }

  def tags: Future[Tags] = {
    val ri = defaultRequest
    ri.method = HttpMethod.GET
    ri.mode = RequestMode.cors

    Fetch
      .fetch(s"${host}/api/tags", ri)
      .toFuture
      .flatMap(r => r.text().toFuture)
      .map{ s =>
        Decoder[Tags].decodeJson(parse(s).toOption.get) match {
          case Left(err)    => throw err
          case Right(tags) => tags
        }}
  }

  def create(task: Task): Future[Id] = {
    val ri = defaultRequest
    ri.method = HttpMethod.POST
    ri.mode = RequestMode.cors
    ri.headers = applicationJson
    ri.body = task.asJson.spaces2

    val r = new Request(s"${host}/api/task", ri)

    dom.experimental.Fetch
      .fetch(r)
      .toFuture
      .flatMap(r => r.text().toFuture)
      .map(s =>
        Decoder[Id].decodeJson(parse(s).toOption.get) match {
          case Left(err) => throw err
          case Right(id) => id
        }
      )
  }

  def complete(id: Id): Future[Task] = {
    val ri = defaultRequest
    ri.method = HttpMethod.POST
    ri.mode = RequestMode.cors

    val r = new Request(s"${host}/api/task/${id.id}/complete", ri)

    dom.experimental.Fetch
      .fetch(r)
      .toFuture
      .flatMap(r => r.text().toFuture)
      .map(s =>
        Decoder[Task].decodeJson(parse(s).toOption.get) match {
          case Left(err)   => throw err
          case Right(task) => task
        }
      )
  }

  def update(id: Id, task: Task): Future[Option[Task]] = {
    val ri = defaultRequest
    ri.method = HttpMethod.POST
    ri.mode = RequestMode.cors
    ri.headers = applicationJson
    ri.body = task.asJson.spaces2

    val r = new Request(s"${host}/api/task/${id.id}", ri)

    dom.experimental.Fetch
      .fetch(r)
      .toFuture
      .flatMap(r => r.text().toFuture)
      .map(s =>
        Decoder[Option[Task]].decodeJson(parse(s).toOption.get) match {
          case Left(err) => throw err
          case Right(optTask) => optTask
        }
      )
  }

  def delete(id: Id): Future[Unit] = {
    val ri = defaultRequest
    ri.method = HttpMethod.DELETE
    ri.mode = RequestMode.cors

    val r = new Request(s"${host}/api/task/${id.id}", ri)

    Fetch
      .fetch(r)
      .toFuture
      .map(r => if (r.ok) () else throw new Exception("Delete failed"))
  }

  def defaultRequest: RequestInit =
    new RequestInit {
      var method: js.UndefOr[HttpMethod] = js.undefined
      var headers: js.UndefOr[HeadersInit] = js.undefined
      var body: js.UndefOr[BodyInit] = js.undefined
      var referrer: js.UndefOr[String] = js.undefined
      var referrerPolicy: js.UndefOr[ReferrerPolicy] = js.undefined
      var mode: js.UndefOr[RequestMode] = js.undefined
      var credentials: js.UndefOr[RequestCredentials] = js.undefined
      var cache: js.UndefOr[RequestCache] = js.undefined
      var redirect: js.UndefOr[RequestRedirect] = js.undefined
      var integrity: js.UndefOr[String] = js.undefined
      var keepalive: js.UndefOr[Boolean] = js.undefined
      var signal: js.UndefOr[AbortSignal] = js.undefined
      var window: js.UndefOr[Null] = js.undefined
    }
}
