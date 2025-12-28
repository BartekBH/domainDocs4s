package domaindocs4s.errors

import tastyquery.Trees.TermTree

final case class DomainDocsArgError(label: String, symbol: String, tree: TermTree)
    extends RuntimeException(
      // for dynamic content experiments: prints the tree when it's not handled by the Collector
      s"@domainDoc: ${tree}",
    )
