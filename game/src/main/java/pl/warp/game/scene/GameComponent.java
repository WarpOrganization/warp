package pl.warp.game.scene;

import pl.warp.engine.core.scene.Component;
import pl.warp.game.GameContext;

/**
 * @author Jaca777
 *         Created 2017-01-27 at 19
 */
public interface GameComponent extends Component {
    @Override
    GameContext getContext();
}
