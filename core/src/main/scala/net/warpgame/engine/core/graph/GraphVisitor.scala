package net.warpgame.engine.core.graph

/**
  * @author Jaca777
  *         Created 2016-01-28 at 14
  */

abstract class GraphVisitor[-A, +Self] { _: Self =>
  def visit(value: A): Self
  def enter(value: A): Boolean = true
}
