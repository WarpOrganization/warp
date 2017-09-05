package pl.warp.engine.core.context.loader

import java.util.Optional

import pl.warp.engine.core.context.annotation.Qualified
import pl.warp.engine.core.context.loader.service.ServiceInfo
import java.{util => jutil}

import pl.warp.engine.core.context.MoreThanOneServiceFoundException

/**
  * @author Jaca777
  *         Created 2017-08-29 at 22
  */
private[core] class JavaContextHolder(services: List[(ServiceInfo, Object)]) {
  def findOne[T](t: Class[T], qualifier: jutil.Optional[String]): jutil.Optional[T] = {
    val assignable = services.filter {
      case (info, _) => t.isAssignableFrom(info.t)
    }
    val qualified = {
      if(qualifier.isPresent)
        assignable.filter {
          case (info, _) => info.qualifier.contains(qualifier.get())
        }
      else assignable
    }
    qualified match {
      case (_, instance) :: Nil =>
        Optional.of(instance.asInstanceOf[T])
      case Nil => Optional.empty()
      case _ => //More than one
        throw new MoreThanOneServiceFoundException(t.getName, qualifier)
    }
  }

}
