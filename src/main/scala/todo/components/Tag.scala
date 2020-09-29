package todo
package components

import slinky.core._
import slinky.core.annotations.react
import slinky.web.html._
import todo.data

@react object Tag {
  import ClassName._

  val tagClasses = """border
                     |border-gray-600
                     |rounded
                     |flex-grow-0
                     |text-gray-600
                     |text-xs""".asClassNames

  val activeTagClasses = """border-teal-600
                           |text-teal-600""".asClassNames

  val inactiveTagClasses = """border-gray-600
                             |text-gray-600""".asClassNames

  val hoverTagClasses = """hover:border-teal-600
                          |hover:text-teal-600 """.asClassNames

  sealed trait Status
  final case object Active extends Status
  final case object Inactive extends Status

  type ClickHandler = () => Unit
  type DeleteHandler = () => Unit

  case class Props(
      tag: data.Tag,
      status: Status,
      clickHandler: Option[ClickHandler],
      deleteHandler: Option[DeleteHandler]
  )

  val component = FunctionalComponent[Props] {
    case Props(tag, status, cH, dH) =>
      val classNames =
        tagClasses
          .withClassNames(status match {
            case Active   => activeTagClasses
            case Inactive => inactiveTagClasses
          })
          .withClassNames(cH.fold("")(_ => hoverTagClasses))
          .withClassNames("items-center p-1 flex-grow-0 flex gap-1")

      div(
        key := tag.tag.toString,
        className := classNames,
        onClick := cH.getOrElse(() => ())
      )(
        span(className := "block-inline")(tag.tag),
        dH.fold(span()())(f =>
          MaterialIcons.MdClose(
            className := tagClasses
              .withClassNames(hoverTagClasses)
              .withClassNames(""),
            onClick := f
          )
        )
      )
  }
}
