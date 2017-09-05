package pl.warp.engine.core.context.loader

import org.scalamock.scalatest.MockFactory
import org.scalatest.{Matchers, WordSpecLike}
import pl.warp.engine.core.context.graph.{DirectedAcyclicGraph, Node}
import pl.warp.engine.core.context.loader.service._
import ServiceCreatorSpec._

import scala.reflect.ClassTag

/**
  * @author Jaca777
  *         Created 2017-09-04 at 10
  */
class ServiceCreatorSpec extends WordSpecLike with Matchers with MockFactory {

  "ServiceCreator" should {

    "create service with no dependencies" in {
      //given
      val creator = ServiceCreator()
      val graph = DirectedAcyclicGraph[ServiceInfo](Node(toServiceInfo[A]))

      //when
      val services = graph.accept(creator).accumulator

      //then
      services.size should be(1)
      services.head._2.getClass should be(classOf[A])
    }

    "create service with multiple dependencies" in {
      val creator = ServiceCreator()
      val graph = DirectedAcyclicGraph[ServiceInfo](
        Node(toServiceInfo[C],
          Node(toServiceInfo[A]), Node(toServiceInfo[B]))
      )

      //when
      val services = graph.accept(creator).accumulator

      //then
      services.size should be(3)
      services.find(_._2.isInstanceOf[C]) should matchPattern {
        case Some((_, c: C)) if c.a != null && c.b != null =>
      }
    }

    "integrate with GraphBuilder" in {
      //given
      val creator = ServiceCreator()
      val graphBuilder = new ServiceGraphBuilder()

      //when
      val graph = graphBuilder.build(
        List(toServiceInfo[A], toServiceInfo[B], toServiceInfo[C], toServiceInfo[D], toServiceInfo[E])
      )
      val services = graph.accept(creator).accumulator

      //then
      services.map(_._2.getClass) should contain theSameElementsAs List(
        classOf[A], classOf[B], classOf[C], classOf[D], classOf[E]
      )
    }

  }

  private def toServiceInfo[T: ClassTag]: ServiceInfo = {
    val serviceLoader = new ServiceResolver(mock[ClassResolver])
    serviceLoader.toServiceInfo(implicitly[ClassTag[T]].runtimeClass)
  }

}

object ServiceCreatorSpec {
  case class A()
  case class B()
  case class C(a: A, b: B)
  case class D(b: B, c: C)
  case class E(a: A, b: B, c: C, D: D)
}
