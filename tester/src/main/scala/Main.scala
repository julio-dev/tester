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

  log.info(s"tester v$version")
  log.info(s"Domain Being Tested: $domain")
  log.info(s"Results Directory: $resultsDir")

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
  val dnsenumCmd = s"dnsenum -o ${resultsPath.toPath}/dnsenum.xml $domain"
  executeCommand(dnsenumCmd)

  log.info("----------")

  log.info("dnsmap")
  val dnsmapCmd = s"dnsmap $domain -r ${resultsPath.toPath}/dnsmap.txt"
  executeCommand(dnsmapCmd)

  log.info("----------")

  log.info("dnsrecon")
  val dnsreconAxfrCmd = s"dnsrecon -t axfr -d $domain -x ${resultsPath.toPath}/dnsreconaxfr.xml"
  val dnsreconCmd = s"dnsrecon -d $domain -x ${resultsPath.toPath}/dnsrecon.xml"
  executeCommand(dnsreconAxfrCmd)
  executeCommand(dnsreconCmd)

  log.info("----------")
  
  log.info("fierce")
  val fierceCmd = s"fierce -dns $domain -file ${resultsPath.toPath}/fierce.txt"
  executeCommand(fierceCmd)

  log.info("----------")

  log.info("urlcrazy")
  val urlcrazyCmd = s"urlcrazy -o ${resultsPath.toPath}/urlcrazy.txt $domain"
  executeCommand(urlcrazyCmd)

  log.info("----------")
  
}