name := "scalaBasics"

version := "0.1"

scalaVersion := "2.11.0-RC3"

scalacOptions ++= Seq(
  "-language:_"
  //"-Xprint:typer"
)

resolvers += Resolver.sonatypeRepo("releases")

libraryDependencies ++= Seq(
  "org.scalaz" % "scalaz-core_2.11.0-RC3" % "7.0.6",
  "org.specs2" % "specs2_2.11.0-RC3" % "2.3.10" % "test"
)



