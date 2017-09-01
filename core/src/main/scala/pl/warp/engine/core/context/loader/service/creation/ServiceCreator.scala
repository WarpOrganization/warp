package pl.warp.engine.core.context.loader.service.creation

import pl.warp.engine.core.context.graph.{DirectedAcyclicGraph, GraphVisitor, Node}
import pl.warp.engine.core.context.loader.service.ServiceInfo

/**
  * @author Jaca777
  *         Created 2017-08-31 at 01
  *         TODO Rethink
  */
class ServiceCreator {

  def constructServices(graph: DirectedAcyclicGraph[ServiceInfo]): Map[ServiceInfo, Object] = {
    val servicesWithCreationOrder = getServicesCreationOrder(graph)
    val groupedByCreationOrder = servicesWithCreationOrder
      .groupBy(_._2)
      .map {
        case (order, services) =>
          (order, services.keys)
      }
    val orderedGroups = groupedByCreationOrder
      .toList
      .sortBy(_._1)
      .map(_._2)

  ???
  }

  private def getServicesCreationOrder(graph: DirectedAcyclicGraph[ServiceInfo]): Map[ServiceInfo, Int] = {
    val depthResolver = new ServiceDepthResolver
    graph.accept(depthResolver)
    depthResolver.depths
  }

  private def createServices(services: Iterable[ServiceInfo]): Object = ???


}
