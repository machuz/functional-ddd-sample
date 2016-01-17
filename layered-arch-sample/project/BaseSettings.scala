import sbt.Keys._
import sbt._
import sbtassembly.AssemblyKeys._
import sbtassembly.{MergeStrategy, PathList}

object BaseSettings extends Build {

  val defaultScalaVersion = "2.11.7"

  lazy val appSettings = Seq(
    name := "sample1-app",
    version := "1.0-SNAPSHOT"
  )

  lazy val domainSettings = Seq(
    name := "domain",
    version := "1.0-SNAPSHOT"
  )

  lazy val infraSettings = Seq(
    name := "infrastructure",
    version := "1.0-SNAPSHOT"
  )


  lazy val coreSettings = Seq(
    name := "core",
    version := "1.0-SNAPSHOT"
  )

  lazy val commonSettings = Seq(
    organization := "layered-arch-sample",
    scalaVersion := defaultScalaVersion,
    retrieveManaged := true
  )

}