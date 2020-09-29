package todo
package components

import slinky.core._
import slinky.core.annotations.react
import slinky.core.facade.ReactRef
import slinky.web.html._
import org.scalajs.dom.html.Input


@react object TextInput {
  import ClassName._

  case class Props(id: String, description: String, ref: ReactRef[Input])

  val inputClasses = """w-full
                       |p-2
                       |border-gray-500
                       |border-b border-t-0 border-l-0 border-r-0
                       |focus:outline-none
                       |focus:border-teal-400""".asClassNames

  val component = FunctionalComponent[Props]{ props =>
    div(className := "mb-2")(
      Label(id = props.id, description = props.description),
      input(className := inputClasses,
            `type` := "text",
            name := props.id,
            id := props.id,
            ref := props.ref)
    )
  }
}
