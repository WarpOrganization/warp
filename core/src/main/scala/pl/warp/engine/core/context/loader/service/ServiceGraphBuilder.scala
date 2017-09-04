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
    } yield dependencyService -> service
    val edgesGraph = buildFromEdges(edges.toList)
    val standaloneNodes = (services diff edges.flatMap {
      case (from, to) => List(from, to)
    }).toList
    addServiceNodes(edgesGraph, standaloneNodes)
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
    val assignable = services
      .filter(s => dependencyInfo.t.isAssignableFrom(s.t))
    val qualified = dependencyInfo.qualifier match {
      case Some(qualifier) =>
        assignable.filter(_.qualifier.exists(_ == qualifier))
      case None =>
        assignable
    }
    if (qualified.size > 1)
      throw AmbiguousServiceDependencyException(service, dependencyInfo, qualified)
    else qualified.head
  }

  @tailrec
  private def addServiceNodes(
    graph: DirectedAcyclicGraph[ServiceInfo],
    nodes: List[ServiceInfo]
  ): DirectedAcyclicGraph[ServiceInfo] = nodes match {
    case node :: tail => addServiceNodes(graph.addNode(node), tail)
    case Nil => graph
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


