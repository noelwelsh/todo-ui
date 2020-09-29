package todo
package components

import slinky.core._
import slinky.core.annotations.react
import slinky.web.html._


@react object Button {
  import ClassName._

  case class Props(title: String, handler: () => ())

  val classes = """w-full
                  |p-2
                  |bg-gradient-to-r
                  |from-teal-400
                  |to-blue-500
                  |text-white
                  |hover:bg-gradient-to-r
                  |hover:from-teal-500
                  |hover:to-blue-600
                  |focus:outline-none
                  |focus:shadow-outline""".asClassNames

  val component = FunctionalComponent[Props]{ props =>
    button(className := classes,
           `type` := "button",
           onClick := props.handler)(props.title)
  }
}
