package pl.warp.engine.core.context.graph

import scala.annotation.tailrec


/**
  * @author Jaca777
  *         Created 2015-12-23 at 11
  * DAG implementation for the service loader.
  * Root node is a node that has no nodes connected to it.
  */
case class DirectedAcyclicGraph[+A](rootNodes: Node[A]*) {

  def addEdge[B >: A](from: B, to: B): DirectedAcyclicGraph[B] = {
    val fromOpt = resolveNode(from)
    val toOpt = resolveNode(to)
    (fromOpt, toOpt) match {
      case (None,           None)         => addEdgeAndCreateNodes(from, to)
      case (Some(fromNode), None)         => addEdgeFromExistingNode(fromNode, to)
      case (None,           Some(toNode)) => addEdgeToExistingNode(from, toNode)
      case (Some(fromNode), Some(toNode)) => addEdgeBetweenExistingNodes(fromNode, toNode)
    }
  }

  def addNode[B >: A](e: B): DirectedAcyclicGraph[B] = {
    val roots: Seq[Node[_ >: A <: B]] = rootNodes :+ Node[B](e)
    new DirectedAcyclicGraph[B](roots: _*)
  }

  private def addEdgeAndCreateNodes[B >: A](from: B, to: B): DirectedAcyclicGraph[B] = {
    val toNode = Node[B](to)
    val fromNode = Node[B](from, toNode)
    val roots = rootNodes :+ fromNode
    new DirectedAcyclicGraph[B](roots: _*)
  }

  private def addEdgeFromExistingNode[B >: A](from: Node[B], to: B): DirectedAcyclicGraph[B] = {
    val newNode = Node[B](to)
    val newLeafs = from.connections :+ newNode
    val updatedNode = Node(from.value, newLeafs: _*)
    replaceNode(from, updatedNode)
  }

  private def addEdgeToExistingNode[B >: A](from: B, to: Node[B]): DirectedAcyclicGraph[B] = {
    val newNode = Node[B](from, to)
    val roots = (if(rootNodes.contains(to)){
      rootNodes.filterNot(_ == to)
    } else rootNodes) :+ newNode
    new DirectedAcyclicGraph[B](roots: _*)
  }

  private def addEdgeBetweenExistingNodes[B >: A](from: Node[B], to: Node[B]): DirectedAcyclicGraph[B] = {
    val leafs = from.connections :+ to
    val updatedNode = Node[B](from.value, leafs: _*)
    val updatedGraph = replaceNode(from, updatedNode)
    val roots = updatedGraph.rootNodes.filterNot(_ == to)
    new DirectedAcyclicGraph[B](roots: _*)
  }


  /**
    * Finds node with the given value in graph.
    */
  def resolveNode[B >: A](value: B): Option[Node[B]] = {
    @tailrec
    def resolveAcc(toVisit: List[Node[A]], visitedNodes: Set[A]): Option[Node[B]] = toVisit match {
      case node :: tail if visitedNodes.contains(node.value) =>
        resolveAcc(tail, visitedNodes)
      case node :: _ if node.value == value => Some(node)
      case Nil => None
      case node :: tail => resolveAcc(tail ::: node.connections.toList, visitedNodes + node.value)
    }
    resolveAcc(rootNodes.toList, Set.empty)
  }

  /**
    * Creates new graph with given node replaced
    * TODO Think about making it tail-rec
    */
  def replaceNode[B >: A](node: Node[B], newNode: Node[B]): DirectedAcyclicGraph[B] = {
    def updateRoot(currNode: Node[A]): Node[B] = {
      if (currNode == node) newNode
      else {
        val value = currNode.value
        val newLeafs = currNode.connections.map(updateRoot)
        Node[B](value, newLeafs: _*)
      }
    }
    new DirectedAcyclicGraph[B](rootNodes.map(updateRoot): _*)
  }

  def accept[B >: A](visitor: GraphVisitor[B]): Unit =
    for (root <- rootNodes) root.accept(visitor)


}
