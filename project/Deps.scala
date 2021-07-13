import sbt._

object Deps {
  def expecty   = "com.eed3si9n.expecty" %% "expecty"    % "0.15.4"
  def munit     = "org.scalameta"        %% "munit"      % "0.7.26"
  def osLib     = "com.lihaoyi"          %% "os-lib"     % "0.7.7"
  def circeYaml = "io.circe"             %% "circe-yaml" % "0.14.0"
}
