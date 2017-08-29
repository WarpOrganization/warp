package pl.warp.engine.core.context.loader.service


case class ServiceInfo(t: Class[_], dependencies: Set[DependencyInfo])
case class DependencyInfo(t: Class[_], qualifier: Option[String])