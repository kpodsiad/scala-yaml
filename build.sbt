lazy val scala3Version  = "3.0.0"
lazy val projectName    = "scala-yaml"
lazy val projectVersion = "0.0.1"

lazy val munit: Seq[Setting[_]] = Seq(
  libraryDependencies ++= Seq(Deps.munit % Test),
  testFrameworks += new TestFramework("munit.Framework")
)

lazy val root = project
  .in(file("."))
  .settings(
    name              := projectName,
    version           := projectVersion,
    scalaVersion      := scala3Version,
    semanticdbVersion := scalafixSemanticdb.revision,
    semanticdbEnabled := true
  )
  .settings(munit)
