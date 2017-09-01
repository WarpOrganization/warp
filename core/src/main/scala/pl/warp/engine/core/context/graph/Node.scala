package pl.warp.engine.core.context.graph

import scala.annotation.tailrec

/**
  * @author Jaca777
  *         Created 2015-12-23 at 11
  */
case class Node[+V](value: V, leaves: List[Node[V]]) {

  def this(value: V, leaves: Node[V]*) = this(value, leaves.toList)

  def checkedForCycle(): Node[V] = {
    def checkForCycle(toVisit: Node[V], visited: List[V]): Unit = {
      if (visited.contains(toVisit.value)) {
        throw CycleFoundException(visited :+ toVisit.value)
      } else {
        val path = visited :+ toVisit.value
        toVisit.leaves.foreach(leaf => checkForCycle(leaf, path))
      }
    }
    checkForCycle(this, List.empty)
    this
  }

  def accept[A >: V, B <: GraphVisitor[A, B]](visitor: B): B = {
    @tailrec
    def acceptAtLeaf(visitor: B, leaves: List[Node[A]] = leaves): B = leaves match {
      case Nil => visitor
      case node :: tail =>
        acceptAtLeaf(node.accept(visitor), tail)
    }

    acceptAtLeaf(visitor.enter())
      .leave()
      .visit(value)
  }


}

object Node {
  def apply[V](value: V, leaves: Node[V]*): Node[V] = Node(value, leaves.toList)
}