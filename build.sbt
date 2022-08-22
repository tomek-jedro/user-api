name := "crud"
organization := "com.crud"

version := "0.1"

scalaVersion := "2.13.8"

val AkkaVersion = "2.6.19"
val AkkaHttpVersion = "10.2.9"
val SlickVersion = "3.3.3"
val tapirVersion = "1.0.5"

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-actor" % AkkaVersion,
  "com.typesafe.akka" %% "akka-protobuf-v3" % AkkaVersion,
  "com.typesafe.akka" %% "akka-slf4j" % AkkaVersion,
  "com.typesafe.akka" %% "akka-stream" % AkkaVersion,
  "com.typesafe.akka" %% "akka-actor-typed" % AkkaVersion,
  "com.typesafe.akka" %% "akka-stream" % AkkaVersion,
  "com.typesafe.akka" %% "akka-http" % AkkaHttpVersion,
  "com.typesafe.akka" %% "akka-http-spray-json" % AkkaHttpVersion,
  "com.typesafe.scala-logging" %% "scala-logging" % "3.9.4",
  "com.typesafe" % "config" % "1.4.2",
  "com.typesafe.slick" %% "slick" % "3.3.3",
  "org.postgresql" % "postgresql" % "42.3.4",
  "com.typesafe.slick" %% "slick-hikaricp" % "3.3.3",
  "com.github.tminglei" %% "slick-pg" % "0.20.3",
  "com.github.tminglei" %% "slick-pg_play-json" % "0.20.3",
  "org.scalactic" %% "scalactic" % "3.2.13",
  "org.scalatest" %% "scalatest" % "3.2.13" % "test",
  "com.dimafeng" %% "testcontainers-scala-scalatest" % "0.40.8" % "test",
  "com.dimafeng" %% "testcontainers-scala-postgresql" % "0.40.8",
  "ch.qos.logback" % "logback-classic" % "1.1.7",
  "com.softwaremill.sttp.tapir" %% "tapir-core" % tapirVersion,
  "com.softwaremill.sttp.tapir" %% "tapir-sttp-client" % tapirVersion,
  "com.softwaremill.sttp.tapir" %% "tapir-openapi-docs" % tapirVersion,
  "com.softwaremill.sttp.apispec" %% "openapi-circe-yaml" % "0.2.1",
  "com.softwaremill.sttp.tapir" %% "tapir-json-circe" % tapirVersion,
  "com.softwaremill.sttp.tapir" %% "tapir-akka-http-server" % "1.0.5" exclude ("com.typesafe.akka", "akka-stream_2.12"),
  "org.webjars" % "swagger-ui" % "3.22.0",
  "org.typelevel" %% "cats-core" % "2.7.0",
  "com.typesafe.akka" % "akka-slf4j_2.13" % AkkaVersion,
  "com.typesafe.akka" %% "akka-stream-testkit" % AkkaVersion,
  "com.typesafe.akka" %% "akka-http-testkit" % AkkaHttpVersion,
  "com.softwaremill.sttp.tapir" %% "tapir-swagger-ui-bundle" % "1.0.5",
  "com.softwaremill.sttp.tapir" %% "tapir-json-spray" % tapirVersion

)

enablePlugins(JavaAppPackaging)
enablePlugins(DockerPlugin)

mainClass in Compile := Some("Main")
packageName in Docker := "user-api"
dockerExposedPorts := Seq(8080)
