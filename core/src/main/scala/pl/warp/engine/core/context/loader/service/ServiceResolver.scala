package pl.warp.engine.core.context.loader.service

import java.lang.reflect.{Constructor, Parameter}

import org.reflections.Reflections
import pl.warp.engine.core.context.annotation.{Qualified, Service, ServiceBuilder}
import pl.warp.engine.core.context.loader.service.ServiceResolver.{AmbiguousServiceBuildersDefnitions, NoServiceConstructorFoundException, UnableToResolveServiceBuilderException}

import scala.collection.JavaConverters._

/**
  * @author Jaca777
  *         Created 2017-08-27 at 22
  */
private[loader] class ServiceResolver(pckg: String) {

  def resolveServiceInfo(): Set[ServiceInfo] = {
    val classes = resolveServiceClasses()
    classes.map(toServiceInfo)
  }

  private def resolveServiceClasses(): Set[Class[_]] = {
    val reflections = new Reflections(pckg)
    reflections.getTypesAnnotatedWith(classOf[Service]).asScala.toSet
  }

  private def toServiceInfo(serviceClass: Class[_]): ServiceInfo = {
    val builderConstructor = findBuilderConstructor(serviceClass)
    val dependencies = getDependencies(builderConstructor)
    ServiceInfo(serviceClass, dependencies.toSet)
  }

  private def findBuilderConstructor(serviceClass: Class[_]): Constructor[_] =
    serviceClass.getConstructors match {
      case Array(constr) => constr
      case constrs: Array[Constructor[_]] =>
        getExplicitConstructor(constrs, serviceClass.getName)
      case Array.empty =>
        throw NoServiceConstructorFoundException(serviceClass.getName)
    }

  private def getExplicitConstructor(constrs: Array[Constructor[_]], className: String): Constructor[_] = {
    val explicitConstrs = constrs.filter(isExplicitBuilder)
    explicitConstrs match {
      case Array(constr) => constr
      case a if a.length > 1 =>
        throw AmbiguousServiceBuildersDefnitions(className)
      case Array.empty =>
        throw UnableToResolveServiceBuilderException(className)
    }
  }

  private def isExplicitBuilder(constructor: Constructor[_]): Boolean =
    constructor.getAnnotation(classOf[ServiceBuilder]) != null

  private def getDependencies(constr: Constructor[_]): Array[DependencyInfo] ={
    val params = constr.getParameters
    params.map(toDependency)
  }

  private def toDependency(param: Parameter): DependencyInfo =
    DependencyInfo(param.getType, getQualifier(param))

  private def getQualifier(param: Parameter): Option[String] = {
    val annotation = param.getAnnotation(classOf[Qualified])
    if(annotation != null) {
      Some(annotation.qualifier())
    } else {
      None
    }
  }
}

object ServiceResolver {
  case class NoServiceConstructorFoundException(className: String)
    extends RuntimeException(
      s"No public constructors found for service class $className"
    )

  case class AmbiguousServiceBuildersDefnitions(className: String)
    extends RuntimeException(
      s"Multiple constructors annotated with @ServiceBuilder found for service class $className"
    )

  case class UnableToResolveServiceBuilderException(className: String)
    extends RuntimeException(
      s"Multiple constructors for service class $className found, but none is annotated with @ServiceBuilder"
    )

}