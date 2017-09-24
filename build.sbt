name := """vocabulary"""
organization := "com.reactive"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.12.3"

libraryDependencies += guice
libraryDependencies += "org.scalatestplus.play" %% "scalatestplus-play" % "3.1.2" % Test

routesImport += "binders.PathBinders._"
routesImport += "binders.QueryStringBinders._"