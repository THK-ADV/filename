name := "filename"
organization := "de.th-koeln.inf.adv"
version := "0.1"
scalaVersion := "2.13.8"

libraryDependencies += "org.scala-lang" % "scala-reflect" % scalaVersion.value

publishTo := Some(
  "GitHub <THK-ADV> Apache Maven Packages" at "https://maven.pkg.github.com/THK-ADV/filename"
)

publishConfiguration := publishConfiguration.value.withOverwrite(true)

publishMavenStyle := true

credentials += Credentials(
  "GitHub Package Registry",
  "maven.pkg.github.com",
  "THK-ADV",
  System.getenv("GITHUB_TOKEN")
)
