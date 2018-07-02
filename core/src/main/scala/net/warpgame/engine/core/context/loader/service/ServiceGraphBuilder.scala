package net.warpgame.engine.core.context.loader.service

import java.lang.invoke.MethodHandles

import net.warpgame.engine.core.context.loader.service.ServiceGraphBuilder._
import net.warpgame.engine.core.graph.DAG

import scala.annotation.tailrec

/**
  * @author Jaca777
  *         Created 2017-08-29 at 22
  */
private[loader] class ServiceGraphBuilder() {

  def build(services: List[ServiceInfo]): DAG[ServiceInfo] = {
    val edges = for {
      service <- services
      dependencyInfo <- service.dependencies
      dependencyService = findDependency(service, services, dependencyInfo)
    } yield service -> dependencyService
    val edgesGraph = buildFromEdges(edges)
    val standaloneNodes = services diff edges.flatMap {
      case (from, to) => List(from, to)
    }
    addServiceNodes(edgesGraph, standaloneNodes)
  }

  @tailrec
  private def buildFromEdges(
    edges: List[(ServiceInfo, ServiceInfo)],
    graph: DAG[ServiceInfo] = DAG()
  ): DAG[ServiceInfo] =
    edges match {
      case (from, to) :: tail =>
        buildFromEdges(tail, graph.addEdge(from, to))
      case Nil =>
        graph
    }


  //OPT we may eventually want to optimize this
  private def findDependency(
    service: ServiceInfo,
    services: List[ServiceInfo],
    dependencyInfo: DependencyInfo
  ): ServiceInfo = {
    val qualified = findQualified(services, dependencyInfo)
    if (qualified.size > 1)
      throw AmbiguousServiceDependencyException(service, dependencyInfo, qualified)
      if(qualified.size < 1)
        throw ServiceNotFoundException(service, dependencyInfo)
    else qualified.head
  }

  private def findQualified(
    services: List[ServiceInfo],
      dependencyInfo: DependencyInfo
  ): List[ServiceInfo] = {
    val assignable = services
      .filter(s => dependencyInfo.`type`.isAssignableFrom(s.`type`))
    dependencyInfo.qualifier match {
      case Some(qualifier) =>
        assignable.filter(_.qualifier.exists(_ == qualifier))
      case None =>
        assignable
    }
  }

  @tailrec
  private def addServiceNodes(
    graph: DAG[ServiceInfo],
    nodes: List[ServiceInfo]
  ): DAG[ServiceInfo] = nodes match {
    case node :: tail => addServiceNodes(graph.addNode(node), tail)
    case Nil => graph
  }

}

object ServiceGraphBuilder {

  case class AmbiguousServiceDependencyException(
    service: ServiceInfo,
    dependencyInfo: DependencyInfo,
    assignable: List[ServiceInfo]
  ) extends RuntimeException({
    val serviceMsg = s"Unable to create instance of ${service.`type`.getName}."
    val causeMsg = dependencyInfo match {
      case DependencyInfo(t, Some(q)) =>
        s"Multiple services qualified with $q and assignable to ${t.getName} found at: "
      case DependencyInfo(t, None) =>
        s"Multiple services assignable to ${t.getName} found at: "
    }
    val services = assignable
      .map(_.`type`.getSimpleName)
      .mkString(", ")
    serviceMsg + causeMsg + services
  })

  case class ServiceNotFoundException(
    service: ServiceInfo,
    dependencyInfo: DependencyInfo,
  ) extends RuntimeException({
    val serviceMsg = s"Unable to create instance of ${service.`type`.getName}."
    val causeMsg = dependencyInfo match {
      case DependencyInfo(t, Some(q)) =>
        s"No services qualified with $q and assignable to ${t.getName} found "
      case DependencyInfo(t, None) =>
        s"No services assignable to ${t.getName} found "
    }
    serviceMsg + causeMsg
  })
}


