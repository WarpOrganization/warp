package pl.warp.engine.core.context.loader.service

import pl.warp.engine.core.context.graph.GraphVisitor

/**
  * @author Jaca777
  *         Created 2017-08-31 at 01
  */

case class ServiceCreator(
  dependencyStack: List[Object] = List.empty,
  accumulator: Map[ServiceInfo, Object] = Map.empty,
) extends GraphVisitor[ServiceInfo, ServiceCreator] {

  override def visit(service: ServiceInfo): ServiceCreator =
    accumulator.get(service) match {
      case Some(instance) =>
        this.copy(
          dependencyStack = instance :: dependencyStack
        )
      case None =>
        val dependenciesNumber = service.dependencies.size
        val dependencies = dependencyStack.take(dependenciesNumber).reverse
        val instance = service.builder.invokeWithArguments(dependencies.toArray: _*)
        ServiceCreator(
          dependencyStack = instance :: dependencyStack.drop(dependenciesNumber),
          accumulator = accumulator + (service -> instance)
        )
    }

  override def enter(service: ServiceInfo): Boolean = !accumulator.contains(service)
}
