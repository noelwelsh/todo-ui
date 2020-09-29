package todo

import slinky.core._
import slinky.core.annotations.react
import slinky.web.html._

import todo.data._

@react object TaskListView {
  case class Props(
      tasks: Tasks,
      complete: App.CompleteTask,
      uncomplete: App.UncompleteTask,
      updateFilter: App.UpdateFilter
  )
  val component = FunctionalComponent[Props] { props =>
    div(className := "p-8")(
      h1(className := "mb-4 text-3xl font-bold text-gray-700")("Tasks"),
      ul(className := "task-list")(
        props.tasks.tasks.map {
          case (id, t) =>
            TaskView(
              id,
              task = t,
              complete = props.complete,
              uncomplete = props.uncomplete,
              updateFilter = props.updateFilter
            )
        }
      )
    )
  }
}
