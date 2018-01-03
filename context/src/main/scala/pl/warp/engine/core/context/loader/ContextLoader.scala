package pl.warp.engine.core.context.loader

import java.lang.invoke.MethodHandles

import com.typesafe.scalalogging.LazyLogging
import pl.warp.engine.core.context.loader.service.{ServiceInfo, ServiceLoader}

/**
  * @author Jaca777
  *         Created 2017-08-29 at 22
  */
private[core] class ContextLoader extends LazyLogging  {

  def loadContext(
    preloadedContext: Object,
    profiles: Array[String]
  ): JavaContextHolder = {
    try {
      logger.info("Loading the application context...")
      val serviceLoader = new ServiceLoader
      val contextInfo = getContextInfo(preloadedContext)
      val services = serviceLoader.loadServices(List(contextInfo), profiles.toSet)
      logger.info(s"Application context loaded (Total services: ${services.length})")
      new JavaContextHolder(services)
    } catch {
      case e: RuntimeException =>
        logger.error("Failed to load the application context", e)
        throw e
    }
  }

  private def getContextInfo(preloadedContext: Object) = {
    ServiceInfo(
      `type` = preloadedContext.getClass,
      qualifier = None,
      builder = MethodHandles.constant(preloadedContext.getClass, preloadedContext),
      dependencies = List.empty
    )
  }
}


