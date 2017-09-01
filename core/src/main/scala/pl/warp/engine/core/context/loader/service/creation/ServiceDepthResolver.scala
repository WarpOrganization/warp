package pl.warp.engine.core.context.loader.service.creation

import pl.warp.engine.core.context.graph.{GraphVisitor, Node}
import pl.warp.engine.core.context.loader.service.ServiceInfo


/**
  * @author Jaca777
  *         Created 2017-08-31 at 01
  */
case class ServiceDepthResolver private (
  private val maxDepths: Map[ServiceInfo, Int],
  private val depth: Int
) extends GraphVisitor[ServiceInfo, ServiceDepthResolver] {

  def this() = this(Map.empty, 0)

  override def visit(service: ServiceInfo): ServiceDepthResolver = {
    if(depth > maxDepths(service))
      this.copy(maxDepths = maxDepths + (service -> depth))
    else this
  }

  override def enter(): ServiceDepthResolver =
    this.copy(depth = depth + 1)
    ServiceDepthResolver(maxDepths, depth + 1)

  override def leave(): ServiceDepthResolver =
    this.copy(depth = depth - 1)

  def depths: Map[ServiceInfo, Int] = maxDepths
}