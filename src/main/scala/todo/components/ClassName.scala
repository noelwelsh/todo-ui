package todo
package components

object ClassName {
  implicit class classNameSyntax(classNames: String) {
    def asClassNames: String =
      classNames.stripMargin.replace('\n', ' ')

    def withClassNames(that: String): String =
      s"${classNames} ${that}"
  }
}
