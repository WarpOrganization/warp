package pl.warp.engine.game.scaladsl.script

import pl.warp.engine.core.scene.Script
import pl.warp.engine.game.scene.GameComponent
import pl.warp.engine.game.script.{GameScript => JavaScript}

/**
  * @author Jaca777
  *         Created 2017-06-10 at 16
  */
abstract class GameScript(owner: GameComponent) extends JavaScript(owner){

}
