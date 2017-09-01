package pl.warp.engine.core.context.loader.service

import pl.warp.engine.core.context.graph.GraphVisitor

/**
  * @author Jaca777
  *         Created 2017-08-31 at 01
  *         TODO Rethink
  */

case class ServiceCreator(
  dependencyStack: List[Object] = List.empty,
  accumulator: Map[ServiceInfo, Object] = Map.empty
) extends GraphVisitor[ServiceInfo, ServiceCreator] {

  override def visit(service: ServiceInfo): ServiceCreator =
    accumulator.get(service) match {
      case Some(instance) =>
        this.copy(dependencyStack = instance :: dependencyStack)
      case None =>
        val dependenciesNumber = service.dependencies.size
        val dependencies = dependencyStack.take(dependenciesNumber)
        val instance = service.builder.invokeWithArguments(dependencies.toArray: _*)
        ServiceCreator(
          service :: dependencyStack.drop(dependenciesNumber),
          accumulator + (service -> instance)
        )
    }

}
