import sys.process._
import com.typesafe.scalalogging.Logger
import java.io.File
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

object Main extends App {
  def executeCommand(command: String): String = {
    val output = command.!!
    output
  }
  val log = Logger("tester")
  val version: String = tester.BuildInfo.version
  val domain: String = args(0)
  val resultsDir: String = args(1)
  log.info("----------")
  log.info("tester v" + version)
  log.info("Domain Being Tested: " + domain)
  log.info("Results Directory: " + resultsDir)
  log.info("----------")
  log.info("Creating results directory.")
  val timeStamp = DateTimeFormatter.ofPattern("yyyyMMdd-HHmmss").format(LocalDateTime.now())
  val resultsPath = new File(s"$resultsDir-$timeStamp")
  if (resultsPath.mkdirs()) {
    log.info(s"${resultsPath.toPath()} directory successfully created.")
  } else {
    log.error("Failed to create results directory.")
  }
  log.info("----------")
  log.info("dnsenum")
}