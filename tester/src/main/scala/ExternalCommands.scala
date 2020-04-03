package com.julioj.tester

import sys.process._
import com.typesafe.scalalogging.Logger
import java.io.File

object ExternalCommands {
  val log = Logger("tester")

  def executeCommand(command: String): String = {
    val output = command.!!
    output
  }

  def dnsenum(domain: String, resultsPath: File) = {
    log.info("----------")
    log.info("dnsenum")
    val dnsenumCmd = s"dnsenum -o ${resultsPath.toPath}/dnsenum.xml $domain"
    executeCommand(dnsenumCmd)
  }

  def dnsmap(domain: String, resultsPath: File) = {
    log.info("----------")
    log.info("dnsmap")
    val dnsmapCmd = s"dnsmap $domain -r ${resultsPath.toPath}/dnsmap.txt"
    executeCommand(dnsmapCmd)
  }

  def dnsrecon(domain: String, resultsPath: File) = {
    log.info("----------")
    log.info("dnsrecon")
    val dnsreconAxfrCmd = s"dnsrecon -t axfr -d $domain -x ${resultsPath.toPath}/dnsreconaxfr.xml"
    val dnsreconCmd = s"dnsrecon -d $domain -x ${resultsPath.toPath}/dnsrecon.xml"
    executeCommand(dnsreconAxfrCmd)
    executeCommand(dnsreconCmd)
  }

  def fierce(domain: String, resultsPath: File) = {
    log.info("----------")
    log.info("fierce")
    val fierceCmd = s"fierce -dns $domain -file ${resultsPath.toPath}/fierce.txt"
    executeCommand(fierceCmd)
  }

  def urlcrazy(domain: String, resultsPath: File) = {
    log.info("----------")
    log.info("urlcrazy")
    val urlcrazyCmd = s"urlcrazy -o ${resultsPath.toPath}/urlcrazy.txt $domain"
    executeCommand(urlcrazyCmd)
  }
}
