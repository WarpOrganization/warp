package pl.warp.engine.core.context.graph

import scala.annotation.tailrec
import scala.collection.Map


/**
  * @author Jaca777
  *         Created 2015-12-23 at 11
  * DAG implementation for the service loader.
  * Root node is a node that has no nodes connected to it.
  */
case class DirectedAcyclicGraph[+A](rootNodes: List[Node[A]]) {

  def this(rootNodes: Node[A]*) = this(rootNodes.toList)

  def addNode[B >: A](e: B): DirectedAcyclicGraph[B] = {
    val roots: List[Node[_ >: A <: B]] = rootNodes :+ Node[B](e)
    new DirectedAcyclicGraph[B](roots)
  }

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

  private def addEdgeAndCreateNodes[B >: A](from: B, to: B): DirectedAcyclicGraph[B] = {
    val toNode = Node[B](to)
    val fromNode = Node[B](from, toNode)
      .checkedForCycle()
    val roots = rootNodes :+ fromNode
    new DirectedAcyclicGraph[B](roots)
  }

  private def addEdgeFromExistingNode[B >: A](from: Node[B], to: B): DirectedAcyclicGraph[B] = {
    val newNode = Node[B](to)
    val newLeafs = from.leaves :+ newNode
    val updatedNode = Node(from.value, newLeafs)
    replaceNode(from, updatedNode)
  }

  private def addEdgeToExistingNode[B >: A](from: B, to: Node[B]): DirectedAcyclicGraph[B] = {
    val newNode = Node[B](from, to)
    val roots = (if(rootNodes.contains(to)){
      rootNodes.filterNot(_ == to)
    } else rootNodes) :+ newNode
    new DirectedAcyclicGraph[B](roots)
  }

  private def addEdgeBetweenExistingNodes[B >: A](from: Node[B], to: Node[B]): DirectedAcyclicGraph[B] = {
    val leafs = from.leaves :+ to
    val updatedNode = Node[B](from.value, leafs)
        .checkedForCycle()
    val updatedGraph = replaceNode(from, updatedNode)
    val roots = updatedGraph.rootNodes.filterNot(_ == to)
    new DirectedAcyclicGraph[B](roots)
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
      case node :: tail => resolveAcc(tail ::: node.leaves, visitedNodes + node.value)
    }
    resolveAcc(rootNodes, Set.empty)
  }

  /**
    * Creates new graph with given node replaced
    */
  def replaceNode[B >: A](node: Node[B], newNode: Node[B]): DirectedAcyclicGraph[B] = {
    def update(currNode: Node[A]): Option[Node[B]] = currNode match {
      case `node` => Some(newNode)
      case _ =>
        val updatedLeavesByValue = getUpdatedLeavesByValue(currNode)
        if(updatedLeavesByValue.isEmpty) {
          None
        } else {
          val newLeaves = currNode.leaves
            .map(leaf => updatedLeavesByValue.getOrElse(leaf.value, leaf))
          Some(Node(currNode.value, newLeaves))
        }
      }

    def getUpdatedLeavesByValue(parentNode: Node[A]): Map[B, Node[B]] = {
      parentNode.leaves
        .map(update)
        .collect {
          case Some(leaf) => leaf
        }.map(leaf => leaf.value -> leaf)
        .toMap
    }

    val updatedRoots = rootNodes
      .map(root => (root, update(root)))
      .map {
        case (_,       Some(updatedRoot)) => updatedRoot
        case (oldRoot, None) => oldRoot
      }

    DirectedAcyclicGraph[B](updatedRoots)
  }

  @tailrec
  final def accept[B >: A, C <: GraphVisitor[B, C]](
    visitor: C,
    at: List[Node[B]] = rootNodes
  ): C = at match {
    case Nil =>
      visitor
    case node :: tail =>
      accept(node.accept(visitor), tail)
  }

}

object DirectedAcyclicGraph {
  def apply[V](rootNodes: Node[V]*): DirectedAcyclicGraph[V] = DirectedAcyclicGraph(rootNodes.toList)
}
