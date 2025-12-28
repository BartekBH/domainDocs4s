package domaindocs4s.dynamiccontent.domain

import domaindocs4s.domainDoc

import java.util.UUID

object Domain {

  // INLINES
  inline def desc                        = "First name of the party" // Ident(desc)
  transparent inline def base            = "First name of the"
  transparent inline def tail            = "party"
  transparent inline def lastNameDesc    = base + " " + tail         // works; Inlined(Literal(Constant(First name of theparty)),Some(TypeIdent(Domain$)),List())
  transparent inline def lastNameDesc_v2 = s"$base $tail" // doesn't work; Inlined(Apply(Select(Apply(Select(Select(Select(Ident(_root_),scala),StringContext),apply[with sig (scala.collection.immutable.Seq):scala.StringContext @apply]),List(Typed(SeqLiteral(List(Literal(Constant()), Literal(Constant( )), Literal(Constant())),TypeWrapper(TypeRef(TermRef(PackageRef(scala), Predef), String))),TypeWrapper(RepeatedType(TypeRef(TermRef(PackageRef(scala), Predef), String)))))),s[with sig (scala.collection.immutable.Seq):java.lang.String @s]),List(Typed(SeqLiteral(List(Inlined(Literal(Constant(First name of the)),Some(TypeIdent(Domain$)),List()), Inlined(Literal(Constant(party)),Some(TypeIdent(Domain$)),List())),TypeWrapper(TypeRef(PackageRef(scala), Any))),TypeWrapper(RepeatedType(TypeRef(PackageRef(scala), Any)))))),Some(TypeIdent(Domain$)),List())
  transparent inline def lastNameDesc_v3 = List(base, tail).mkString // doesn't work; Inlined(Select(Apply(TypeApply(Select(Ident(List),apply[with sig (1,scala.collection.immutable.Seq):java.lang.Object @apply]),List(TypeWrapper(TypeRef(PackageRef(java.lang), String)))),List(Typed(SeqLiteral(List(Inlined(Literal(Constant(First name of the)),Some(TypeIdent(Domain$)),List()), Inlined(Literal(Constant(party)),Some(TypeIdent(Domain$)),List())),TypeWrapper(TypeRef(PackageRef(java.lang), String))),TypeWrapper(RepeatedType(TypeRef(PackageRef(java.lang), String)))))),mkString),Some(TypeIdent(Domain$)),List())

  @domainDoc(lastNameDesc)
  final case class FirstName(value: String)


  // BASIC MACROS
  import domaindocs4s.dynamiccontent.Macros.JoinMode
  import domaindocs4s.dynamiccontent.Macros

  // since macro `fieldDesc` is inlined, wrapping it into another `inlined def` makes the tree nested
  transparent inline def affixationDesc = Macros.joinStrings(List(base, tail), JoinMode.Affixation("\"", "\""))
  transparent inline def mkStringDesc   = Macros.joinStrings(List(base, tail), JoinMode.MkString("[", " ", "]"))

  @domainDoc(Macros.joinStrings(List(base, tail), JoinMode.Concat(" "))) // Inlined(Literal(Constant(First name of the party)),Some(TypeIdent(Domain$)),List())
  final case class FirstName_Concat(value: String)

  @domainDoc(affixationDesc) // Inlined(Inlined(Literal(Constant("First name of theparty")),Some(SelectTypeTree(TypeWrapper(PackageRef(domaindocs4s.dynamiccontent)),Macros$)),List()),Some(TypeIdent(Domain$)),List())
  final case class FirstName_Affixation(value: String)

  @domainDoc(mkStringDesc) // Inlined(Inlined(Literal(Constant([First name of the party])),Some(SelectTypeTree(TypeWrapper(PackageRef(domaindocs4s.dynamiccontent)),Macros$)),List()),Some(TypeIdent(Domain$)),List())
  final case class FirstName_MkString(value: String)


  // TYPE-LEVEL
  import scala.compiletime.constValue
  import scala.compiletime.ops.string.+

  // type needs to be "converted" to constant by using constValue[T]
  type Base         = "Unique technical identifier of"
  type Tail         = "a party."
  type IdDesc = Base + " " + Tail // works but is limited to string ops (probably could be easly extended - needs to be explored)

  @domainDoc(constValue[IdDesc])
  final case class Id(value: UUID)

}
