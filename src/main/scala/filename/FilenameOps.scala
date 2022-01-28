package filename

import scala.annotation.tailrec
import scala.language.experimental.macros
import scala.reflect.macros.blackbox

object FilenameOps {
  def functionName: String = macro functionNameImpl

  def fileName: String = macro sourceFileImpl

  def sourceFileImpl(c: blackbox.Context): c.Expr[String] = {
    import c.universe._
    c.Expr[String](Literal(Constant(c.enclosingPosition.source.file.name)))
  }

  def functionNameImpl(c: blackbox.Context): c.Expr[String] =
    findOwningMethod(c)(c.internal.enclosingOwner)
      .map(owner => c.Expr(c.parse(s""""${owner.name.toString}"""")))
      .getOrElse(
        c.abort(
          c.enclosingPosition,
          "functionName can be used only inside function."
        )
      )

  def findOwningMethod(
      c: blackbox.Context
  )(sym: c.Symbol): Option[c.Symbol] = {
    @tailrec
    def go(sym: c.Symbol): Option[c.Symbol] = {
      if (sym == c.universe.NoSymbol) None
      else if (sym.isMethod) Option(sym)
      else go(sym.owner)
    }
    go(sym)
  }
}
