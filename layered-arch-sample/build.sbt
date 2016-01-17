import BaseSettings._
import Dependencies._

name := """layered-arch-sample"""

scalaVersion := defaultScalaVersion

////////////////////////
// scalikejdbc
////////////////////////
scalikejdbcSettings

////////////////////////
// flyway
///////////////////////
Seq(flywaySettings: _*)

flywayLocations := Seq("filesystem:db/migration/mysql")

flywayInitOnMigrate := true

flywayUrl := "jdbc:mysql://localhost/sample_1_db"

flywayUser := "root"

flywayPassword := "rootpasswd"

////////////////////////
// projects
///////////////////////
lazy val root = (project in file("."))
  .settings(commonSettings: _*)
  .settings(libraryDependencies ++= rootProjectDeps)

lazy val app = (project in file("./modules/sample-app"))
  .settings(appSettings: _*)
  .settings(commonSettings: _*)
  .dependsOn(domain, infrasutructure, core)
  .aggregate(domain, infrasutructure, core)

lazy val domain = (project in file("./modules/domain"))
  .settings(domainSettings: _*)
  .settings(commonSettings: _*)
  .settings(initialCommands in console := """
    import scala.concurrent._
    import scala.concurrent.duration.Duration
    import scalikejdbc._
    import scalikejdbc.config._
    import sample1.domain.model.user._
    import sample1.domain.model.message._
    import sample1.domain.service.user._
    import sample1.domain.service.message._
    import sample1.domain.lifecycle.user._
    import sample1.domain.lifecycle.message._
    def getValue[A](f: Future[A]): A = Await.result(f, Duration(300, "seconds"))
    DBs.setupAll()
  """)
  .dependsOn(infrasutructure)
  .aggregate(infrasutructure)

lazy val infrasutructure = (project in file("./modules/infrastructure"))
  .settings(infraSettings: _*)
  .settings(commonSettings: _*)
  .settings(libraryDependencies ++= infraProjectDeps ++ testDeps)
  .dependsOn(core)
  .aggregate(core)

lazy val core = (project in file("./modules/core"))
  .settings(coreSettings: _*)
  .settings(commonSettings: _*)
  .settings(libraryDependencies ++= coreProjectDeps ++ testDeps)


