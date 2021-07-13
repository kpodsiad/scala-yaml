import Settings.project

val scala3Version   = "3.0.0"
val projectName     = "scala-yaml"
val projectTestName = "scala-yaml-test"
val projectVersion  = "0.0.1"

lazy val munit: Seq[Setting[_]] = Seq(
  libraryDependencies ++= Seq(Deps.munit % Test, Deps.expecty % Test),
  testFrameworks += new TestFramework("munit.Framework")
)

lazy val core = project("core")
  .settings(
    munit,
    name := projectName,
    version := projectVersion,
    scalaVersion := scala3Version,
    semanticdbVersion := scalafixSemanticdb.revision,
    semanticdbEnabled := true
  )

lazy val testProjectDependencies = Seq(
  libraryDependencies ++= Seq(Deps.osLib, Deps.circeYaml)
)

lazy val packager = project("testing")
  .dependsOn(core)
  .settings(
    name := projectTestName,
    version := projectVersion,
    scalaVersion := scala3Version,
    munit,
    testProjectDependencies
  )
