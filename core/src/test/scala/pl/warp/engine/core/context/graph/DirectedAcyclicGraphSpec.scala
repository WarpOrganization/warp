package pl.warp.engine.core.context.graph

import org.scalatest.{Matchers, WordSpecLike}


/**
  * @author Jaca777
  *         Created 2015-12-23 at 12
  */
class DirectedAcyclicGraphSpec extends WordSpecLike with Matchers {

  "DirectedAcyclicGraph" should {

    "add two new nodes" in {
      val graph = new DirectedAcyclicGraph[Int]()
      val newGraph = graph.addEdge(1, 2)
      val rootNode = newGraph.rootNodes.head
      rootNode.value should be(1)
      rootNode.connections.head.value should be(2)
    }

    "connect node to existing one" in {
      val root = Node(1, Node(2, Node(4), Node(5, Node(6))), Node(3))
      val graph = new DirectedAcyclicGraph[Int](root)
      val newGraph = graph.addEdge(7, 5)
      val newRoot = newGraph.rootNodes(1)
      newRoot should be(Node(7, Node(5, Node(6))))
    }

    "connect existing node to new one" in {
      val root = Node(1, Node(2), Node(3, Node(4), Node(5)))
      val graph = new DirectedAcyclicGraph[Int](root)
      val newGraph = graph.addEdge(4, 7)
      val newRoot = newGraph.rootNodes.head
      newRoot should be(Node(1, Node(2), Node(3, Node(4, Node(7)), Node(5))))
    }

    "connect existing node to existing node in connected graph" in {
      val root = Node(1, Node(2), Node(3, Node(4), Node(5)))
      val graph = new DirectedAcyclicGraph[Int](root)
      val newGraph = graph.addEdge(1, 3)
      val newRoot = newGraph.rootNodes.head
      newRoot should be(Node(1, Node(2), Node(3, Node(4), Node(5)), Node(3, Node(4), Node(5))))
    }

    "connect existing node to existing node in disconnected graphs" in {
      val root1 = Node(1, Node(2), Node(3, Node(4), Node(5)))
      val root2 = Node(6, Node(7), Node(8))
      val graph = new DirectedAcyclicGraph[Int](Array(root1, root2): _*)
      val newGraph = graph.addEdge(6, 3)
      val newRoot = newGraph.rootNodes(1)
      newRoot should be(Node(6, Node(7), Node(8), Node(3, Node(4), Node(5))))
    }

    "degrade a root node after adding an edge to it" in {
      val root1 = Node(1, Node(2), Node(3, Node(4), Node(5)))
      val root2 = Node(6, Node(7), Node(8))
      val graph = new DirectedAcyclicGraph[Int](Array(root1, root2): _*)
      val newGraph = graph.addEdge(6, 1)
      newGraph
    }

    "detect cyclic dependency in self-referencing node" in {
      val graph = new DirectedAcyclicGraph[Int]
      intercept[CycleFoundException[Int]] {
        graph.addEdge(1, 1)
      }.getMessage should be (s"Cycle found: 1 -> 1")
    }

    "detect cyclic dependency" in {
      val root1 = Node(1, Node(2), Node(3, Node(4), Node(5)))
      val root2 = Node(6, Node(7), Node(8, Node(9)))
      val graph = new DirectedAcyclicGraph[Int](Array(root1, root2): _*)
      intercept[CycleFoundException[Int]] {
        graph.addEdge(5, 6)
          .addEdge(9, 1)
      }.cycle should contain theSameElementsInOrderAs List(9, 1,3, 5, 6, 8, 9)
    }

  }
}