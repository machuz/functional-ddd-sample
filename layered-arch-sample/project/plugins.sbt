///////////////////////
// flyway plugin
///////////////////////
addSbtPlugin("org.flywaydb" % "flyway-sbt" % "3.0")

resolvers += "Flyway" at "http://flywaydb.org/repo"

///////////////////////
// sbt assembly
///////////////////////
resolvers += Resolver.url("artifactory", url("http://scalasbt.artifactoryonline.com/scalasbt/sbt-plugin-releases"))(Resolver.ivyStylePatterns)

addSbtPlugin("com.eed3si9n" % "sbt-assembly" % "0.14.1")

