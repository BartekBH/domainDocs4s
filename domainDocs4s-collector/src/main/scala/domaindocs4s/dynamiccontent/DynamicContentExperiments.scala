package domaindocs4s.dynamiccontent

import domaindocs4s.collector.TastyQueryCollector
import domaindocs4s.output.Glossary
import tastyquery.Contexts.Context
import tastyquery.jdk.ClasspathLoaders

import java.io.File
import java.nio.file.{Path, Paths}

object DynamicContentExperiments extends App {

  val classpath: List[Path] =
    sys.props("java.class.path")
      .split(File.pathSeparator)
      .toList
      .map(_.trim)
      .filter(_.nonEmpty)
      .map(Paths.get(_))

  given ctx: Context = Context.initialize(ClasspathLoaders.read(classpath))

  val pkg = "domaindocs4s.dynamiccontent.domain"
  val collector = new TastyQueryCollector
  val docs = collector.collectSymbols(pkg)

  val glossary = Glossary.build(docs).asMarkdown

  println(glossary)
}
