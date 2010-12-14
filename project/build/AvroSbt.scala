import sbt._

class AvroSbt(info: ProjectInfo) extends PluginProject(info) with IdeaProject {
  val jboss = "JBoss" at "https://repository.jboss.org/nexus/content/groups/public/"

  val avro = "org.apache.avro" % "avro" % "1.4.1" withSources()
  val jopt = "net.sf.jopt-simple" % "jopt-simple" % "3.2" withSources()
  

  /**
   * Publish via Ivy.
   */
  lazy val publishTo = Resolver.sftp("Personal Repo",
                                     "codahale.com",
                                     "/home/codahale/repo.codahale.com/") as ("codahale")
  override def managedStyle = ManagedStyle.Maven
}
