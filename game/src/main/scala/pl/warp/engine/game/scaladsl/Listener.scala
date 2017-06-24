package pl.warp.engine.game.scaladsl

import java.util.function.Consumer

import pl.warp.engine.core.scene.{Event, SimpleListener => JavaListener}
import pl.warp.engine.game.scene.GameComponent

/**
  * @author Jaca777
  *         Created 2017-06-10 at 16
  */
/*
class Listener[T <: Event](component: GameComponent, eventName: String, handler: T => Unit)
  extends JavaListener(component, eventName, new Consumer[Event] {
    override def accept(t: Event): Unit = handler(t)
  })
*/
