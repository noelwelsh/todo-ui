package todo

import slinky.core._
import slinky.core.facade.Hooks._
import slinky.core.annotations.react
import slinky.web.html._

import scala.scalajs.js
import scala.scalajs.js.annotation.JSImport

import todo.data._
import todo.components._

@JSImport("resources/App.css", JSImport.Default)
@js.native
object AppCSS extends js.Object

@JSImport("resources/logo.svg", JSImport.Default)
@js.native
object ReactLogo extends js.Object

@react object App {
  implicit val ec: scala.concurrent.ExecutionContext =
    scala.concurrent.ExecutionContext.global

  type UpdateState = () => Unit
  type AddTask = Task => Unit
  type DeleteTask = Id => Unit
  type CompleteTask = Id => Unit
  type UncompleteTask = (Id, Task) => Unit
  type UpdateFilter = Option[todo.data.Tag] => Unit

  private val css = AppCSS

  type Props = Unit
  val component = FunctionalComponent[Unit] { _ =>
    val (tasks, updateTasks) = useState(Tasks.empty)
    val (tags, updateTags) = useState(Tags.empty)
    val (tagFilter, updateTagFilter) = useState(Option.empty[todo.data.Tag])

    useEffect(updateState, List(tagFilter))

    def updateState: UpdateState =
      () => {
        tagFilter.fold(Api.tasks)(tag => Api.tasks(tag)).foreach { tasks =>
          updateTasks(tasks)
        }
        Api.tags.foreach { tags => updateTags(tags) }
      }

    def addTask: AddTask =
      task => Api.create(task).foreach(_ => updateState())
    def deleteTask: DeleteTask =
      id => Api.delete(id).foreach(_ => updateState())
    def completeTask: CompleteTask =
      id => Api.complete(id).foreach(_ => updateState())
    def uncompleteTask: UncompleteTask =
      (id, task) => Api.update(id, task.uncomplete).foreach(_ => updateState())
    def updateFilter: UpdateFilter =
      filter => { updateTagFilter(filter) }

    div(className := "App")(
      div(className := "md:container mx-auto p-8")(
        div(className := "flex mb-4")(
          div(className := "w-1/4 bg-white")(
            TagsPane(
              tags = tags,
              currentFilter = tagFilter,
              updateFilter = updateTagFilter
            )
          ),
          div(className := "w-3/4 bg-white")(
            TaskListView(
              tasks = tasks,
              complete = completeTask,
              uncomplete = uncompleteTask,
              updateFilter = updateFilter
            ),
            TaskEditor(addTask)
          )
        )
      )
    )
  }
}
