package net.warpgame.engine.core.context.loader

import org.scalamock.scalatest.MockFactory
import org.scalatest.{Matchers, WordSpecLike}
import net.warpgame.engine.core.context.service.{Qualified, Service, ServiceBuilder}
import net.warpgame.engine.core.context.loader.ServiceResolverSpec.Test
import net.warpgame.engine.core.context.loader.service.{ClassResolver, DependencyInfo, ServiceResolver}
import ServiceResolverSpec._
/**
  * @author Jaca777
  *         Created 2017-09-04 at 10
  */
class ServiceResolverSpec extends WordSpecLike with Matchers with MockFactory {

  "ServiceResolver" should {

    "resolve service and its qualifiers" in {
      //given
      val serviceResolver = makeResolver()

      //when
      val services = serviceResolver.resolveServiceInfo()

      //then
      services.size should be(1)
      services.head.`type` should be(classOf[Test])
      services.head.qualifier should be(Some("test"))
    }

    "resolve service constructor" in {
      //given
      val serviceResolver = makeResolver()

      //when
      val service = serviceResolver.resolveServiceInfo().head

      //then
      service.builder.`type`().parameterList() should contain theSameElementsInOrderAs List(classOf[A], classOf[B])
    }

    "resolve service dependencies" in {
      //given
      val serviceResolver = makeResolver()

      //when
      val service = serviceResolver.resolveServiceInfo().head

      //then
      service.dependencies should contain theSameElementsInOrderAs List(
        DependencyInfo(classOf[A], None),
        DependencyInfo(classOf[B], Some("b"))
      )
    }
  }


  private def makeResolver(): ServiceResolver = {
    val classResolver = mock[ClassResolver]
    val serviceResolver = new ServiceResolver(classResolver)
    (classResolver.resolveServiceClasses _).expects().returns(Set(classOf[Test]))
    serviceResolver
  }

}

object ServiceResolverSpec {

  class A
  class B
  class C

  @Service
  @Qualified(qualifier = "test")
  class Test() {

    def this(a: A, c: C) = this()

    @ServiceBuilder
    def this(a: A, @Qualified(qualifier = "b") b: B) = this()

  }
}
