enablePlugins(ScalaJSBundlerPlugin)

name := "todo-ui"

Global / onChangedBuildSource := ReloadOnSourceChanges
ThisBuild / scalaVersion := "2.13.3"
ThisBuild / useSuperShell := false

npmDependencies in Compile += "react" -> "16.13.1"
npmDependencies in Compile += "react-dom" -> "16.13.1"
npmDependencies in Compile += "react-proxy" -> "1.1.8"

npmDevDependencies in Compile += "file-loader" -> "6.0.0"
npmDevDependencies in Compile += "style-loader" -> "1.2.1"
npmDevDependencies in Compile += "css-loader" -> "3.5.3"
npmDevDependencies in Compile += "postcss-loader" -> "4.0.0"
npmDevDependencies in Compile += "postcss" -> "7.0.32"
npmDevDependencies in Compile += "html-webpack-plugin" -> "4.3.0"
npmDevDependencies in Compile += "copy-webpack-plugin" -> "5.1.1"
npmDevDependencies in Compile += "webpack-merge" -> "4.2.2"
npmDevDependencies in Compile += "tailwindcss" -> "1.8.5"
npmDevDependencies in Compile += "autoprefixer" -> "9.8.6"
npmDevDependencies in Compile += "react-icons" -> "3.11.0"

libraryDependencies ++= Seq(
  "me.shadaj" %%% "slinky-web" % "0.6.6",
  "me.shadaj" %%% "slinky-hot" % "0.6.6",
  "org.scala-js" %%% "scalajs-dom" % "1.0.0",
  // "com.softwaremill.sttp.client" %%% "core" % "2.1.0",
  // "io.monix" %%% "monix" % "3.2.0"
)

val circeVersion = "0.13.0"

libraryDependencies ++= Seq(
  "io.circe" %%% "circe-core",
  "io.circe" %%% "circe-parser"
).map(_ % circeVersion)

libraryDependencies += "org.scalatest" %%% "scalatest" % "3.1.1" % Test

scalacOptions += "-Ymacro-annotations"
scalacOptions += "-Wunused:imports"
scalacOptions += "-Werror"

version in webpack := "4.43.0"
version in startWebpackDevServer:= "3.11.0"

webpackResources := baseDirectory.value / "webpack" * "*"

webpackConfigFile in fastOptJS := Some(baseDirectory.value / "webpack" / "webpack-fastopt.config.js")
webpackConfigFile in fullOptJS := Some(baseDirectory.value / "webpack" / "webpack-opt.config.js")
webpackConfigFile in Test := Some(baseDirectory.value / "webpack" / "webpack-core.config.js")

webpackDevServerExtraArgs in fastOptJS := Seq("--inline", "--hot")
webpackBundlingMode in fastOptJS := BundlingMode.LibraryOnly()

requireJsDomEnv in Test := true

addCommandAlias("dev", ";fastOptJS::startWebpackDevServer;~fastOptJS")

addCommandAlias("build", "fullOptJS::webpack")
