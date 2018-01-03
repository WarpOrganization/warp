package pl.warp.engine.core.context.loader.service

import com.typesafe.scalalogging.LazyLogging
import org.reflections.Reflections
import pl.warp.engine.core.context.service.{Profile, Service}

import scala.collection.JavaConverters._

/**
  * @author Jaca777
  *         Created 2017-09-04 at 10
  */
class ClassResolver(pckg: String, profiles: Set[String]) extends LazyLogging {

  def resolveServiceClasses(): Set[Class[_]] = {
    logger.debug("Resolving service classes...")
    val reflections = new Reflections(pckg)
    val serviceClasses = reflections.getTypesAnnotatedWith(classOf[Service])
      .asScala.toSet
    serviceClasses.filter(appliesToProfiles(profiles))
  }

  def appliesToProfiles(profiles: Set[String])(serviceClass: Class[_]): Boolean = {
    val profile = serviceClass.getAnnotation(classOf[Profile])
    profile == null || profiles.contains(profile.value())
  }
}
