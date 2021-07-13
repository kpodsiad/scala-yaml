import org.virtuslab.internal.load.parse.ParserImpl
import org.virtuslab.internal.load.parse.Event
import org.virtuslab.internal.load.parse.Event._
import os._
import org.virtuslab.internal.load.reader.YamlReader
import com.eed3si9n.expecty.Expecty.expect
import io.circe._

import java.io.File

class EventTesterSpec extends munit.FunSuite {

  val yamlDirPath              = getClass.getResource("/yaml")
  val yamlDir                  = new File(yamlDirPath.getPath)
  val yamlPaths: List[os.Path] = yamlDir.listFiles().map(Path(_)).toList

  private def convertEventToYamlTestSuiteFormat(event: List[Event]): String = {
    event
      .map(event =>
        event match {
          case StreamStart   => "+STR"
          case StreamEnd     => "-STR"
          case DocumentStart => "+DOC"
          case DocumentEnd   => "-DOC"
          case SequenceStart => "+SEQ"
          case SequenceEnd   => "-SEQ"
          case MappingStart  => "+MAP"
          case MappingEnd    => "-MAP"
          case Scalar(value) => s"=VAL :$value"
        }
      )
      .mkString("\n")
  }

  test("should parse yaml to event") {

    var counter = 0

    yamlPaths.map { path =>

      val yaml = os.read(path)

      val reader = YamlReader(yaml)
      val events = ParserImpl.getEvents(reader).getOrElse(sys.error("Parsing yaml to event"))
      val eventYamlTestSuite: String = convertEventToYamlTestSuiteFormat(events)

      val libYamlEvent: String = os
        .proc("/Users/lwronski/projects/libyaml/tests/run-parser-test-suite", path)
        .call(cwd = os.pwd)
        .out
        .text()
        .trim

      if (eventYamlTestSuite == libYamlEvent) {
        counter += 1
      } else {
        println(path)
      }
      println(
        s"$counter - ${yamlPaths.size} - ${"%.2f".format(counter.toDouble / yamlPaths.size * 100)}%"
      )

    }

  }
}
