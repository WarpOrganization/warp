package pl.warp.engine.core.context.loader.service

import java.lang.invoke.MethodHandle
import java.lang.reflect.Constructor


case class ServiceInfo(
  t: Class[_],
  qualifier: Option[String],
  builder: MethodHandle,
  dependencies: List[DependencyInfo]
) {
  override def toString: String = {
    t.getName + qualifier.map(" [" + _ + "]").getOrElse("")
  }
}

case class DependencyInfo(t: Class[_], qualifier: Option[String])
