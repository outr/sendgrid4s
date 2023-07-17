// Scala versions
val scala3 = "3.3.0"

val scala213 = "2.13.11"

name := "sendgrid4s"
organization := "com.outr"
version := "1.0.18"

scalaVersion := scala213
crossScalaVersions := List(scala3, scala213)

scalacOptions ++= Seq("-unchecked", "-deprecation")
javacOptions ++= Seq("-source", "1.8", "-target", "1.8")

sonatypeCredentialHost := "s01.oss.sonatype.org"
sonatypeRepository := "https://s01.oss.sonatype.org/service/local"
publishTo := sonatypePublishToBundle.value
sonatypeProfileName := "com.outr"
licenses := Seq("MIT" -> url("https://github.com/outr/sendgrid4s/blob/master/LICENSE"))
sonatypeProjectHosting := Some(xerial.sbt.Sonatype.GitHubHosting("outr", "sendgrid4s", "matt@matthicks.com"))
homepage := Some(url("https://github.com/outr/sendgrid4s"))
scmInfo := Some(
  ScmInfo(
    url("https://github.com/outr/sendgrid4s"),
    "scm:git@github.com:outr/sendgrid4s.git"
  )
)
developers := List(
  Developer(id="darkfrog", name="Matt Hicks", email="matt@matthicks.com", url=url("https://matthicks.com"))
)

outputStrategy := Some(StdoutOutput)

fork := true

libraryDependencies ++= Seq(
  "com.outr" %% "spice-client" % "0.1.6"
)