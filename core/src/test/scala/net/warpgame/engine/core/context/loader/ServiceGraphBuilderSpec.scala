package net.warpgame.engine.core.context.loader

import java.lang.invoke.MethodHandle

import org.scalamock.scalatest.MockFactory
import org.scalatest.matchers.{MatchResult, Matcher}
import org.scalatest.{Matchers, WordSpecLike}
import net.warpgame.engine.core.context.loader.ServiceGraphBuilderSpec.{GraphMatchers, ServiceBuilders}
import net.warpgame.engine.core.context.loader.service.{DependencyInfo, ServiceGraphBuilder, ServiceInfo}

import scala.reflect.ClassTag
import ServiceGraphBuilderSpec._
import net.warpgame.engine.core.context.graph.{CycleFoundException, DAG}
import net.warpgame.engine.core.context.loader.service.ServiceGraphBuilder.AmbiguousServiceDependencyException
/**
  * @author Jaca777
  *         Created 2017-09-04 at 10
  */
class ServiceGraphBuilderSpec extends WordSpecLike with Matchers with MockFactory with GraphMatchers with ServiceBuilders {

  "ServiceGraphBuilder" should {

    "build 1-elem service graph" in {
      //given
      val graphBuilder = new ServiceGraphBuilder
      val services = List(service[A]())

      //when
      val graph = graphBuilder.build(services)

      //then
      val rootNodes = graph.rootNodes
      rootNodes.size should be(1)
      rootNodes.map(_.value).head should be(service[A]())
    }

    "build graph with dependent nodes" in {
      //given
      val graphBuilder = new ServiceGraphBuilder
      val services = List(
        service[A](),
        service[B](dependencies = List(dep[A]())),
        service[C](dependencies = List(dep[A](), dep[B]()))
      )

      //when
      val graph = graphBuilder.build(services)

      //then
      val rootNodes = graph.rootNodes
      rootNodes.size should be(1)
      rootNodes.map(_.value).head.`type` should be(classOf[C])
      graph should containDependency[B -> A]
      graph should containDependency[C -> A]
    }

    "use qualifiers to resolve services" in {
      //given
      val graphBuilder = new ServiceGraphBuilder
      val services = List(
        service[B](dependencies = List(dep[A](qualifier = Some("test")))),
        service[D](qualifier = Some("test")),
        service[E](qualifier = None)
      )

      //when
      val graph = graphBuilder.build(services)

      //then
      graph should containDependency[(B -> D)]
    }

    "throw exception when a cyclic dependency is present" in {
      //given
      val graphBuilder = new ServiceGraphBuilder
      val services = List(
        service[A](dependencies = List(dep[C]())),
        service[B](dependencies = List(dep[A]())),
        service[C](dependencies = List(dep[B]()))
      )

      //then
      intercept[CycleFoundException[ServiceInfo]] {
        graphBuilder.build(services)
      }
    }

    "throw exception when a dependency is ambiguous" in {
      //given
      val graphBuilder = new ServiceGraphBuilder
      val services = List(
        service[A](dependencies = List(dep[A]())),
        service[D](),
        service[E]()
      )

      //then
      intercept[AmbiguousServiceDependencyException] {
        graphBuilder.build(services)
      }
    }
  }
}

object ServiceGraphBuilderSpec {

  class A
  class B
  class C
  class D extends A
  class E extends A



  trait GraphMatchers {

    class FileEndsWithExtensionMatcher(from: Class[_], to: Class[_]) extends Matcher[DAG[ServiceInfo]] {

      def apply(graph: DAG[ServiceInfo]) = {
        val node = graph.resolveNode(_.`type` == from)
        val matches = node match {
          case Some(b) => b.leaves.exists(_.value.`type` == to)
          case None => false
        }
        MatchResult(
          matches,
          s"""Graph did not contain dependency from ${from.getName} to ${to.getName}""",
          s"""Graph contains dependency from ${from.getName} to ${to.getName}"""
        )
      }
    }


    trait DependencyLike[T] {
      def from: Class[_]

      def to: Class[_]
    }

    case class DependencyLikeApply[T](from: Class[_], to: Class[_]) extends DependencyLike[T]

    type ->[A, B]

    implicit def arrowDependencyLike[A: ClassTag, B: ClassTag, ->[A, B]]: DependencyLike[A -> B] = {
      DependencyLikeApply[A -> B](
        implicitly[ClassTag[A]].runtimeClass,
        implicitly[ClassTag[B]].runtimeClass
      )
    }

    def containDependency[C: DependencyLike] = {
      val dependency = implicitly[DependencyLike[C]]
      new FileEndsWithExtensionMatcher(
        dependency.from,
        dependency.to
      )
    }
  }


  trait ServiceBuilders {
    def service[T: ClassTag](
      qualifier: Option[String] = None,
      builder: MethodHandle = null,
      dependencies: List[DependencyInfo] = List.empty
    ): ServiceInfo = {
      ServiceInfo(implicitly[ClassTag[T]].runtimeClass, qualifier, builder, dependencies)
    }

    def dep[T: ClassTag](qualifier: Option[String] = None): DependencyInfo = {
      DependencyInfo(implicitly[ClassTag[T]].runtimeClass, qualifier)
    }
  }

}