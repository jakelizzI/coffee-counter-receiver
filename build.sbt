name := "coffee-counter-receiver"

version := "1.0.0"

scalaVersion := "2.12.4"

libraryDependencies ++= Seq(
  "net.java.dev.jna" % "jna" % "4.5.0",
  // https://mvnrepository.com/artifact/org.wildfly.swarm/javafx
  "org.scalafx" %% "scalafx" % "8.0.144-R12",
  // https://mvnrepository.com/artifact/org.wildfly.swarm/javafx
  "org.wildfly.swarm" % "javafx" % "2018.5.0",
  // https://mvnrepository.com/artifact/org.scalafx/scalafxml-core-sfx8
  "org.scalafx" %% "scalafxml-core-sfx8" % "0.4"
)

addCompilerPlugin("org.scalamacros" % "paradise" % "2.1.0" cross CrossVersion.full)