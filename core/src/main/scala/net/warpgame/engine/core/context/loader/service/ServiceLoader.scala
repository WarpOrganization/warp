package net.warpgame.engine.core.context.loader.service

import com.typesafe.scalalogging.LazyLogging
import net.warpgame.engine.core.context.graph.DAG

/**
  * @author Jaca777
  *         Created 2017-08-29 at 22
  */
private[loader] class ServiceLoader extends LazyLogging  {

  def loadServices(
    preloadedServices: List[ServiceInfo],
    profiles: Set[String]
  ): List[(ServiceInfo, Object)] = {
    logger.info("Loading application services...")
    val servicesInfo = resolveServicesInfo(profiles) ++ preloadedServices
    logger.info("Creating service graph...")
    val serviceGraph = createGraph(servicesInfo)
    logger.info("Instantiating services...")
    createServices(serviceGraph).toList
  }

  private def resolveServicesInfo(profiles: Set[String]): Set[ServiceInfo] = {
    val serviceResolver = new ServiceResolver(new ClassResolver(ServiceLoader.ServiceRootPackage, profiles))
    serviceResolver.resolveServiceInfo()
  }

  private def createGraph(
    servicesInfo: Set[ServiceInfo],
  ): DAG[ServiceInfo] = {
    val graphBuilder = new ServiceGraphBuilder()
    graphBuilder.build(servicesInfo.toList)
  }

  private def createServices(
    serviceGraph: DAG[ServiceInfo]
  ): Map[ServiceInfo, Object] = {
    serviceGraph
      .accept(ServiceCreator())
      .accumulator
  }

}

object ServiceLoader {
  val ServiceRootPackage = "net.warpgame"
}