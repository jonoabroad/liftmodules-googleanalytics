name := "google-analytics"

organization := "net.liftmodules"

version := "1.0-SNAPSHOT"

liftVersion <<= liftVersion ?? "2.5-SNAPSHOT"

liftEdition <<= liftVersion apply { _.substring(0,3) }

name <<= (name, liftEdition) { (n, e) =>  n + "_" + e }

scalaVersion := "2.9.2"

crossScalaVersions := Seq("2.10.0", "2.9.2", "2.9.1-1", "2.9.1")

scalacOptions ++= Seq("-unchecked", "-deprecation")

resolvers += "Java.net Maven2 Repository" at "http://download.java.net/maven/2/"

resolvers += "CB Central Mirror" at "http://repo.cloudbees.com/content/groups/public"

libraryDependencies <++= liftVersion { v =>
  "net.liftweb" %% "lift-webkit" % v % "provided" ::
  Nil
}

libraryDependencies ++= Seq(
  "org.scala-tools.testing" % "specs_2.9.0" % "1.6.8" % "test", // For specs.org tests
  "junit" % "junit" % "4.8" % "test->default", // For JUnit 4 testing
  "javax.servlet" % "servlet-api" % "2.5" % "provided->default",
  "com.h2database" % "h2" % "1.2.138", // In-process database, useful for development systems
  "ch.qos.logback" % "logback-classic" % "0.9.26" % "compile->default" // Logging
)


publishTo <<= version { _.endsWith("SNAPSHOT") match {
 	case true  => Some("snapshots" at "https://oss.sonatype.org/content/repositories/snapshots")
 	case false => Some("releases" at "https://oss.sonatype.org/service/local/staging/deploy/maven2")
  }
 }

credentials += Credentials( file("sonatype.credentials") )

credentials += Credentials( file("/private/liftmodules/sonatype.credentials") )


publishMavenStyle := true

publishArtifact in Test := false

pomIncludeRepository := { _ => false }

pomExtra := (
	<url>https://github.com:d6y/liftmodules-googleanalytics</url>
	<licenses>
		<license>
	      <name>Apache 2.0 License</name>
	      <url>http://www.apache.org/licenses/LICENSE-2.0.html</url>
	      <distribution>repo</distribution>
	    </license>
	 </licenses>
	 <scm>
	    <url>git@github.com:d6y/liftmodules-googleanalytics.git</url>
	    <connection>scm:git:git@github.com:d6y/liftmodules-googleanalytics.git</connection>
	 </scm>
	 <developers>
	    <developer>
	      <id>d6y</id>
	      <name>Richard Dallaway</name>
	      <url>http://richard.dallaway.com</url>
	 	</developer>
	 </developers>
 )


// Thank you:
// https://github.com/sbt/sbt.github.com/blob/gen-master/src/jekyll/using_sonatype.md
// https://docs.sonatype.org/display/Repository/Sonatype+OSS+Maven+Repository+Usage+Guide
