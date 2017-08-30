package pl.warp.engine.core.context.loader.service

import pl.warp.engine.core.context.graph.DirectedAcyclicGraph
import pl.warp.engine.core.context.loader.service.ServiceGraphBuilder._

import scala.annotation.tailrec

/**
  * @author Jaca777
  *         Created 2017-08-29 at 22
  */
private[loader] class ServiceGraphBuilder {

  def build(services: Set[ServiceInfo]): DirectedAcyclicGraph[ServiceInfo] = {
    val edges = for {
      service <- services
      dependencyInfo <- service.dependencies
      dependencyService = findDependency(service, services, dependencyInfo)
    } yield (service, dependencyService)
    buildFromEdges(edges.toList)
  }

  @tailrec
  private def buildFromEdges(
    edges: List[(ServiceInfo, ServiceInfo)],
    graph: DirectedAcyclicGraph[ServiceInfo] = DirectedAcyclicGraph()
  ): DirectedAcyclicGraph[ServiceInfo] =
    edges match {
      case (from, to) :: tail =>
        buildFromEdges(tail, graph.addEdge(from, to))
      case Nil =>
        graph
    }

  //OPT we may eventually want to optimize this
  private def findDependency(
    service: ServiceInfo,
    services: Set[ServiceInfo],
    dependencyInfo: DependencyInfo
  ): ServiceInfo = {
    dependencyInfo.qualifier match {
      case Some(qualifier) =>
        val qualified = services
          .filter(_.qualifier.exists(_ == qualifier))
        val assignable = qualified
          .filter(s => dependencyInfo.t.isAssignableFrom(s.t))
        if (assignable.size > 1)
          throw AmbiguousServiceDependencyException(service, dependencyInfo, qualified)
        else assignable.head
    }
  }

}

object ServiceGraphBuilder {

  case class AmbiguousServiceDependencyException(
    service: ServiceInfo,
    dependencyInfo: DependencyInfo,
    assignable: Set[ServiceInfo]
  ) extends RuntimeException({
    val serviceMsg = s"Unable to create instance of ${service.t.getName}."
    val causeMsg = dependencyInfo match {
      case DependencyInfo(t, Some(q)) =>
        s"Multiple services qualified with $q and assignable to ${t.getName} found at: "
      case DependencyInfo(t, None) =>
        s"Multiple services assignable to ${t.getName} found at: "
    }
    val services = assignable
      .map(_.t.getSimpleName)
      .mkString(", ")
    serviceMsg + causeMsg + services
  })

}


