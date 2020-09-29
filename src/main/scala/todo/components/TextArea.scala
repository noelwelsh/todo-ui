package todo
package components

import slinky.core._
import slinky.core.annotations.react
import slinky.core.facade.ReactRef
import slinky.web.html._
import org.scalajs.dom.html.TextArea


@react object TextArea {

  case class Props(id: String, description: String, ref: ReactRef[TextArea])

  val classes = """w-full
                  |p-2
                  |border-gray-500
                  |border
                  |focus:outline-none
                  |focus:border-teal-400""".stripMargin.replace('\n', ' ')

  val component = FunctionalComponent[Props]{ props =>
    div(className := "mb-2")(
      Label(id = props.id, description = props.description),
      textarea(className := classes,
               name := props.id,
               id := props.id,
               ref := props.ref)
    )
  }
}
