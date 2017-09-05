package pl.warp.engine.core.context.loader

import com.typesafe.scalalogging.LazyLogging
import pl.warp.engine.core.context.loader.service.ServiceLoader

/**
  * @author Jaca777
  *         Created 2017-08-29 at 22
  */
private[core] class ContextLoader extends LazyLogging  {
  def loadContext(): JavaContextHolder = {
    try {
      logger.info("Loading the application context...")
      val serviceLoader = new ServiceLoader
      val services = serviceLoader.loadServices()
      logger.info("Application context loaded")
      new JavaContextHolder(services)
    } catch {
      case e: RuntimeException =>
        logger.error("Failed to load the application context", e)
        throw e
    }
  }
}


