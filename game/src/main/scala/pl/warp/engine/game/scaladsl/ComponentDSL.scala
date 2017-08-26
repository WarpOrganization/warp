package pl.warp.engine.game.scaladsl

import java.util.function.Consumer

import pl.warp.engine.core.component.SimpleListener
import pl.warp.engine.game.scene.GameComponent
import rx.lang.scala.Observable
import rx.lang.scala.Subject

/**
  * @author Jaca777
  *         Created 2017-06-10 at 16
  */

object ComponentDSL {
/*  implicit class ScalaComponent(component: GameComponent) {
    def events[T <: Event](name: String): Observable[T] = {
      val eventsSubject: Subject[T] = Subject()
      component.addListener(new Listener[T](component, name, eventsSubject.onNext))
      eventsSubject
    }
  }*/
}

