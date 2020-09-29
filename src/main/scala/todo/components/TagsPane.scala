package todo
package components

import slinky.core._
import slinky.core.annotations.react
import slinky.core.facade.ReactElement
import slinky.web.html._
import todo.data.Tags

@react object TagsPane {
  import ClassName._

  val tagClasses = """border
                     |border-gray-600
                     |rounded
                     |flex-grow-0
                     |text-gray-600
                     |p-1
                     |text-xs""".asClassNames

  val activeTagClasses = """border-teal-600
                           |text-teal-600 """.asClassNames ++ tagClasses

  val inactiveTagClasses = """border-gray-600
                          |text-gray-600
                          |hover:border-teal-600
                          |hover:text-teal-600 """.asClassNames ++ tagClasses

  case class Props(
      tags: Tags,
      currentFilter: Option[todo.data.Tag],
      updateFilter: App.UpdateFilter
  )

  val component = FunctionalComponent[Props] { props =>
    div(className := "p-8")(
      h2(className := "mb-4 text-xl font-bold text-gray-700")("Tags"),
      div(className := "border-b pb-4 mb-4")(
        h3(className := "mb-2 text-lg font-bold text-gray-600")(
          "Current Filter"
        ),
        div(className := "flex")(
          props.currentFilter
            .map[ReactElement](t =>
              Tag(
                tag = t,
                status = Tag.Active,
                clickHandler = None,
                deleteHandler = Some(() => props.updateFilter(None))
              )
            )
            .getOrElse(
              p(className := "text-gray-500")("None")
            )
        )
      ),
      div(className := "border-b pb-4 mb-4")(
        h3(className := "mb-2 text-lg font-bold text-gray-600")("All Tags"),
        div(className := "flex flex-wrap gap-1")(
          props.tags.tags.zipWithIndex.map {
            case (t, idx) =>
              div(
                key := idx.toString,
                className := inactiveTagClasses,
                onClick := (_ => props.updateFilter(Some(t)))
              )(t.tag)
          }
        )
      )
    )
  }
}
