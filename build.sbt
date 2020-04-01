name := "FormulaGame"

version := "1.0.0"

scalaVersion := "2.12.6"

sbtVersion := "1.3.2"


scalacOptions ++= Seq("-deprecation")

libraryDependencies += "org.scalatest" %% "scalatest" % "2.0.M6-SNAP9" % "test"

libraryDependencies += "junit" % "junit" % "4.11" % "test"

libraryDependencies += "org.specs2" %% "specs2" % "2.1.1" % "test"

ScctPlugin.instrumentSettings
libraryDependencies += "org.scalactic" %% "scalactic" % "3.1.1"
libraryDependencies += "org.scalatest" %% "scalatest" % "3.1.1" % "test"