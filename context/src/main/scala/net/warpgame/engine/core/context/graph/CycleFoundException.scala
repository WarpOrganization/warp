package net.warpgame.engine.core.context.graph

/**
  * @author Jaca777
  *         Created 2017-08-27 at 14
  */
case class CycleFoundException[A](cycle: List[A])
  extends RuntimeException(s"Cycle found: ${cycle.mkString(" -> ")}")