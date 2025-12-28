package domaindocs4s.dynamiccontent

import scala.quoted.*

object Macros {

  enum JoinMode {
    case Concat(delimiter: String)
    case Affixation(prefix: String, suffix: String)
    case MkString(prefix: String, delimiter: String, suffix: String)
  }

  transparent inline def joinStrings(inline strings: List[String], inline mode: JoinMode): String =
    ${ impl('strings, 'mode) }

  private def impl(
      stringsExpr: Expr[List[String]],
      modeExpr: Expr[JoinMode],
  )(using Quotes): Expr[String] = {
    import quotes.reflect.*

    val strings = stringsExpr.valueOrAbort

    modeExpr match {
      case '{ JoinMode.Concat($delimiter) } =>
        val d = delimiter.valueOrAbort
        Expr(strings.mkString(d))

      case '{ JoinMode.Affixation($prefix, $suffix) } =>
        val p    = prefix.valueOrAbort
        val s    = suffix.valueOrAbort
        val text = strings.mkString
        Expr(strings.mkString(p, "", s))

      case '{ JoinMode.MkString($prefix, $delimiter, $suffix) } =>
        val p = prefix.valueOrAbort
        val d = delimiter.valueOrAbort
        val s = suffix.valueOrAbort
        Expr(strings.mkString(p, d, s))

      case _ =>
        report.errorAndAbort("JoinMode must be a compile-time constant")
    }
  }
}
