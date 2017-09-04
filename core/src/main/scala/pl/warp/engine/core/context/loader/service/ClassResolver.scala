package pl.warp.engine.core.context.loader.service

import org.reflections.Reflections
import pl.warp.engine.core.context.annotation.Service
import scala.collection.JavaConverters._

/**
  * @author Jaca777
  *         Created 2017-09-04 at 10
  */
class ClassResolver(pckg: String) {
  def resolveServiceClasses(): Set[Class[_]] = {
    val reflections = new Reflections(pckg)
    reflections.getTypesAnnotatedWith(classOf[Service]).asScala.toSet
  }
}
