import sbt._

class AvroSbt(info: ProjectInfo) extends PluginProject(info) with IdeaProject {
  val avro = "org.apache.avro" % "avro" % "1.5.1" withSources()
  val avroCompiler = "org.apache.avro" % "avro-compiler" % "1.5.1" withSources()
  val avroTools = "org.apache.avro" % "avro-tools" % "1.5.1" withSources()
  

  /**
   * Publish via Ivy.
   */
  lazy val publishTo = Resolver.sftp("Personal Repo",
                                     "codahale.com",
                                     "/home/codahale/repo.codahale.com/") as ("codahale")
  override def managedStyle = ManagedStyle.Maven
}
