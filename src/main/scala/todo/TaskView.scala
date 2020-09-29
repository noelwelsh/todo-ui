package todo

import slinky.core._
import slinky.core.annotations.react
import slinky.web.html._

import todo.components._
import todo.data

@react object TaskView {
  import ClassName._

  val completeClasses = """border
                          |border-gray-700
                          |text-gray-700
                          |rounded
                          |mr-2
                          |p-2
                          |inline-block
                          |hover:border-teal-500
                          |hover:text-teal-500""".asClassNames

  val uncompleteClasses = """border
                            |border-gray-600
                            |text-gray-600
                            |rounded
                            |mr-2
                            |inline-block
                            |hover:border-teal-500
                            |hover:text-teal-500""".asClassNames

  case class Props(
      id: data.Id,
      task: data.Task,
      complete: App.CompleteTask,
      uncomplete: App.UncompleteTask,
      updateFilter: App.UpdateFilter
  )
  val component = FunctionalComponent[Props] {
    case Props(id, task, complete, uncomplete, updateFilter) =>
      def clickHandler(): Unit =
        complete(id)

      task.state match {
        case data.Active =>
          li(
            key := id.toString,
            className := "task bg-white mb-8 pb-2 border-b"
          )(
            div(className := "mb-2 text-gray-700 block flex items-center")(
                div(className := completeClasses, onClick := clickHandler _),
              h4(className := "text-lg font-bold inline-block")(
                task.description
              )
            ),
            div(className := "flex flex-wrap gap-1 mb-4")(
              task.tags.map { t =>
                Tag(
                  tag = t,
                  status = Tag.Inactive,
                  clickHandler = Some(() => updateFilter(Some(t))),
                  deleteHandler = None
                )
              }
            ),
            task.notes
              .map(s => p(className := "text-gray-600")(s))
              .getOrElse(div())
            // Button("Done", clickHandler _)
          )

        case c: data.Completed =>
          li(
            key := id.toString,
            className := "task bg-white mb-8 pb-2 border-b"
          )(
            div(className := "flex items-center")(
              MaterialIcons.MdCheck(className := uncompleteClasses,
                                    onClick := (() => uncomplete(id, task))),
              p(className := "description strikethrough text-gray-500")(task.description)
            )
          )
      }
  }
}
