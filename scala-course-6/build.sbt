name := "ping-pong"

scalaVersion := "2.10.2"

libraryDependencies ++= Seq(
    "com.typesafe.akka"     %%  "akka-actor"           % "2.2.0",
    "com.google.guava"     %  "guava"           % "13.0",
    "kamon"                 %%  "kamon-core"           % "0.1-SNAPSHOT",
    "kamon"                 %%  "kamon-dashboard"      % "0.1-SNAPSHOT"
)

atmosSettings