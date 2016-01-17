import sbt.Keys._
import sbt._
import BaseSettings._

object Dependencies {

  val rootProjectDeps = Seq(
    "mysql" % "mysql-connector-java" % "5.1.32" // use flyway
  )

  val coreProjectDeps = Seq(
    "com.typesafe" % "config" % "1.3.0",
    "mysql" % "mysql-connector-java" % "5.1.32",
    "org.scalaz" %% "scalaz-core" % "7.1.6",
    "ch.qos.logback" % "logback-classic" % "1.1.3",
    "org.scalikejdbc" %% "scalikejdbc" % "2.3.4",
    "org.scalikejdbc" %% "scalikejdbc-config" % "2.3.4"
  )

  val infraProjectDeps = Seq(
    "org.scalikejdbc" %% "scalikejdbc-test" % "2.3.4" % "test"
  )

  val testDeps = Seq(
    "org.specs2" %% "specs2-core" % "2.5-scalaz-7.1.6" % "test",
    "org.specs2" %% "specs2-mock" % "2.4.15" % "test"
  )

}