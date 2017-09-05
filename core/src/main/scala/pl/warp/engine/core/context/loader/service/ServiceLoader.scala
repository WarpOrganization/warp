package pl.warp.engine.core.context.loader.service

import pl.warp.engine.core.context.graph.DirectedAcyclicGraph

/**
  * @author Jaca777
  *         Created 2017-08-29 at 22
  */
private[loader] class ServiceLoader {

  def loadServices(): Map[ServiceInfo, Object] = {
    val servicesInfo = resolveServicesInfo()
    val serviceGraph = createGraph(servicesInfo)
    createServices(serviceGraph)
  }

  private def resolveServicesInfo(): Set[ServiceInfo] = {
    val serviceResolver = new ServiceResolver(new ClassResolver(ServiceLoader.ServiceRootPackage))
    serviceResolver.resolveServiceInfo()
  }

  private def createGraph(servicesInfo: Set[ServiceInfo]): DirectedAcyclicGraph[ServiceInfo] = {
    val graphBuilder = new ServiceGraphBuilder
    graphBuilder.build(servicesInfo.toList)
  }

  private def createServices(serviceGraph: DirectedAcyclicGraph[ServiceInfo]): Map[ServiceInfo, Object] = {
    serviceGraph
      .accept(ServiceCreator())
      .accumulator
  }

}

object ServiceLoader {
  val ServiceRootPackage = "pl.warp"
}