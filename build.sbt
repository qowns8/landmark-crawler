name := "landmark-crawler"

version := "0.1"

scalaVersion := "2.12.6"

libraryDependencies ++= Seq(
  "org.seleniumhq.selenium" % "selenium-java" % "2.35.0",
  "org.seleniumhq.selenium" % "selenium-chrome-driver" % "3.12.0",
  "org.scalatest" % "scalatest_2.12" % "3.0.5",
  "org.json4s" %% "json4s-jackson" % "3.5.2"
)