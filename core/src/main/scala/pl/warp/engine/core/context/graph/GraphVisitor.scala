package pl.warp.engine.core.context.graph


/**
  * @author Jaca777
  *         Created 2016-01-28 at 14
  */

//Resigned from functional approach, performance suffers too much
abstract class GraphVisitor[A] {
  def visit(value: A): Unit
  def enter(): Unit
  def leave(): Unit
}
