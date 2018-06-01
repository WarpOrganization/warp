package net.warpgame.engine.core.context.graph

import org.scalatest.{Matchers, WordSpecLike}


/**
  * @author Jaca777
  *         Created 2015-12-23 at 12
  */
class DAGSpec extends WordSpecLike with Matchers {

  "DirectedAcyclicGraph" should {

    "add two new nodes" in {
      //given
      val graph = DAG[Int]()

      //when
      val newGraph = graph.addEdge(1, 2)

      //then
      val rootNode = newGraph.rootNodes.head
      rootNode.value should be(1)
      rootNode.leaves.head.value should be(2)
    }

    "connect node to existing one" in {
      //given
      val root = Node(1, Node(2, Node(4), Node(5, Node(6))), Node(3))
      val graph = DAG[Int](root)

      //when
      val newGraph = graph.addEdge(7, 5)

      //then
      val newRoot = newGraph.rootNodes(1)
      newRoot should be(Node(7, Node(5, Node(6))))
    }

    "connect existing node to new one" in {
      //given
      val root = Node(1, Node(2), Node(3, Node(4), Node(5)))
      val graph = DAG[Int](root)

      //when
      val newGraph = graph.addEdge(4, 7)

      //then
      val newRoot = newGraph.rootNodes.head
      newRoot should be(Node(1, Node(2), Node(3, Node(4, Node(7)), Node(5))))
    }

    "connect existing node to existing node in connected graph" in {
      //given
      val root = Node(1, Node(2), Node(3, Node(4), Node(5)))
      val graph = DAG[Int](root)

      //when
      val newGraph = graph.addEdge(1, 3)

      //then
      val newRoot = newGraph.rootNodes.head
      newRoot should be(Node(1, Node(2), Node(3, Node(4), Node(5)), Node(3, Node(4), Node(5))))
    }

    "connect existing node to existing node in disconnected graphs" in {
      //given
      val root1 = Node(1, Node(2), Node(3, Node(4), Node(5)))
      val root2 = Node(6, Node(7), Node(8))
      val graph = DAG[Int](Array(root1, root2): _*)

      //when
      val newGraph = graph.addEdge(6, 3)

      //then
      val newRoot = newGraph.rootNodes(1)
      newRoot should be(Node(6, Node(7), Node(8), Node(3, Node(4), Node(5))))
    }

    "degrade a root node after adding an edge to it" in {
      //given
      val root1 = Node(1, Node(2), Node(3, Node(4), Node(5)))
      val root2 = Node(6, Node(7), Node(8))
      val graph = DAG[Int](Array(root1, root2): _*)

      //when
      val newGraph = graph.addEdge(6, 1)

      //then
      newGraph.rootNodes should matchPattern {case List(Node(6, _)) =>}
    }

    "detect cyclic dependency in self-referencing node" in {
      //given
      val graph = DAG[Int]()

      //then
      intercept[CycleFoundException[Int]] {
        graph.addEdge(1, 1)
      }.getMessage should be (s"Cycle found: 1 -> 1")
    }

    "detect cyclic dependency" in {
      //given
      val root1 = Node(1, Node(2), Node(3, Node(4), Node(5)))
      val root2 = Node(6, Node(7), Node(8, Node(9)))
      val graph = DAG[Int](Array(root1, root2): _*)

      //then
      intercept[CycleFoundException[Int]] {
        graph.addEdge(5, 6)
          .addEdge(9, 1)
      }.cycle should contain theSameElementsInOrderAs List(9, 1,3, 5, 6, 8, 9)
    }

  }
}