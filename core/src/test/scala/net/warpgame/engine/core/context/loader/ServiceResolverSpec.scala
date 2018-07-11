package net.warpgame.engine.core.context.loader

import org.scalamock.scalatest.MockFactory
import org.scalatest.{Matchers, WordSpecLike}
import net.warpgame.engine.core.context.service.{Qualified, Service, ServiceBuilder}
import net.warpgame.engine.core.context.loader.ServiceResolverSpec.Test
import net.warpgame.engine.core.context.loader.service.{ClassResolver, DependencyInfo, ServiceResolver}
import ServiceResolverSpec._
import net.warpgame.engine.core.context.service.{Qualified, ServiceBuilder}
/**
  * @author Jaca777
  *         Created 2017-09-04 at 10
  *         */
class ServiceResolverSpec extends WordSpecLike with Matchers with MockFactory {

  "ServiceResolver" should {

    "resolve services and its qualifiers" in {
      //given
      val serviceResolver = makeResolver()

      //when
      val service = serviceResolver.resolveServiceInfo().find(s => s.`type` == classOf[Test]).get

      //then
      service.`type` should be(classOf[Test])
      service.qualifier should be(Some("test"))
    }

    "resolve service constructor" in {
      //given
      val serviceResolver = makeResolver()

      //when
      val service = serviceResolver.resolveServiceInfo().find(s => s.`type` == classOf[Test]).get

      //then
      service.builder.`type`().parameterList() should contain theSameElementsInOrderAs List(classOf[A], classOf[B])
    }

    "resolve service dependencies" in {
      //given
      val serviceResolver = makeResolver()

      //when
      val service = serviceResolver.resolveServiceInfo().find(s => s.`type` == classOf[Test]).get

      //then
      service.dependencies should contain theSameElementsInOrderAs List(
        DependencyInfo(classOf[A], None),
        DependencyInfo(classOf[B], Some("b"))
      )
    }

    "resolve collective dependencies" in {
      //given
      val serviceResolver = makeResolver()

      //when
      val service = serviceResolver.resolveServiceInfo().find(s => s.`type` == classOf[Array[X]]).get

      service.dependencies.size should be (2)
    }
  }


  private def makeResolver(): ServiceResolver = {
    val classResolver = mock[ClassResolver]
    val serviceResolver = new ServiceResolver(classResolver)
    (classResolver.resolveServiceClasses _).expects().returns(Set(classOf[Test], classOf[TestWithCollectiveDep], classOf[A], classOf[B], classOf[C]))
    serviceResolver
  }

}

object ServiceResolverSpec {

  class X
  @Service
  class A extends X
  @Service
  class B extends X
  @Service
  class C

  @Service
  @Qualified(qualifier = "test")
  class Test() {

    def this(a: A, c: C) = this()

    @ServiceBuilder
    def this(a: A, @Qualified(qualifier = "b") b: B) = this()

  }

  @Service
  class TestWithCollectiveDep(ax: Array[X])
}

