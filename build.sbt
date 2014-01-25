name := "scalaBasics"

version := "0.1"

scalaVersion := "2.11.0-M8"

scalacOptions ++= Seq(
  "-language:_"
  //"-Xprint:typer"
)

resolvers += Resolver.sonatypeRepo("releases")

libraryDependencies ++= Seq(
  "org.scalaz" % "scalaz-core_2.11.0-M7" % "7.1.0-M4"  
)



