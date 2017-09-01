package pl.warp.engine.core.context.loader.service

import java.lang.invoke.MethodHandle
import java.lang.reflect.Constructor


case class ServiceInfo(
  t: Class[_],
  qualifier: Option[String],
  builder: MethodHandle,
  dependencies: List[DependencyInfo]
)

case class DependencyInfo(t: Class[_], qualifier: Option[String])
