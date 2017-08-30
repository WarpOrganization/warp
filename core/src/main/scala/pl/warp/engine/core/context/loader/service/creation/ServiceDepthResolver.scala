package pl.warp.engine.core.context.loader.service.creation

import pl.warp.engine.core.context.graph.{GraphVisitor, Node}
import pl.warp.engine.core.context.loader.service.ServiceInfo

/**
  * @author Jaca777
  *         Created 2017-08-31 at 01
  */
class ServiceDepthResolver extends GraphVisitor[ServiceInfo] {
  var maxDepths: Map[ServiceInfo, Int] = Map[ServiceInfo, Int]()
  private var depth = 0

  override def visit(service: ServiceInfo): Unit = {
    if(depth > maxDepths(service))
      maxDepths += (service -> depth)
  }

  override def enter(): Unit =
    depth += 1

  override def leave(): Unit =
    depth -= 1
}
