import sbt._

class AvroSbt(info: ProjectInfo) extends PluginProject(info) with IdeaProject {
  val avro = "org.apache.avro" % "avro" % "1.4.1" withSources() intransitive()
  val jopt = "net.sf.jopt-simple" % "jopt-simple" % "3.2" withSources() intransitive()
  

  /**
   * Publish via Ivy.
   */
  lazy val publishTo = Resolver.sftp("Personal Repo",
                                     "codahale.com",
                                     "/home/codahale/repo.codahale.com/") as ("codahale")
  override def managedStyle = ManagedStyle.Maven
}
