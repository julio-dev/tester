package com.julioj.tester

import com.typesafe.scalalogging.Logger
import java.io.File
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.Duration
import org.openqa.selenium.By
import org.openqa.selenium.Keys
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.firefox.FirefoxDriver
import org.openqa.selenium.firefox.FirefoxOptions
import org.openqa.selenium.support.ui.WebDriverWait
import org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated

object Main extends App {
  val log = Logger("tester")
  val version: String = tester.BuildInfo.version
  val domain: String = args(0)
  val resultsDir: String = args(1)

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

  log.info("OTG-INFO-001 - Conduct search engine discovery/reconnaissance for information leakage.")
  val options: FirefoxOptions = new FirefoxOptions()
  options.setHeadless(true)
  val firefox: WebDriver = new FirefoxDriver(options)

  val implicitWait = new WebDriverWait(firefox, 10)

  try {
    firefox.get(s"https://www.google.com/search?q=site%3A$domain")
    val citeTags = firefox.findElements(By.tagName("cite"))
    citeTags.forEach { citeTag =>
      log.debug(s"${citeTag.getText}")
    }
  } finally {
    firefox.quit()
  }
}