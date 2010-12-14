package avro

import sbt._
import org.apache.avro.specific.SpecificCompiler
import org.apache.avro.tool.IdlTool
import java.util.ArrayList

trait AvroCompiler extends DefaultProject {
  override def compileOrder = CompileOrder.JavaThenScala

  def avroIdls = "src" / "main" / "avro" ** "*.avdl"
  def avroSchemas = "src" / "main" / "avro" ** "*.avsc"
  def avroProtocols = "src" / "main" / "avro" ** "*.avpr"
  def avroOutputPath = "src" / "main" / "java"

  private val idlTool = new IdlTool
  
  def generateAvroAction = task {
    for (idl <- avroIdls.get) {
      val tool = new IdlTool

      log.info("Compiling IDL %s".format(idl))

      val args = new ArrayList[String]
      args.add(idl.toString)
      args.add(idl.toString.replaceAll("""(.+?)(\.[^.]*$|$)""", "$1.avpr"))

      tool.run(System.in, System.out, System.err, args)
    }

    for (schema <- avroSchemas.get) {
      log.info("Compiling schema %s".format(schema))
      SpecificCompiler.compileSchema(schema.asFile, avroOutputPath.asFile)
    }
    
    for (protocol <- avroProtocols.get) {
      log.info("Compiling protocol %s".format(protocol))
      SpecificCompiler.compileProtocol(protocol.asFile, avroOutputPath.asFile)
    }
    
    None
  } describedAs("Generates Java classes from the specified Avro schema and protocol files.")
  lazy val generateAvro = generateAvroAction
  
  override def compileAction = super.compileAction dependsOn(generateAvro)
  
  def cleanAvroAction = task {
    FileUtilities.clean(avroOutputPath :: Nil, false, log)
    None
  } describedAs("Deletes the Avro output directory.")
  
  lazy val cleanAvro = cleanAvroAction
}
